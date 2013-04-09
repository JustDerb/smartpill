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

	public List<PrescriptionDateTime> dateTimes;

	public Prescription() {
		this("", "", "", "", new ArrayList<PrescriptionDateTime>());
	}

	public Prescription(String name, String description, String picturePath,
			String dosage, List<PrescriptionDateTime> dateTimes) {
		this(null, name, description, picturePath, dosage, dateTimes);
	}

	public Prescription(Integer id, String name, String description,
			String picturePath, String dosage, List<PrescriptionDateTime> dateTimes) {
		this.id = id;
		this.name = name;
		this.message = description;
		this.picturePath = picturePath;
		this.dosage = dosage;
		this.dateTimes = dateTimes;
	}
}
