package shared;

import java.io.Serializable;

public class DatabaseControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1526489888325105178L;

	public enum DbType {
		CREATE,
		RETRIEVE,
		UPDATE,
		DELETE
	}
	
	public Serializable object;
	
	public DbType action;
	
	public DatabaseControl(DbType action, Serializable object) {
		this.action = action;
		this.object = object;
	}

}
