package doctor;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import shared.DatabaseControl;
import shared.PillMessage;
import shared.TCPClient;

public class TCPUIClient extends TCPClient {

	private LinkedBlockingQueue<Object> queue;
	
	public TCPUIClient(String IP, int port) throws IOException {
		super(IP, port);
		queue = new LinkedBlockingQueue<Object>();
	}
	
	public Object getResponse()
	{
		try{
			return queue.take();
		} 
		catch (InterruptedException e){
			return null;
		}
	}

	@Override
	public void receivedObject(Object obj, TCPClient client) {
		try {
			queue.put(obj);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		if (obj instanceof PillMessage) {
//			// Received general message
//			PillMessage msg = (PillMessage) obj;
//			if (msg.isSuccess) {
//				// Do something...
//			} else {
//				// Show pop up with error (But, do not block!)
//			}
//		}
//		else if (obj instanceof DatabaseControl) {
//			// Received a successful CREATE, DELETE, UPDATE call
//			DatabaseControl control = (DatabaseControl)obj;
//		}
	}

}
