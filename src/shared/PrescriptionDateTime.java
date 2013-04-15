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
	
	public Integer forPrescription;
	
	/**
	 * Do not use right now, since it doesn't work
	 */
	//public HashMap<String, Integer> repeat;
	
	public Time timeOfDay;
	
	public PrescriptionDateTime(Prescription forPrescription) {
		this(forPrescription, new Time(System.currentTimeMillis()));
	}
	
	public PrescriptionDateTime(Prescription forPrescription, Time timeOfDay) {
		this(null, forPrescription.id, timeOfDay);
	}
	
	public PrescriptionDateTime(Integer id, Integer forPrescription, Time timeOfDay) {
		this.id = id;
		this.timeOfDay = timeOfDay;
		this.forPrescription = forPrescription;
	}
}
