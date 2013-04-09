package backend;

import java.sql.SQLException;
import java.util.List;

public interface SQLDAO<T, PrimaryKey> {
	public T insert(T dao) throws SQLException;

	public boolean update(T dao) throws SQLException;

	public boolean delete(T dao) throws SQLException;

	public List<T> findAll() throws SQLException;

	public T findByPrimaryKey(PrimaryKey key) throws SQLException;
}
