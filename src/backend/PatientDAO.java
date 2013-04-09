package backend;

import java.sql.SQLException;
import java.util.List;

import shared.Patient;

public class PatientDAO implements SQLDAO<Patient, Integer> {

	@Override
	public Patient insert(Patient dao) throws SQLException {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(Patient dao) throws SQLException {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Patient dao) throws SQLException {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Patient> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Patient findByPrimaryKey(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
