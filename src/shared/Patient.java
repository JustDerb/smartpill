package shared;

import java.io.Serializable;

public class Patient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2788852993981521151L;

	public Integer id;
	
	public String name;
	
	public String email;
	
	public Patient() {
		this("", "");
	}
	
	public Patient(String name, String email) {
		this(null, name, email);
	}
	
	public Patient(Integer id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
}
