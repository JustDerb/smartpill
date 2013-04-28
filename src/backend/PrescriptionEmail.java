package backend;

import java.io.Serializable;
import java.sql.Timestamp;

import shared.Doctor;
import shared.PrescriptionDateTime;

public class PrescriptionEmail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6861573974609989684L;

	public Integer id;

	public boolean read;

	public PrescriptionDateTime forPrescriptionDateTime;

	public Timestamp dateTime;
	
	public boolean doctor_alerted;
	
	public Doctor for_doctor;

	public PrescriptionEmail(PrescriptionDateTime forPrescriptionDateTime, Doctor for_doctor) {
		this(forPrescriptionDateTime, for_doctor, false);
	}

	public PrescriptionEmail(PrescriptionDateTime forPrescriptionDateTime, Doctor for_doctor,
			boolean read) {
		this(null, forPrescriptionDateTime, for_doctor, read, new Timestamp(
				System.currentTimeMillis()), read);
	}

	public PrescriptionEmail(Integer id,
			PrescriptionDateTime forPrescriptionDateTime, Doctor for_doctor, boolean read,
			Timestamp dateTime,
			boolean doctor_alerted) {
		this.id = id;
		this.forPrescriptionDateTime = forPrescriptionDateTime;
		this.read = read;
		this.dateTime = dateTime;
		this.doctor_alerted = doctor_alerted;
		this.for_doctor = for_doctor;
	}

}
