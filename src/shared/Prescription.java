package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Prescription implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1953915730551828590L;

	public Integer id;

	public String name;

	public String message;

	public String picturePath;

	public String dosage;

	public Patient for_patient;

	// public List<PrescriptionDateTime> dateTimes;

	public Prescription(String name, String description, String picturePath,
			String dosage, List<PrescriptionDateTime> dateTimes,
			Patient for_patient) {
		this(null, name, description, picturePath, dosage, dateTimes,
				for_patient);
	}

	public Prescription(Integer id, String name, String description,
			String picturePath, String dosage,
			List<PrescriptionDateTime> dateTimes, Patient for_patient) {
		this.id = id;
		this.name = name;
		this.message = description;
		this.picturePath = picturePath;
		this.dosage = dosage;
		this.for_patient = for_patient;
		// this.dateTimes = dateTimes;
	}
	
	@Override
	public String toString() {
		return name + " " + dosage;
	}
}
