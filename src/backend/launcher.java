package backend;

import java.sql.SQLException;
import java.util.List;

import shared.Doctor;

public class launcher {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {
		try {
			testDoctor();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
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
		
		docDAO.delete(doc);
	}

}
