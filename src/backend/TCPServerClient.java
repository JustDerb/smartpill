package backend;

import java.io.IOException;
import java.net.Socket;

import shared.*;

public class TCPServerClient extends TCPClient {

	public TCPServerClient(Socket socket) throws IOException {
		super(socket, true);
	}

	@Override
	public void receivedObject(Object obj, TCPClient client) {
		if (obj instanceof Patient) {
			Patient p = (Patient)obj;
			System.out.println("Received Patient: " + p.name);
		} else if (obj instanceof DatabaseControl) {
			DatabaseControl dbc = (DatabaseControl)obj;
			System.out.println("Received DatabaseControl: " + dbc.action.name());
		} else {
			System.out.println("Received: " + obj.getClass().getName());
		}
	}

}
