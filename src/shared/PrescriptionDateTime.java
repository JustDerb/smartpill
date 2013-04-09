package shared;

import java.io.Serializable;
import java.sql.Time;
import java.util.HashMap;

public class PrescriptionDateTime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -823124324629426109L;

	public Integer id;
	
	/**
	 * Do not use right now, since it doesn't work
	 */
	//public HashMap<String, Integer> repeat;
	
	public Time timeOfDay;
	
	public PrescriptionDateTime() {
		this(null, new Time(System.currentTimeMillis()));
	}
	
	public PrescriptionDateTime(Time timeOfDay) {
		this(null, timeOfDay);
	}
	
	public PrescriptionDateTime(Integer id, Time timeOfDay) {
		this.id = id;
		this.timeOfDay = timeOfDay;
	}
}
