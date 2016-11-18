package hospital;

import javax.swing.*;

public class main {

	public static void main(String[] args) {
		startSimulation();
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				MainWindow mw = new MainWindow ();
			}});
	}

	private static void startSimulation() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				DatabaseConnection.initialize();

				Database db = Database.getInstance();
				
				DatabaseConnection.initialize();
				db.load();
				db.save();

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
			}
		}).start();
	}

}
