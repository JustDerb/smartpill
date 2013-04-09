package shared;

import java.io.Serializable;

public class Doctor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7202015027500662758L;

	public Integer id;
	
	public String username;
	
	public String name;
	
	public String email;
	
	public Doctor() {
		this("", "", "");
	}
	
	public Doctor(String username, String name, String email) {
		this(null, username, name, email);
	}
	
	public Doctor(Integer id, String username, String name, String email) {
		this.id = null;
		this.username = username;
		this.name = name;
		this.email = email;
	}
}