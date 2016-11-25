package hospital;

import javax.swing.*;

public class Main {

	public static void Main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
	        @Override
	        public void uncaughtException(Thread t, Throwable ex) {
	            JOptionPane.showMessageDialog(null, ex);
	        }
	    });
		
		startSimulation();
		Database.getInstance().waitUntilReady();

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

					try {
						DatabaseConnection.initialize();

						db.load();
						db.save();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Datenbank konnte nicht geöffnet werden: \n\n" + ex);
					}

					Doctor doc1 = new Doctor();
					doc1.setName("Dr. Heisenberg");
					db.addDoctor(doc1);

					Doctor doc2 = new Doctor();
					doc2.setName("Dr. Bergmann");
					db.addDoctor(doc2);

					Doctor doc3 = new Doctor();
					doc3.setName("Dr. Schwarzkopf");
					db.addDoctor(doc3);

					Patient pat1 = new Inpatient();
					pat1.setName("Fr. Hildegard");
					db.addPatient(pat1);

					Patient pat2 = new Outpatient();
					pat2.setName("Fr. Esmeralda");
					db.addPatient(pat2);

					Patient pat3 = new Inpatient();
					pat3.setName("Hr. Wagner");
					db.addPatient(pat3);

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
