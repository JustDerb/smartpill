package shared;

import java.io.Serializable;
import java.sql.Timestamp;

public class Alert implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2159559099609398072L;

	public Integer id;

	public enum AlertType {
		PATIENT, SYSTEM, INFO, ERROR, DOCTOR
	};

	protected AlertType type;

	public void setType(AlertType type) {
		this.type = type;
	}

	public AlertType getType() {
		return this.type;
	}

	public String name;

	public String description;

	public Timestamp date;

	public boolean read;

	public Doctor forDoctor;

	public Alert(String title, String message, AlertType type, Doctor forDoctor) {
		this(null, title, message, type, new Timestamp(
				System.currentTimeMillis()), false, forDoctor);
	}

	public Alert(Integer id, String title, String message, AlertType type,
			Timestamp date, boolean read, Doctor forDoctor) {
		this.id = id;
		this.setType(type);
		this.name = title;
		this.description = message;
		this.date = date;
		this.read = read;
		this.forDoctor = forDoctor;
	}
}
