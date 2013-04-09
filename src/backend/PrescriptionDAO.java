package backend;

import java.sql.SQLException;
import java.util.List;

import shared.Prescription;

public class PrescriptionDAO implements SQLDAO<Prescription, Integer> {

	@Override
	public Prescription insert(Prescription dao) throws SQLException {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(Prescription dao) throws SQLException {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Prescription dao) throws SQLException {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Prescription> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Prescription findByPrimaryKey(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
