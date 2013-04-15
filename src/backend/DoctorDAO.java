package backend;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shared.Doctor;

public class DoctorDAO implements SQLDAO<Doctor, Integer> {

	private static final String TABLE = "doctors";
	
	public void eraseTable() throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("TRUNCATE TABLE ");
		sb.append(DoctorDAO.TABLE);
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		
		ps.execute();
	}

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
		sb.append(" VALUES ");
		sb.append(" ( NULL, ?,        ?,        ?,    ?,    ?     );");
		PreparedStatement ps = conn.prepareStatement(sb.toString());

		ps.setString(1, dao.username);
		ps.setString(2, hash);
		ps.setString(3, salt);
		ps.setString(4, dao.name);
		ps.setString(5, dao.email);

		// Try and add it
		ps.execute();

		return findByUsername(dao.username);
	}

	public boolean isCorrectLogin(String username, String password)
			throws SQLException {
		Doctor doc = findByUsername(username);
		if (doc == null)
			return false;
		else {
			String hash = DoctorDAO.getHash(password, doc.salt);

			if (hash.equals(doc.password))
				return true;
			else
				return false;
		}
	}

	@Override
	public boolean update(Doctor dao) throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(DoctorDAO.TABLE);
		sb.append(" SET ");
		sb.append("username = ?, ");
		sb.append("password = ?, ");
		sb.append("salt = ?, ");
		sb.append("name = ?, ");
		sb.append("email = ? ");
		sb.append(" WHERE id = ?");
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());

		ps.setString(1, dao.username);
		ps.setString(2, dao.password);
		ps.setString(3, dao.salt);
		ps.setString(4, dao.name);
		ps.setString(5, dao.email);
		ps.setInt(6, dao.id);

		// Try and add it
		ps.execute();

		return ps.getUpdateCount() > 0;
	}

	@Override
	public boolean delete(Doctor dao) throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(DoctorDAO.TABLE);
		sb.append(" WHERE id = ?");
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());

		ps.setInt(1, dao.id);

		// Try and add it
		ps.execute();

		return ps.getUpdateCount() > 0;
	}

	@Override
	public List<Doctor> findAll() throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id FROM ");
		sb.append(DoctorDAO.TABLE);
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		
		// Try and add it
		ResultSet result = ps.executeQuery();
		
		List<Doctor> list = new ArrayList<Doctor>();
		
		while (result.next()) {
			list.add(findByPrimaryKey(result.getInt("id")));
		}

		return list;
	}

	@Override
	public Doctor findByPrimaryKey(Integer key) throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ");
		sb.append(DoctorDAO.TABLE);
		sb.append(" WHERE id = ?");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		
		ps.setInt(1, key);
		
		// Try and add it
		ResultSet result = ps.executeQuery();

		if (result.next()) {
			return new Doctor(
					result.getInt("id"),
					result.getString("username"),
					result.getString("name"),
					result.getString("email"),
					result.getString("salt"),
					result.getString("password"));
		}
		else
			return null;
	}

	public Doctor findByUsername(String username) throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ");
		sb.append(DoctorDAO.TABLE);
		sb.append(" WHERE username = ?");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		
		ps.setString(1, username);
		
		// Try and add it
		ResultSet result = ps.executeQuery();

		if (result.next()) {
			return new Doctor(
					result.getInt("id"),
					result.getString("username"),
					result.getString("name"),
					result.getString("email"),
					result.getString("salt"),
					result.getString("password"));
		}
		else
			return null;
	}

}
