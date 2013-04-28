package backend;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import nogit.EmailCreds;

import backend.DAO.AlertDAO;
import backend.DAO.DoctorDAO;

import shared.Alert;
import shared.Doctor;
import shared.SmartPillDefaults;

public class launcher {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException {
		TCPServer server = new TCPServer(SmartPillDefaults.SERVER_PORT);
		Thread serverThread = new Thread(server);
		serverThread.start();
		
		GmailIMAPClient emailClient = new GmailIMAPClient(EmailCreds.USERNAME, EmailCreds.PASSWORD);
		EmailScheduler scheduler = new EmailScheduler(emailClient);
		
		emailClient.start();
		scheduler.start();
		
		System.out.println("Listening...");
		
		while (true) ;
	}
	
	private static void testAlert() throws SQLException {
		Doctor doc = new Doctor("DocAlert", "Doctor Name", "doctor@email.com");
		DoctorDAO docDAO = new DoctorDAO();
		doc = docDAO.insert(doc, "blah");
		//String title, String message, AlertType type, Doctor forDoctor
		Alert alert = new Alert("This is a title", "This is a message", Alert.AlertType.DOCTOR, doc);
		AlertDAO alertDAO = new AlertDAO();
		alertDAO.eraseTable();
		alert = alertDAO.insert(alert);

		
		
		if (!docDAO.delete(doc))
			throw new IllegalArgumentException("Could not delete");
	}
	
	private static void testDoctor() throws SQLException {
		String username = "testUsername";
		String password = "testPassword";
		Doctor doc = new Doctor(username, "Doctor Name", "doctor@email.com");
		DoctorDAO docDAO = new DoctorDAO();
		docDAO.eraseTable();
		doc = docDAO.insert(doc, password);
		
		if (!docDAO.isCorrectLogin(username, password))
			throw new IllegalArgumentException("Wrong password");
		
		Doctor docSame = docDAO.findByUsername(username);
		if (!doc.equals(docSame))
			throw new IllegalArgumentException("Wrong doc");
		
		List<Doctor> docList = docDAO.findAll();
		if (docList.size() != 1)
			throw new IllegalArgumentException("Not one doc");
		
		doc.name = "UPDATEDNAME";
		if (!docDAO.update(doc))
			throw new IllegalArgumentException("Could not update");
		
		if (!docDAO.delete(doc))
			throw new IllegalArgumentException("Could not delete");
	}

}
