package backend;

import java.sql.SQLException;
import java.util.List;

import shared.PrescriptionDateTime;

public class PrescriptionDateTimeDAO implements SQLDAO<PrescriptionDateTime, Integer> {

	@Override
	public PrescriptionDateTime insert(PrescriptionDateTime dao) throws SQLException {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(PrescriptionDateTime dao) throws SQLException {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(PrescriptionDateTime dao) throws SQLException {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PrescriptionDateTime> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrescriptionDateTime findByPrimaryKey(Integer key)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
