package doctor;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import shared.Alert;
import shared.Alert.AlertType;
import shared.DatabaseControl;
import shared.Doctor;
import shared.Patient;
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
			tcpClient = new TCPUIClient("10.30.10.78", SmartPillDefaults.SERVER_PORT);
			Thread clientThread = new Thread(tcpClient);
			clientThread.start();
		} 
		catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Could not connect to server");
			System.exit(0);
			
		}
		loginPanel = new LoginPanel(this);
		homePanel = new HomePage(this);
		patientPanel = new PatientPanel(this);
		
		frame = new JFrame();
		panel = new JPanel(new BorderLayout());
		panel.add(loginPanel);
		state = LOGIN;
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
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
	
	public String[] getPatients(String regex){
		//TODO
		int size = 40;
		String ret[] = new String[size];
		for (int i = 0; i < size; i++){
			ret[i] = "patient" + i;
		}
		return ret;
	}
	
	public Alert[] getAlerts(){
		//TODO
		int size = 3;
		Alert ret[] = new Alert[size];
		for (int i = 0; i < size; i++){
			ret[i] = new Alert("Patient" + i, "Not replying", AlertType.PATIENT, doctor);
		}
		return ret;
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
			panel.add(homePanel, BorderLayout.CENTER);
			state = HOME;
		}
		else if (view == PATIENT){
			panel.add(patientPanel, BorderLayout.CENTER);
			state = PATIENT;
		}
		panel.revalidate();
		panel.repaint();
	}
	
	/**
	 * Gets the current state that the gui is in.
	 * @return The static int the represents the view that is currently being displayed.
	 */
	public int getState(){
		return state;
	}
	
	public void setPatient(String name){
		//TODO
	}
	
	public boolean addPatient(String firstName, String lastName, String email, String smsEmail){
		Patient p = new Patient(firstName + " " + lastName, email, smsEmail, doctor);
		DatabaseControl dbControl = new DatabaseControl(DatabaseControl.DbType.CREATE, p);
		try {
			tcpClient.sendMessage(dbControl);
			Object obj = tcpClient.getResponse();
			if (obj instanceof DatabaseControl){
				patient = (Patient) ((DatabaseControl) obj).object;
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
