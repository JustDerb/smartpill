package doctor;

import java.io.IOException;

import shared.DatabaseControl;
import shared.PillMessage;
import shared.TCPClient;

public class TCPUIClient extends TCPClient {

	public TCPUIClient(String IP, int port) throws IOException {
		super(IP, port);
	}

	@Override
	public void receivedObject(Object obj, TCPClient client) {
		if (obj instanceof PillMessage) {
			// Received general message
			PillMessage msg = (PillMessage) obj;
			if (msg.isSuccess) {
				// Do something...
			} else {
				// Show pop up with error (But, do not block!)
			}
		}
		else if (obj instanceof DatabaseControl) {
			// Received a successful CREATE, DELETE, UPDATE call
			DatabaseControl control = (DatabaseControl)obj;
		}
	}

}
