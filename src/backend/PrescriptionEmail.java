package backend;

import java.io.Serializable;
import java.sql.Timestamp;

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

	public PrescriptionEmail(PrescriptionDateTime forPrescriptionDateTime) {
		this(forPrescriptionDateTime, false);
	}

	public PrescriptionEmail(PrescriptionDateTime forPrescriptionDateTime,
			boolean read) {
		this(null, forPrescriptionDateTime, read, new Timestamp(
				System.currentTimeMillis()));
	}

	public PrescriptionEmail(Integer id,
			PrescriptionDateTime forPrescriptionDateTime, boolean read,
			Timestamp dateTime) {
		this.id = id;
		this.forPrescriptionDateTime = forPrescriptionDateTime;
		this.read = read;
		this.dateTime = dateTime;
	}

}
