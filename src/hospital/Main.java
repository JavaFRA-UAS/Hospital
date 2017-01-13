package hospital;

import java.nio.file.*;

import javax.swing.*;

import hospital.database.Database;
import hospital.database.DatabaseConnection;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.window.LoginWindow;

public class Main {

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		});

		Database db = Database.getInstance();

		try {
			DatabaseConnection.initialize();

			db.load();
			db.save();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Datenbank konnte nicht geöffnet werden: \n\n" + ex);
		}

		startSimulation();
		Database.getInstance().waitUntilReady();
		
		VitalsSimulation.initialize();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				LoginWindow mw = new LoginWindow();
				mw.setVisible(true);
			}
		});
	}

	private static void startSimulation() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Database db = Database.getInstance();

					// if there are no doctors in the database
					if (db.getDoctors().length == 0) {
						// ... add some doctors
						Doctor doc1 = new Doctor();
						doc1.setName("Dr. Heisenberg");
						db.addDoctor(doc1);

						Doctor doc2 = new Doctor();
						doc2.setName("Dr. Bergmann");
						db.addDoctor(doc2);

						Doctor doc3 = new Doctor();
						doc3.setName("Dr. Schwarzkopf");
						db.addDoctor(doc3);
					}

					// if there are no patients in the database
					if (db.getPatients().length == 0) {
						// ... add some patients
						Patient pat1 = new Inpatient();
						pat1.setName("Fr. Hildegard");
						db.addPatient(pat1);

						Patient pat2 = new Outpatient();
						pat2.setName("Fr. Esmeralda");
						db.addPatient(pat2);

						Patient pat3 = new Inpatient();
						pat3.setName("Hr. Wagner");
						db.addPatient(pat3);
					}

					if (db.getNurses().length == 0) {
						// ... add some patients
						Nurse nur1 = new Nurse();
						nur1.setName("Fr. Müller");
						db.addNurse(nur1);

						Nurse nur2 = new Nurse();
						nur2.setName("Fr. Gieselmann");
						db.addNurse(nur2);

						Nurse nur3 = new Nurse();
						nur3.setName("Fr. Otto");
						db.addNurse(nur3);
					}

					try {
						db.save();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Datenbank konnte nicht geöffnet werden: \n\n" + ex);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		}).start();
	}

}
