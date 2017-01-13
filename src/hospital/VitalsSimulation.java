package hospital;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import hospital.database.Database;
import hospital.helper.RandomGenerator;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.views.VitalsPanel;

public class VitalsSimulation {

	static HashSet<Integer> runningSimulations = new HashSet<Integer>();

	public static void initialize() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					Database db = Database.getInstance();
					while (true) {
						for (Map.Entry<Integer, Patient> entry : db.getPatientMap().entrySet()) {
							final Integer patientId = entry.getKey();

							if (!runningSimulations.contains(patientId)) {
								VitalsSimulation.runSimulation(patientId);
								runningSimulations.add(patientId);
							}
						}
						Thread.sleep(300);

						VitalsPanel.onRefresh();

					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		}).start();
	}

	public static void runSimulation(final Integer patientId) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Database db = Database.getInstance();
					while (true) {
						Patient patient = db.getPatientMap().get(patientId);

						if (patient == null) {
							// patient doesnt exist any more,
							// but maybe he will be added again,
							// so dont terminate the thread
							Thread.sleep(1000);
							continue;
						}

						if (!patient.isAlive()) {
							Thread.sleep(1000);
							continue;
						}

						// simulate heartbeat
						patient.getHeart().beat();

						// wait until next heartbeat
						Thread.sleep(patient.getHeart().getMillisecondsUntilNextHeartbeat());
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					RandomGenerator random = new RandomGenerator();

					Database db = Database.getInstance();
					while (true) {
						Patient patient = db.getPatientMap().get(patientId);

						if (patient == null) {
							// patient doesnt exist any more,
							// but maybe he will be added again,
							// so dont terminate the thread
							Thread.sleep(1000);
							continue;
						}

						if (!patient.isAlive()) {
							Thread.sleep(1000);
							continue;
						}

						// modify blood pressure
						patient.getVitals().getBloodpressure().randomChange();

						// modify body temperature
						double temperature = patient.getVitals().getBodytemperature();
						temperature += random.nextDouble() * 0.2;
						patient.getVitals().setBodytemperature(temperature);

						// wait 1 second
						Thread.sleep(500);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Database db = Database.getInstance();
					while (true) {
						Patient patient = db.getPatientMap().get(patientId);

						if (patient == null) {
							// patient doesnt exist any more,
							// but maybe he will be added again,
							// so dont terminate the thread
							Thread.sleep(1000);
							continue;
						}

						if (!patient.isAlive()) {
							Thread.sleep(1000);
							continue;
						}

						// simulate breathing
						patient.getLungs().breathe();

						// wait until next breath
						Thread.sleep(patient.getLungs().getMillisecondsUntilNextBreath());
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		}).start();
	}
}
