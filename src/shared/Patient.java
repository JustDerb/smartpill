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
	
	public String smsEmail;
	
	public Patient(String name, String email, String smsEmail) {
		this(null, name, email, smsEmail);
	}
	
	public Patient(Integer id, String name, String email, String smsEmail) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.smsEmail = smsEmail;
	}
}
