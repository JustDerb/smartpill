package backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import shared.TCPClient;

public class TCPServer implements Runnable {
	private ServerSocket servSocket;

	private List<TCPClient> clients;
	
	public TCPServer(int port) throws IOException {
		this.servSocket = new ServerSocket(port);
		this.clients = new LinkedList<TCPClient>();
	}
	
	public void close() 
	{
		tryClose();
	}

	@Override
	public void run() {
		while (this.servSocket.isBound()) {
			try {
				Socket connectionSocket = servSocket.accept();
				
				System.out.println("Got connection!");
				// Create
				TCPServerClient client = new TCPServerClient(connectionSocket);
				this.clients.add(client);
				
				Thread thread = new Thread(client);
				thread.start();
				
			} catch (IOException e) {
				tryClose();
				break;
			}
		}
	}
	
	private void tryClose() 
	{
		for (TCPClient c : this.clients)
			c.close();
		
		try {
			this.servSocket.close();
		} catch (IOException e) {
		}
	}

}
