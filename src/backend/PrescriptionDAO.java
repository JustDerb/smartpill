package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shared.Prescription;
import shared.PrescriptionDateTime;

public class PrescriptionDAO implements SQLDAO<Prescription, Integer> {

	private static final String TABLE = "prescription";
	
	@Override
	public Prescription insert(Prescription dao) throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(PrescriptionDAO.TABLE);
		sb.append(" ( `id`,   `name`, `message`, `picture_path`, `dosage` ) ");
		sb.append(" VALUES ");
		sb.append(" ( NULL,    ?,      ?,         ?,              ?       );");

		PreparedStatement ps = conn.prepareStatement(sb.toString(),
				Statement.RETURN_GENERATED_KEYS);

		ps.setString(1, dao.name);
		ps.setString(2, dao.message);
		ps.setString(3, dao.picturePath);
		ps.setString(4, dao.dosage);
		ps.setInt(3, dao.id);

		// Try and add it
		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			// Retrieve the newly created id and pass back the Alert object
			dao.id = rs.getInt(1);
		} else
			throw new SQLException("Could not retrieve updated values");
		
		// Create any Date/Times
		PrescriptionDateTimeDAO pdtDAO = new PrescriptionDateTimeDAO();
		for (PrescriptionDateTime pdt : dao.dateTimes)
		{
			pdt.forPrescription = dao.id;
			pdtDAO.insert(pdt);
		}
		
		return dao;
	}

	@Override
	public boolean update(Prescription dao) throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(PrescriptionDAO.TABLE);
		sb.append(" SET ");
		sb.append("name = ?, ");
		sb.append("message = ?, ");
		sb.append("picture_path = ?, ");
		sb.append("dosage = ? ");
		sb.append(" WHERE id = ?");

		PreparedStatement ps = conn.prepareStatement(sb.toString());

		ps.setString(1, dao.name);
		ps.setString(2, dao.message);
		ps.setString(3, dao.picturePath);
		ps.setString(4, dao.dosage);
		ps.setInt(5, dao.id);

		// Try and add it
		ps.execute();
		
		// TODO: Remove or create any Date/Times
		// Update any Date/Times
		PrescriptionDateTimeDAO pdtDAO = new PrescriptionDateTimeDAO();
		for (PrescriptionDateTime pdt : dao.dateTimes)
		{
			pdtDAO.update(pdt);
		}

		return ps.getUpdateCount() > 0;
	}

	@Override
	public boolean delete(Prescription dao) throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(PrescriptionDAO.TABLE);
		sb.append(" WHERE id = ?");

		PreparedStatement ps = conn.prepareStatement(sb.toString());

		ps.setInt(1, dao.id);

		// Try and add it
		ps.execute();

		return ps.getUpdateCount() > 0;
	}

	@Override
	public List<Prescription> findAll() throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id FROM ");
		sb.append(PrescriptionDAO.TABLE);
		PreparedStatement ps = conn.prepareStatement(sb.toString());

		// Try and add it
		ResultSet result = ps.executeQuery();

		List<Prescription> list = new ArrayList<Prescription>();

		while (result.next()) {
			list.add(findByPrimaryKey(result.getInt("id")));
		}

		return list;
	}

	@Override
	public Prescription findByPrimaryKey(Integer key) throws SQLException {
		Connection conn = SQLDatabase.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ");
		sb.append(PrescriptionDAO.TABLE);
		sb.append(" WHERE id = ?");
		PreparedStatement ps = conn.prepareStatement(sb.toString());

		ps.setInt(1, key);

		// Try and add it
		ResultSet result = ps.executeQuery();

		if (result.next()) {
			Integer prescriptionId = result.getInt("id");
			return new Prescription(prescriptionId,
					result.getString("name"),
					result.getString("message"),
					result.getString("picture_path"),
					result.getString("dosage"),
					grabTimes(prescriptionId));
		} else
			return null;
	}
	
	private List<PrescriptionDateTime> grabTimes(Integer prescriptionID) throws SQLException {
		return (new PrescriptionDateTimeDAO()).findByPrescriptionKey(prescriptionID);
	}

}
