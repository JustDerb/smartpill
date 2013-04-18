package shared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public abstract class TCPClient implements Runnable {
	private Socket sock;

	private ObjectOutputStream outToClient;
	private ObjectInputStream inFromClient;

	public TCPClient(String IP, int port) throws IOException {
		this(new Socket(IP, port), false);
	}

	public TCPClient(Socket socket, boolean onServer) throws IOException {
		this.sock = socket;

		if (onServer) {
			this.outToClient = new ObjectOutputStream(
					this.sock.getOutputStream());
			this.inFromClient = new ObjectInputStream(
					this.sock.getInputStream());
		} else {
			this.inFromClient = new ObjectInputStream(
					this.sock.getInputStream());
			this.outToClient = new ObjectOutputStream(
					this.sock.getOutputStream());
		}
	}

	public Socket getSocket() {
		return this.sock;
	}

	public void sendMessage(Serializable s) throws IOException {
		if (!this.sock.isClosed())
			this.outToClient.writeObject(s);
		else
			throw new IOException("Socket closed.");
	}
	
	public void trySendMessage(Serializable s) {
		try {
			this.sendMessage(s);
		} catch (IOException e) {
		}
	}

	public void close() {
		tryClose();
	}

	@Override
	public void run() {
		while (!this.sock.isClosed()) {
			Object s = null;
			try {
				s = this.inFromClient.readObject();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				tryClose();
			}

			if (s != null) {
				receivedObject(s, this);
			}
		}

		tryClose();
	}

	public abstract void receivedObject(Object obj, TCPClient client);

	private void tryClose() {
		try {
			this.sock.close();
		} catch (IOException e1) {
		}
	}
}
