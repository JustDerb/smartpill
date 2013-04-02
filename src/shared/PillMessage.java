package shared;

import java.io.Serializable;

public class PillMessage implements Serializable {

	/**
	 * Version for serializing objects
	 */
	private static final long serialVersionUID = -6297012409845844272L;
	
	public String message;
	
	public boolean isSuccess;
	
	public PillMessage(String message) {
		this(message, true);
	}
	
	public PillMessage(String message, boolean success) {
		this.message = message;
		this.isSuccess = success;
	}
}
