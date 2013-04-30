package backend;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

public class GmailIMAPClient {
	private Thread emailThread;

	private GmailWatcher watcher;

	private OutgoingMail outMail;

	public GmailIMAPClient(String username, String password) {
		this.watcher = new GmailWatcher(username, password);
		this.emailThread = new Thread(this.watcher);
		this.outMail = new OutgoingMail(username, password);
	}

	public void SendEmail(String to, String subject, String bodyText)
			throws MessagingException {
		this.outMail.sendMessage(to, subject, bodyText);
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
			this.shouldStop = false;
			this.running = true;

			// Open the email inbox
			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", "imaps");
			Store store = null;
			while (!this.shouldStop) {
				try {
					Session session = Session.getDefaultInstance(props, null);
					store = session.getStore("imaps");
					store.connect("imap.gmail.com", this.username,
							this.password);

					while (store.isConnected()) {
						Folder inbox = store.getFolder("inbox");
						inbox.open(Folder.READ_WRITE);
						// Get un-seen (unread) messages
						FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN),
								false);
						Message messages[] = inbox.search(ft);

						for (Message msg : messages) {
							System.out.println("Got email!");
							// Print our addresses
							Address[] from = msg.getFrom();
							for (Address addy : from) {
								InternetAddress IMAPaddy = (InternetAddress) addy;
								System.out.println("From: "
										+ IMAPaddy.getAddress());
							}

							// Grab our body
							ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
							String body = "";
							try {
								msg.writeTo(outputStream);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							// Convert to a string
							body = outputStream.toString();
							// Analyze it
							if (PrescriptionEmailMessage.markEmailRead(body))
								System.out.println("Got prescription taken email.");
							else
								System.out.println("Got email, but didn't find PID!");

							// Object content = null;
							// try {
							// content = msg.getContent();
							// } catch (IOException e) {
							// e.printStackTrace();
							// }
							// if (content != null) {
							// String body = "";
							// if (content instanceof MimeMultipart) {
							// MimeMultipart contentMulti =
							// (MimeMultipart)content;
							// int bodyCount = contentMulti.getCount();
							// for (int i = 0; i < bodyCount; ++i)
							// {
							// BodyPart bp = contentMulti.getBodyPart(i);
							// }
							// } else if (msg.getContentType().toLowerCase()
							// .startsWith("text")) {
							// body = content.toString();
							// }
							//
							// // Get the body to see if we can find our
							// // PillMessageId
							// PrescriptionEmailMessage.markEmailRead(body);
							// }
						}

						inbox.close(false);
						// Wait 5 seconds
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
						}
					}
					
					store.close();
					// Wait 5 seconds
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}

				} catch (NoSuchProviderException e) {
					e.printStackTrace();
					this.running = false;
				} catch (MessagingException e) {
					e.printStackTrace();
					this.running = false;
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
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
