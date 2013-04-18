package shared;

import java.io.Serializable;

public class RetrievalMessage {

	// TODO: Make these sub classes for easier argument population
	public enum Retrieve {
		DOC_CHECK_LOGIN,
		DOC_GET_ALERTS,
		DOC_GET_PATIENTS,
		PATIENT_GET_PRESCRIPTION
	}
	
	public Serializable obj;
	
	public Retrieve action;
	
	public RetrievalMessage(Retrieve action, Serializable obj)
	{
		this.action = action;
		this.obj = obj;
	}
}
