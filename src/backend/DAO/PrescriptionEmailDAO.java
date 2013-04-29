package backend.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import shared.Doctor;
import shared.PrescriptionDateTime;
import backend.PrescriptionEmail;
import backend.SQLDatabase;

public class PrescriptionEmailDAO implements SQLDAO<PrescriptionEmail, Integer> {

	private static final String TABLE = "email";

	@Override
	public PrescriptionEmail insert(PrescriptionEmail dao) throws SQLException {
		if (dao.forPrescriptionDateTime == null)
			throw new SQLException(
					"PrescriptionEmail.forPrescriptionDateTime cannot be null");
		if (dao.for_doctor == null)
			throw new SQLException(
					"PrescriptionEmail.for_doctor cannot be null");

		Connection conn = SQLDatabase.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ");
			sb.append(PrescriptionEmailDAO.TABLE);
			sb.append(" ( `id`,   `for_prescription_meta`, `read`, `added`, `doctor_alerted`, `for_doctor` ) ");
			sb.append(" VALUES ");
			sb.append(" ( NULL,    ?,                       ?,      NULL,    ?,                ?   );");

			PreparedStatement ps = conn.prepareStatement(sb.toString(),
					Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, dao.forPrescriptionDateTime.id);
			ps.setBoolean(2, dao.read);
			ps.setBoolean(3, dao.doctor_alerted);
			ps.setInt(4, dao.for_doctor.id);

			// Try and add it
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				// Retrieve the newly created id and pass back the object
				dao.id = rs.getInt(1);
				//dao.dateTime = rs.getTimestamp(2);
				return dao;
			} else
				throw new SQLException("Could not retrieve updated values");
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean update(PrescriptionEmail dao) throws SQLException {
		if (dao.forPrescriptionDateTime == null)
			throw new SQLException(
					"PrescriptionEmail.forPrescriptionDateTime cannot be null");
		if (dao.for_doctor == null)
			throw new SQLException(
					"PrescriptionEmail.for_doctor cannot be null");

		Connection conn = SQLDatabase.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE ");
			sb.append(PrescriptionEmailDAO.TABLE);
			sb.append(" SET ");
			sb.append("for_prescription_meta = ?, ");
			sb.append("`read` = ?, ");
			sb.append("`added` = ?, ");
			sb.append("`doctor_alerted` = ?, ");
			sb.append("`for_doctor` = ? ");
			sb.append(" WHERE `id` = ?");

			PreparedStatement ps = conn.prepareStatement(sb.toString());

			ps.setInt(1, dao.forPrescriptionDateTime.id);
			ps.setBoolean(2, dao.read);
			ps.setTimestamp(3, dao.dateTime);
			ps.setBoolean(4, dao.doctor_alerted);
			ps.setInt(5, dao.for_doctor.id);
			ps.setInt(6, dao.id);

			// Try and add it
			ps.execute();

			return ps.getUpdateCount() > 0;
		} finally {
			conn.close();
		}
	}

	@Override
	public boolean delete(PrescriptionEmail dao) throws SQLException {
		Connection conn = SQLDatabase.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM ");
			sb.append(PrescriptionEmailDAO.TABLE);
			sb.append(" WHERE `id` = ?");

			PreparedStatement ps = conn.prepareStatement(sb.toString());

			ps.setInt(1, dao.id);

			// Try and add it
			ps.execute();

			return ps.getUpdateCount() > 0;
		} finally {
			conn.close();
		}
	}

	@Override
	public ArrayList<PrescriptionEmail> findAll() throws SQLException {
		Connection conn = SQLDatabase.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT `id` FROM ");
			sb.append(PrescriptionEmailDAO.TABLE);
			PreparedStatement ps = conn.prepareStatement(sb.toString());

			// Try and add it
			ResultSet result = ps.executeQuery();

			ArrayList<PrescriptionEmail> list = new ArrayList<PrescriptionEmail>();

			while (result.next()) {
				list.add(findByPrimaryKey(result.getInt("id")));
			}

			return list;
		} finally {
			conn.close();
		}
	}

	@Override
	public PrescriptionEmail findByPrimaryKey(Integer key) throws SQLException {
		Connection conn = SQLDatabase.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM ");
			sb.append(PrescriptionEmailDAO.TABLE);
			sb.append(" WHERE `id` = ?");
			PreparedStatement ps = conn.prepareStatement(sb.toString());

			ps.setInt(1, key);

			// Try and add it
			ResultSet result = ps.executeQuery();

			PrescriptionDateTimeDAO pdtDb = new PrescriptionDateTimeDAO();
			DoctorDAO dDb = new DoctorDAO();

			if (result.next()) {
				PrescriptionDateTime pdt = pdtDb.findByPrimaryKey(result
						.getInt("for_prescription_meta"));
				Doctor doc = dDb.findByPrimaryKey(result.getInt("for_doctor"));
				return new PrescriptionEmail(result.getInt("id"), pdt, doc,
						result.getBoolean("read"),
						result.getTimestamp("added"),
						result.getBoolean("doctor_alerted"));
			} else
				return null;
		} finally {
			conn.close();
		}
	}

	public ArrayList<PrescriptionEmail> findAllNotReadOrAlerted(int numOfSeconds)
			throws SQLException {
		Connection conn = SQLDatabase.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT `id` FROM ");
			sb.append(PrescriptionEmailDAO.TABLE);
			sb.append(" WHERE `read` = FALSE ");
			sb.append(" AND `doctor_alerted` = FALSE ");
			sb.append(" AND ABS(UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(`added`)) > "
					+ numOfSeconds);
			PreparedStatement ps = conn.prepareStatement(sb.toString());

			// Try and add it
			ResultSet result = ps.executeQuery();

			ArrayList<PrescriptionEmail> list = new ArrayList<PrescriptionEmail>();

			while (result.next()) {
				list.add(findByPrimaryKey(result.getInt("id")));
			}

			return list;
		} finally {
			conn.close();
		}
	}
}
