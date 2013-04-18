package doctor;

import java.io.IOException;

import shared.Patient;
import shared.SmartPillDefaults;
import shared.TCPClient;

public class TCPEchoClient extends TCPClient {
	
	public static void main(String[] args) throws IOException {
		TCPEchoClient client = new TCPEchoClient("localhost", SmartPillDefaults.SERVER_PORT);
		Thread clientThread = new Thread(client);
		clientThread.start();
		
		System.out.println("Connected.");
		while (true)
		{
			try {
				Thread.sleep(1000);
				client.sendMessage(new Patient("Name", "Email", "SmsEmail"));
			} catch (InterruptedException e) {
			}
		}
	}

	public TCPEchoClient(String IP, int port) throws IOException {
		super(IP, port);
	}

	@Override
	public void receivedObject(Object obj, TCPClient client) {
		System.out.println("Received: " + obj.getClass().getName());
	}

}
