package backend;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.sun.mail.smtp.SMTPMessage;

/**
 * Allows user to create and send an email
 * @author Jamison
 *
 */
public class OutgoingMail {
	
	/**
	 * User name for email account
	 */
	private String user;
	/**
	 * Password for the email account
	 */
	private String password;
	/**
	 * Session that is used to send an email
	 */
	private Session session;
	/**
	 * The email client that will be used
	 */
	private String client;
	/**
	 * List of attachments for an email, will be cleared after each message is sent
	 */
	static public ArrayList<File> attachments;
	/**
	 * Flag to determine if attachments are present and should be added
	 */
	private boolean attached;
	
	/**
	 * Constructs the class object with the assumption that gmail is the client, providing the full email is not 
	 * necessary for a regular gmail account, but it is advised to do so anyway.  Provided the full email also allows
	 * for university emails such as cymail to be used by this class.
	 * @param user the user name
	 * @param password the password for that user name
	 */
	public OutgoingMail(String user, String password){
		this.user = user;
		this.password = password;
		//client is assumed to be gmail
		client = "gmail";
		//create session for this object
		setSession(client);
		//set attached flag to false since no attachments have been added yet
		attached = false;
		attachments = new ArrayList<File>();
	}
	
	/**
	 * Will send an email to with the given subject and and body to one or many recipients.
	 * @param to the string that has the recipients in it, will work for multiple recipients as long as the emails
	 * are separated by commas
	 * @param subject The subject of the email
	 * @param bodyText the text that is to be placed in the body of the email
	 */
	public void sendMessage(String to, String subject, String bodyText) throws MessagingException{
		//pick the recipients out of the string containing them
		ArrayList<String> toList = parseTo(to);
		//construct the message object that will be sent
		SMTPMessage smtp = new SMTPMessage(session);
		
		//set the subject of the email
		smtp.setSubject(subject);
		//add all of the recipients to the email
		for (int i = 0; i < toList.size(); i++){
			smtp.addRecipient(Message.RecipientType.TO, new InternetAddress(toList.get(i)));
		}
		
		//if attachments are present the message must be constructed as a multipart object
		if (attached){
			//all of the parts of the message will be added to this multipart object which will then be
			//added to the smtp message
			MimeMultipart mp = new MimeMultipart();
			//cycle through adding all the attachments individually
			for (int i = 0; i < attachments.size(); i++){
				//each attachment is put into a bodypart
				MimeBodyPart a = new MimeBodyPart();
				//use a source and handler to get the data from the file 
				FileDataSource source = new FileDataSource(attachments.get(i));
				a.setDataHandler(new DataHandler(source));
				//set the name of the file as it should appear when the file appears in the email
				a.setFileName(attachments.get(i).getName());
				//bodypart to the multipart object
				mp.addBodyPart(a);
			}
			//create another body part to add the text in
			MimeBodyPart bt = new MimeBodyPart();
			bt.setText(bodyText);
			//put body text body part into the multipart
			mp.addBodyPart(bt);
			//put multipart into the message so it can be sent
			smtp.setContent(mp);
		}
		//if no attachments are present just set the body text without multipart stuff
		else {
			//set the body text for the email
			smtp.setText(bodyText);
		}
		
		//send the email
		Transport.send(smtp);
		//clear attachments and set flag to false
		attachments.clear();
		attached = false;
	}
	
	/**
	 * Picks the email addresses out of a string assuming that they are seperated by commas
	 * @param toLine the string containing the email addresses
	 * @return a ArrayList<String> with the email addresses in it
	 */
	private ArrayList<String> parseTo(String toLine){
		ArrayList<String> recipients = new ArrayList<String>();
		int startLocation = 0;
		boolean firstLetterFound = false;
		
		//cycle through testing every char in the string
		for (int i = 0; i < toLine.length(); i++){
			//if a comma is found and the firstLetterFound is set than parse out the email adress
			if (toLine.charAt(i) == ',' && firstLetterFound){
				recipients.add(toLine.substring(startLocation, i));
				firstLetterFound = false;
			}
			//if the first letter has not been found and a character other than blank space or comma comes
			//up set that as the startLocation since that will be where the first letter of the email address
			//is located
			else if ((toLine.charAt(i) != ' ' && toLine.charAt(i) != ',')& !firstLetterFound){
				firstLetterFound = true;
				startLocation = i;
			}
			//special case to account for when only one email is listed or the last email in the string when
			//multiple emails are lsited
			else if (i == toLine.length() - 1 && firstLetterFound){
				recipients.add(toLine.substring(startLocation));
			}
		}
		return recipients;
	}
	
	
	private void setSession(String client){
		if (client.equals("gmail")){
			Properties props = new Properties();
			//set the name of the host server
			props.put("mail.smtp.host", "smtp.gmail.com");
			//I don't really know what these two do they were in the example though
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			//set up authentication
			props.put("mail.smtp.auth", "true");
			//set port for access
			props.put("mail.smtp.port", "465");
					session = Session.getInstance(props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user, password);
						}	
					});
		}
	}
	
	/**
	 * Adds a file to attachments so that it can be sent with the email as an attachment
	 * @param f the file to be added to the email
	 */
	public void addAttachment(File f)throws IllegalArgumentException{
		//check the file type to make sure that gmail allows that type of file to be sent
		EmailFileFilter filter = new EmailFileFilter();
		if (!filter.accept(f)) throw new IllegalArgumentException();
		//add the file to the attachments arayList
		attachments.add(f);
		//set attached true so that attachments will be added when message is sent
		attached = true;
	}
	
	/**
	 * Removes an attachment at the given index
	 * @param index of the file that should be removed from the attachment list
	 */
	public void removeAttachment(int index){
		//remove the file located at the given index in the attachments arrayList
		attachments.remove(index);
		//the size of the arrayList attachments is 0 so there are no files to be added so attached should be false
		if (attachments.size() == 0){
			attached = false;
		}
	}
	
	/**
	 * Returns the index of the first occurrence of an attachment with the given file name
	 * @param fileName name of the file for which you wish to find the index
	 * @return the index location of the first attachment with the given file name
	 * @throws IllegalArgumentException if the file name provided is not an attachment
	 */
	public int getAttachmentIndex(String fileName) throws IllegalArgumentException{
		for (int i = 0; i < attachments.size(); i++){
			//return with current i when file with given fileName is found
			if (attachments.get(i).getName().equals(fileName)) return i;
		}
		//the the for loop ends the file was not found so IllegalArgumentException should be thrown
		throw new IllegalArgumentException();
	}
	
	/**
	 * File filter that filters out ade,adp,bat,chm,cmd,com,cpl,hta,ins,isp,jse,lib,mde,msc
	 * msp,mst,pif,scr,sct,shb,sys,vb,vbe,vbs,vxd,wsc,wsf,wsh extensions because google blocks them for
	 * security purposes
	 * @author Jamison
	 *
	 */
	private class EmailFileFilter extends FileFilter{

		@Override
		public boolean accept(File f) {
			String fileName = f.getName();
			
			//check all 1 period 2 letter extensions, length of 4 is required to run these test because you need
			//to have atleast 1 char infront of the period which means 4 chars total are needed
			if (fileName.length() >= 4){
				String sub = fileName.substring(fileName.length() - 3, fileName.length());
				if (sub.equals(".vb")) return false;
			}
			
			if (fileName.length() >= 5){
				String sub = fileName.substring(fileName.length() - 4, fileName.length());
				//check all 1 period 3 letter extensions
				if (sub.equals(".ade")) return false;
				else if (sub.equals(".adp")) return false;
				else if (sub.equals(".bat")) return false;
				else if (sub.equals(".chm")) return false;
				else if (sub.equals(".cmd")) return false;
				else if (sub.equals(".com")) return false;
				else if (sub.equals(".cpl")) return false;
				else if (sub.equals(".hta")) return false;
				else if (sub.equals(".ins")) return false;
				else if (sub.equals(".isp")) return false;
				else if (sub.equals(".jse")) return false;
				else if (sub.equals(".lib")) return false;
				else if (sub.equals(".mde")) return false;
				else if (sub.equals(".msc")) return false;
				else if (sub.equals(".msp")) return false;
				else if (sub.equals(".mst")) return false;
				else if (sub.equals(".pif")) return false;
				else if (sub.equals(".scr")) return false;
				else if (sub.equals(".sct")) return false;
				else if (sub.equals(".shb")) return false;
				else if (sub.equals(".sys")) return false;
				else if (sub.equals(".vbe")) return false;
				else if (sub.equals(".vbs")) return false;
				else if (sub.equals(".vxd")) return false;
				else if (sub.equals(".wsc")) return false;
				else if (sub.equals(".wsf")) return false;
				else if (sub.equals(".wsh")) return false;
			}
			
			//didn't fail any of the extension test so it should be safe
			return true;
		}

		@Override
		public String getDescription() {
			return "filters out extensions that are not allowed in emails for security reasons";
		}
		
	}
	
	public ArrayList<File> getAttachments(){
		return attachments;
	}
	
}


