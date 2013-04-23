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
	
	public Doctor for_doctor;
	
	public Patient(String name, String email, String smsEmail, Doctor doctor) {
		this(null, name, email, smsEmail, doctor);
	}
	
	public Patient(Integer id, String name, String email, String smsEmail, Doctor doctor) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.smsEmail = smsEmail;
		this.for_doctor = doctor;
		if (this.for_doctor == null
				|| this.for_doctor.id == null)
			throw new IllegalArgumentException("Doctor object is invalid for Patient");
	}
}
