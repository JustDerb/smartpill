package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import shared.Alert;
import shared.Prescription;
import shared.PrescriptionDateTime;
import backend.DAO.AlertDAO;
import backend.DAO.PrescriptionDAO;
import backend.DAO.PrescriptionDateTimeDAO;
import backend.DAO.PrescriptionEmailDAO;

public class EmailScheduler {
	private final ScheduledExecutorService service;
	
	private static final int SEC_DELAY = 30;

	// 1 days
	private static final int SEC_ALERT_DOCTOR = 24 * 60 * 60;

	private GmailIMAPClient client;

	private Thread schedThread;
	private Emailer schedObj;

	public EmailScheduler(GmailIMAPClient client) {
		this.client = client;
		this.schedObj = new Emailer();
		this.schedThread = new Thread(this.schedObj);
		this.service = Executors.newScheduledThreadPool(20);
	}

	public boolean start() {
		if (!this.schedObj.isRunning()) {
			this.schedThread.start();
			return true;
		} else {
			return false;
		}
	}

	public boolean stop(boolean wait) {
		if (!this.schedObj.isRunning()) {
			return false;
		} else {
			this.schedObj.stop();
			if (wait) {
				try {
					this.schedThread.join();
				} catch (InterruptedException e) {
					// Quite!
				}
			}
			return true;
		}
	}

	private class Emailer implements Runnable {

		private boolean running;

		private boolean shouldStop;

		public Emailer() {
			this.running = false;
			this.shouldStop = false;
		}

		@Override
		public void run() {
			this.shouldStop = false;
			this.running = true;

			while (!this.shouldStop) {
				checkAndAlertDoctors();
				checkAndSendEmails();

				// Wait SEC_DELAY seconds
				try {
					Thread.sleep(SEC_DELAY * 1000);
				} catch (InterruptedException e) {
				}
			}

			this.running = false;
		}

		private void checkAndAlertDoctors() {
			Connection conn;
			try {
				conn = SQLDatabase.getConnection();

				PrescriptionEmailDAO peDb = new PrescriptionEmailDAO();
				List<PrescriptionEmail> toAlert = peDb
						.findAllNotReadOrAlerted(SEC_ALERT_DOCTOR);
				if (toAlert.size() > 0) {
					AlertDAO aDb = new AlertDAO();
					PrescriptionDAO pDb = new PrescriptionDAO();

					for (PrescriptionEmail email : toAlert) {
						Prescription p = pDb
								.findByPrimaryKey(email.forPrescriptionDateTime.forPrescription);
						StringBuilder message = new StringBuilder();
						message.append("Patient ");
						message.append(p.for_patient.name);
						message.append(" has not responded to their email about taking '");
						message.append(p.name);
						message.append("' (");
						message.append(p.dosage);
						message.append(") at ");
						message.append(email.dateTime.toString());
						Alert a = new Alert("No Email Response",
								message.toString(), Alert.AlertType.DOCTOR,
								email.for_doctor);
						try {
							aDb.insert(a);
							email.doctor_alerted = true;
							peDb.update(email);
						} catch (SQLException e) {
							System.err.println("checkAndAlertDoctors: " + e.getMessage());
						}
					}
				}

				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		private void checkAndSendEmails() {
			Connection conn;
			try {
				conn = SQLDatabase.getConnection();

				// Grab all reminders that are within 10 seconds of needing
				// to be sent
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT `id` ");
				sql.append("FROM  `prescription_meta` ");
				sql.append("WHERE TIME_TO_SEC(ABS(TIMEDIFF(CURTIME(), `day_time`))) < "
						+ SEC_DELAY + ";");

				PreparedStatement ps = conn.prepareStatement(sql.toString());

				ResultSet result = ps.executeQuery();
				PrescriptionDateTimeDAO pdtDb = new PrescriptionDateTimeDAO();
				PrescriptionEmailDAO peDb = new PrescriptionEmailDAO();

				while (result.next()) {
					PrescriptionDateTime pdt = pdtDb.findByPrimaryKey(result
							.getInt("id"));

					PrescriptionDAO pDb = new PrescriptionDAO();
					Prescription p = pDb.findByPrimaryKey(pdt.forPrescription);

					System.out.println("Sending reminder to "
							+ p.for_patient.name + " about " + p.name);
					service.execute(new PrescriptionEmailMessage(p, pdt,
							p.for_patient, client));
				}

				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		public boolean isRunning() {
			return this.running;
		}

		public void stop() {
			this.shouldStop = true;
		}
	}
}
