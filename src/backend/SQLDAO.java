package backend;

import java.sql.SQLException;
import java.util.List;

public interface SQLDAO<T, PrimaryKey> {
	public void insert(T dao) throws SQLException;

	public void update(T dao) throws SQLException;

	public void delete(T dao) throws SQLException;

	public List<T> findAll() throws SQLException;

	public T findByPrimaryKey(PrimaryKey key) throws SQLException;
}
