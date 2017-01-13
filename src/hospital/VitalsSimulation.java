package hospital;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import hospital.database.Database;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;

public class VitalsSimulation {

	static HashMap<Integer, Thread> threadsOfPatients = new HashMap<Integer, Thread>();

	public static void initialize() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					Database db = Database.getInstance();
					while (true) {
						for (Map.Entry<Integer, Patient> entry : db.getPatientMap().entrySet()) {
							Integer patientId = entry.getKey();
							Patient patient = entry.getValue();

							if (!threadsOfPatients.containsKey(patientId)) {
								Thread t = new Thread(new Runnable() {
									@Override
									public void run() {
										VitalsSimulation.runSimulation(patientId);
									}
								});
								t.start();
								threadsOfPatients.put(patientId, t);
							}
						}
						Thread.sleep(1000);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		}).start();
	}

	public static void runSimulation(long patientId) {
		try {
			Random random = new Random();

			Database db = Database.getInstance();
			while (true) {
				Patient patient = db.getPatientMap().get(patientId);

				if (patient == null) {
					// patient doesnt exist any more, but maybe he will be added
					// again
					Thread.sleep(1000);
					continue;
				}

				// simulate heartbeat
				patient.getHeart().beat();
				
				// modify blood pressure
				patient.getVitals().getBloodpressure().randomChange();
				
				// modify body temperature
				double temperature = patient.getVitals().getBodytemperature();
				temperature += random.nextDouble() * 0.1;
				patient.getVitals().setBodytemperature(temperature);

				// wait until next heartbeat
				Thread.sleep(patient.getHeart().getMillisecondsUntilNextHeartbeat());
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}
}
