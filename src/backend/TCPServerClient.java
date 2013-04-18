package backend;

import java.io.IOException;
import java.net.Socket;

import shared.TCPClient;

public class TCPServerClient extends TCPClient {

	public TCPServerClient(Socket socket) throws IOException {
		super(socket, true);
	}

	@Override
	public void receivedObject(Object obj, TCPClient client) {
		System.out.println("Received: " + obj.getClass().getName());
	}

}
