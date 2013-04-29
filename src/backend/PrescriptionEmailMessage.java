package backend;

import java.sql.SQLException;

import javax.mail.MessagingException;

import backend.DAO.PrescriptionEmailDAO;

import shared.Patient;
import shared.Prescription;
import shared.PrescriptionDateTime;

/**
 * Message that gets sent to the patient
 * 
 * @author Justin
 * 
 */
public class PrescriptionEmailMessage implements Runnable {
	private Prescription prescription;
	private GmailIMAPClient client;
	private Patient forPatient;
	private PrescriptionDateTime forTime;

	private static final String SUBJECT = "SmartPill - Time to take your prescription";

	private static final String IDENTIFIER_START = "<!-- PID:[";
	private static final String IDENTIFIER_END = "] -->";

	public PrescriptionEmailMessage(Prescription prescription,
			PrescriptionDateTime forTime, Patient forPatient,
			GmailIMAPClient client) {
		this.prescription = prescription;
		this.client = client;
		this.forPatient = forPatient;
		this.forTime = forTime;
	}

	private String getBody(PrescriptionEmail emailObj) {
		StringBuilder body = new StringBuilder();
		body.append("<p>Hello " + this.forPatient.name + ",<br/>\n");
		body.append("It is currently time you you to take your medication. \n");
		body.append("Below is what you should be taking: </p>\n");
		body.append("<p><strong>Prescription name:</strong> "
				+ this.prescription.name + "<br/>\n");
		body.append("<strong>Dosage:</strong> " + this.prescription.dosage
				+ "<br/>\n");
		body.append("<strong>Message:</strong>" + this.prescription.message
				+ "<br/>\n");
		body.append("</p>");
		// TODO: HTML encode the picture path!
		body.append("<img alt='' src='" + this.prescription.picturePath
				+ "' />");

		// Throw in our email id
		body.append(IDENTIFIER_START);
		body.append(emailObj.id);
		body.append(IDENTIFIER_END);

		return body.toString();
	}

	public static boolean markEmailRead(String messageBody) {
		String messageBody2 = messageBody.replace("\r\n>", "");
		// Try and find the email ID prefix
		int PIDIndex = messageBody2.indexOf(IDENTIFIER_START);
		if (PIDIndex < 0) {
			return false;
		} else {
			// Try and find the email ID postfix
			String choppedMessage = messageBody2.substring(PIDIndex);
			int PIDEndIndex = choppedMessage.indexOf(IDENTIFIER_END);
			if (PIDEndIndex < 0) {
				return false;
			} else {
				// Substring to just get the numbers
				choppedMessage = choppedMessage.substring(
						IDENTIFIER_START.length(), PIDEndIndex);
				Integer id = null;
				try {
					id = Integer.parseInt(choppedMessage.trim());
				} catch (NumberFormatException e) {
					System.err.println("markEmailRead: " + e.getMessage());
					return false;
				}
				if (id == null)
					return false;
				
				// Okay, lets try and find something...
				PrescriptionEmailDAO peDb = new PrescriptionEmailDAO();
				PrescriptionEmail email = null;
				try {
					email = peDb.findByPrimaryKey(id);
				} catch (SQLException e) {
					System.err.println("markEmailRead: " + e.getMessage());
					return false;
				}
				if (email == null)
					return false;
				
				email.read = true;
				try {
					peDb.update(email);
				} catch (SQLException e) {
					System.err.println("markEmailRead: " + e.getMessage());
					return false;
				}
				
				return true;
			}
		}
	}

	public void run() {
		try {
			// Throw the email into the SQL database
			PrescriptionEmail email = new PrescriptionEmail(this.forTime, this.forPatient.for_doctor);
			PrescriptionEmailDAO emailDb = new PrescriptionEmailDAO();
			email = emailDb.insert(email);

			this.client.SendEmail(this.forPatient.email, SUBJECT,
					getBody(email));			
		} catch (Exception e) {
			System.out.println("Failed to send email to: "
					+ this.forPatient.email + "\n" + e.getMessage());
		}
	}
}
