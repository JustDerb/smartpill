package shared;

import java.io.Serializable;

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
	
	public Prescription() {
		this("", "", "", "");
	}
	
	public Prescription(String name, String description, String picturePath, String dosage) {
		this(null, name, description, picturePath, dosage);
	}
	
	public Prescription(Integer id, String name, String description, String picturePath, String dosage) {
		this.id = id;
		this.name = name;
		this.message = description;
		this.picturePath = picturePath;
		this.dosage = dosage;
	}
}
