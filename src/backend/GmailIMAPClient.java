package backend;

public class GmailIMAPClient {

	
	
	private Thread emailThread;
	
	private GmailWatcher watcher;
	
	public GmailIMAPClient(String username, String password) {
		this.watcher = new GmailWatcher(username, password);
		this.emailThread = new Thread(this.watcher);
	}
	
	public boolean start() {
		if (!this.watcher.isRunning()) {
			this.emailThread.start();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean stop(boolean wait) {
		if (!this.watcher.isRunning()) {
			return false;
		} else {
			this.watcher.stop();
			if (wait) {
				try {
					this.emailThread.join();
				} catch (InterruptedException e) {
					// Quite!
				}
			}
			return true;
		}
	}
	
	private class GmailWatcher implements Runnable {

		private String username;
		
		private String password;
		
		private boolean running;
		
		private boolean shouldStop;
		
		public GmailWatcher(String username, String password) {
			this.username = username;
			this.password = password;
			this.running = false;
			this.shouldStop = false;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			this.shouldStop = false;
			this.running = true;
			
			while (!this.shouldStop) {
				
			}
			
			this.running = false;
		}
		
		public boolean isRunning() {
			return this.running;
		}
		
		public void stop() {
			this.shouldStop = true;
		}
	}
}
