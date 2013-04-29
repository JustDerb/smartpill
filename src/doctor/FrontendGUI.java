package doctor;

import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import shared.Alert;
import shared.DatabaseControl;
import shared.Doctor;
import shared.Patient;
import shared.PillMessage;
import shared.Prescription;
import shared.PrescriptionDateTime;
import shared.RetrievalMessage;
import shared.RetrievalMessage.Retrieve;
import shared.SmartPillDefaults;

public class FrontendGUI {
	
	/**
	 * Indicates that the login panel should be displayed.
	 */
	public static int LOGIN = 0;
	/**
	 * Indicates that the home page panel should be displayed.
	 */
	public static int HOME = 1;
	/**
	 * Indicates that the patient page panel should be displayed.
	 */
	public static int PATIENT = 2;
	
	/**
	 * The current panel being displayed
	 */
	private int state;
	
	private TCPUIClient tcpClient;
	private Doctor doctor;
	private Patient patient;
	
	//gui parts
	private JFrame frame;
	private JPanel panel;
	private LoginPanel loginPanel;
	private HomePage homePanel;
	private PatientPanel patientPanel;
	
	public FrontendGUI(){
		try {
			tcpClient = new TCPUIClient("10.24.86.191", SmartPillDefaults.SERVER_PORT);
			Thread clientThread = new Thread(tcpClient);
			clientThread.start();
		} 
		catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Could not connect to server");
			System.exit(0);
			
		}
		
		loginPanel = new LoginPanel(this);
//		homePanel = new HomePage(this);
//		patientPanel = new PatientPanel(this);
		
		frame = new JFrame();
		panel = new JPanel(new BorderLayout());
		panel.add(loginPanel);
		state = LOGIN;
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 600);
		frame.setVisible(true);
		
	}
	
	/**
	 * Verify a username and password as being valid or not.
	 * @param username is the username that was chosen by the user.
	 * @param password is the password that the user associated with username.
	 * @return true if the username and password are valid false if not.
	 */
	public boolean verifyLogin(String username, String password){
		Doctor d = new Doctor(null, username, "", "", "", password);
		RetrievalMessage rMessage = new RetrievalMessage(Retrieve.DOC_CHECK_LOGIN, d);
		try {
			tcpClient.sendMessage(rMessage);
			Object obj = tcpClient.getResponse();
			if (obj instanceof Doctor){
				doctor = (Doctor) obj;
				return true;
			}
			else{
				return false;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Create a new user for the system.
	 * @param username used to identify the user.
	 * @param password used to secure the account.
	 * @param email email account associated with the user.
	 * @param firstName the user's given name.
	 * @param lastName the user's surname name.
	 * @return true if the account was successfully created false if not.
	 */
	public boolean verifyNewUser(String username, String password, String email, String firstName, String lastName){
		Doctor d = new Doctor(null, username, firstName + " " + lastName, email, "", password);
		DatabaseControl dbControl = new DatabaseControl(DatabaseControl.DbType.CREATE, d);
		try {
			tcpClient.sendMessage(dbControl);
			Object obj = tcpClient.getResponse();
			if (obj instanceof DatabaseControl){
				doctor = (Doctor) ((DatabaseControl) obj).object;
				return true;
			}
			else{
				return false;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Gets all patients belonging to a doctor whose names match the given regex expression.
	 * @param regex the expression used when determining which patients are to be returned.
	 * @return A List containing all patients whose name matches the regex expression provided.
	 */
	public List<Patient> getPatients(String regex){
		List<Patient> ret = new ArrayList<Patient>();
		
		RetrievalMessage rMessage = new RetrievalMessage(Retrieve.DOC_GET_PATIENTS, doctor);
		try {
			tcpClient.sendMessage(rMessage);
			Object obj = tcpClient.getResponse();
			if (obj instanceof List<?>){
				List<?> unknownList = (List<?>) obj;
				if (unknownList != null && unknownList.size() > 0){
					//add each patient that is in the unknownList that matches the regex expression to the ret list
					for (int i = 0; i < unknownList.size(); i++){
						if (unknownList.get(i) instanceof Patient){
							Patient temp = (Patient) unknownList.get(i);
							//regex check
							if (temp.name.split(regex).length > 1){
								ret.add(temp);
							}
						}
					}
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public List<Alert> getAlerts(){
//		//test code for adding alerts
//		Alert a = new Alert("Test", "Testing alert adding 4", AlertType.PATIENT, doctor);
//		DatabaseControl dbControl = new DatabaseControl(DatabaseControl.DbType.CREATE, a);
//		try {
//			tcpClient.sendMessage(dbControl);
//			Object obj = tcpClient.getResponse();
//			if (obj instanceof DatabaseControl){
//				Alert alert = (Alert) ((DatabaseControl) obj).object;
//			}
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		List<Alert> ret = new ArrayList<Alert>();
		
		RetrievalMessage rMessage = new RetrievalMessage(Retrieve.DOC_GET_ALERTS, doctor);
		try {
			tcpClient.sendMessage(rMessage);
			Object obj = tcpClient.getResponse();
			if (obj instanceof List<?>){
				List<?> unknownList = (List<?>) obj;
				if (unknownList != null && unknownList.size() > 0){
					//add all parts of unknownList that are Alerts to ret, they should be filtered for read and unread in db class
					for (int i = 0; i < unknownList.size(); i++){
						if (unknownList.get(i) instanceof Alert){
							ret.add((Alert) unknownList.get(i));
						}
					}
					
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * Marks an Alert as read in the database as of right now it is just being deleted rather than being marked as read though there 
	 * is no difference between the two from the GUI point of view.
	 * @param alert to be marked as read.
	 * @return Returns true if the Alert was properly marked as read and false if not.
	 */
	public boolean markAsRead(Alert alert){
		boolean ret = false;
		//deletes the Alert form the database
		DatabaseControl dbControl = new DatabaseControl(DatabaseControl.DbType.DELETE, alert);
		try {
			tcpClient.sendMessage(dbControl);
			Object obj = tcpClient.getResponse();
			if (obj instanceof DatabaseControl){
				//this will be true if it was removed
				ret = (Boolean) ((DatabaseControl) obj).object;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public boolean addPrescription(String prescriptionName, String link, int refillPeriod, String dosage, 
			String message, List<Pair<Integer, Integer>> times){
		
		Prescription pForAdding = null;
		boolean ret = true;
		Prescription p = new Prescription(prescriptionName, message, link, dosage, null, patient);
		//add the prescription for this patient to the database
		DatabaseControl dbControl = new DatabaseControl(DatabaseControl.DbType.CREATE, p);
		try {
			tcpClient.sendMessage(dbControl);
			Object obj = tcpClient.getResponse();
			if (obj instanceof DatabaseControl){
				pForAdding = (Prescription) ((DatabaseControl) obj).object;
			}
			else{
				return false;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		

		//add each time that the prescription is suppose to be taken to the db
		Calendar calendar = Calendar.getInstance();
		for (Pair<Integer, Integer> pair: times){
			calendar.set(0, 0, 0, pair.getLeft(), pair.getRight(), 0);
			Time t = new Time(calendar.getTime().getTime());
			PrescriptionDateTime pdt = new PrescriptionDateTime(pForAdding, t);
			
			dbControl = new DatabaseControl(DatabaseControl.DbType.CREATE, pdt);
			try {
				tcpClient.sendMessage(dbControl);
				Object obj = tcpClient.getResponse();
				if (!(obj instanceof DatabaseControl)){
					ret = false;
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	/**
	 * Gets the current state that the gui is in.
	 * @return The static int the represents the view that is currently being displayed.
	 */
	public int getState(){
		return state;
	}
	
	/**
	 * Sets the patient that the doctor is currently viewing.
	 * @param p is the patient that the doctor selected to view.
	 */
	public void setPatient(Patient p){
		patient = p;
		if (patientPanel == null){
			patientPanel = new PatientPanel(this);
		}
		patientPanel.setPatientInfo(p);
	}
	
	/**
	 * Adds a new patient that is associated with the currently active Doctor.
	 * @param firstName is the first name of the patient.
	 * @param lastName is the last name of the patient.
	 * @param email is the email of the patient.
	 * @param smsEmail is the sms email of the patient that can be used to receive text messages.
	 * @return True if the patient was added successfully false if not.
	 */
	public boolean addPatient(String firstName, String lastName, String email, String smsEmail){
		Patient p = new Patient(firstName + " " + lastName, email, smsEmail, doctor);
		DatabaseControl dbControl = new DatabaseControl(DatabaseControl.DbType.CREATE, p);
		try {
			tcpClient.sendMessage(dbControl);
			Object obj = tcpClient.getResponse();
			if (obj instanceof DatabaseControl){
				patient = (Patient) ((DatabaseControl) obj).object;
				if (patientPanel == null){
					patientPanel = new PatientPanel(this);
				}
				patientPanel.setPatientInfo(patient);
				return true;
			}
			else{
				return false;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Switches the panel that is currently in view for the user.
	 * @param view what view the gui should be showing to the user.
	 */
	public void setState(int view){
		panel.removeAll();
		if (view == LOGIN){
			panel.add(loginPanel, BorderLayout.CENTER);
			state = LOGIN;
		}
		else if (view == HOME){
			if (homePanel == null){
				homePanel = new HomePage(this);
			}
			panel.add(homePanel, BorderLayout.CENTER);
			state = HOME;
		}
		else if (view == PATIENT){
			if (patientPanel == null){
				patientPanel = new PatientPanel(this);
			}
			panel.add(patientPanel, BorderLayout.CENTER);
			state = PATIENT;
		}
		panel.revalidate();
		panel.repaint();
	}
	
	/**
	 * Gets the frame that is used for the GUI.
	 * @return Frame that is the parent of all panels used in the GUI.
	 */
	public JFrame getFrame(){
		return frame;
	}
	
	/**
	 * Gets the patient that is currently being viewed by the doctor.
	 * @return The patient that is currently being viewed by the doctor or null if no patient is currently being viewed.
	 */
	public Patient getCurrentPatient(){
		return patient;
	}
	
	/**
	 * Gets the doctor that is currently logged into the system.
	 * @return The doctor that is currently logged in or null if no doctor has logged into the system.
	 */
	public Doctor getDoctor(){
		return doctor;
	}
}
