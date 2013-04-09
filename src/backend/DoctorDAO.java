package backend;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import shared.Doctor;

public class DoctorDAO implements SQLDAO<Doctor, Integer> {

	private static final String TABLE = "";

	private static String getHash(String password, String salt) {
		String hash = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte[] hashBytes = md.digest((password + salt).getBytes());

			StringBuffer sb = new StringBuffer();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// Don't have it....
			// THis is bad for security but...
			hash = password + salt;
		}
		return hash;
	}

	@Override
	public Doctor insert(Doctor dao) throws SQLException {
		return this.insert(dao, "");
	}

	public Doctor insert(Doctor dao, String password) throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		// Generate our hash
		SecureRandom random = new SecureRandom();
		String salt = new BigInteger(130, random).toString(32);
		String hash = DoctorDAO.getHash(password, salt);

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(DoctorDAO.TABLE);
		sb.append(" ( id,   username, password, salt, name, email ) ");
		sb.append("VALUES");
		sb.append(" ( NULL, ?,        ?,        ?,    ?,    ?     );");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		
		ps.setString(0, dao.username);
		ps.setString(1, hash);
		ps.setString(2, salt);
		ps.setString(3, dao.name);
		ps.setString(4, dao.email);
		
		// Try and add it
		ps.execute();
		
		return findByUsername(dao.username);
	}

	public boolean isCorrectLogin(String username, String password) {
		return false;
	}

	@Override
	public boolean update(Doctor dao) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Doctor dao) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Doctor> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Doctor findByPrimaryKey(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Doctor findByUsername(String username) {
		return null;
	}

}
