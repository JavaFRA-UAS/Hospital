package hospital;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hospital.database.Factory;
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

					while (true) {

						for (int patientId : Inpatient.getFactory().getAllIds()) {
							if (!runningSimulations.contains(patientId)) {
								VitalsSimulation.runSimulation(patientId, Inpatient.getFactory());
								runningSimulations.add(patientId);
							}
						}
						for (int patientId : Outpatient.getFactory().getAllIds()) {
							if (!runningSimulations.contains(patientId)) {
								VitalsSimulation.runSimulation(patientId, Outpatient.getFactory());
								runningSimulations.add(patientId);
							}
						}
						Thread.sleep(300);

						VitalsPanel.onRefresh();

					}
				} catch (Exception ex) {
					Log.showException(ex);
				}
			}
		}).start();
	}

	public static void runSimulation(final Integer patientId, final Factory<? extends Patient> factory) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Patient patient = factory.get(patientId);

						if (patient == null) {
							// patient doesnt exist any more,
							// but maybe he will be added again,
							// so dont terminate the thread
							Thread.sleep(1000);
							continue;
						}

						if (patient.isDeleted() || !patient.isAlive()) {
							Thread.sleep(1000);
							continue;
						}

						// simulate heartbeat
						patient.getHeart().beat();

						// wait until next heartbeat
						Thread.sleep(patient.getHeart().getMillisecondsUntilNextHeartbeat());
					}
				} catch (final Exception ex) {
					Log.showException(ex);
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					RandomGenerator random = new RandomGenerator();
					Random random2 = new Random();

					while (true) {
						Patient patient = factory.get(patientId);

						if (patient == null) {
							// patient doesnt exist any more,
							// but maybe he will be added again,
							// so dont terminate the thread
							Thread.sleep(1000);
							continue;
						}

						if (patient.isDeleted() || !patient.isAlive()) {
							Thread.sleep(1000);
							continue;
						}

						// modify blood pressure
						patient.getVitals().getBloodpressure().randomChange();

						// modify body temperature
						double temperature = patient.getVitals().getBodytemperature();
						temperature += random.nextDouble() * 0.2;
						patient.getVitals().setBodytemperature(temperature);

						// death of old age
						double probability = patient.getAge() < 60 ? 0.0 : (patient.getAge() - 60.0) / 30.0;
						for (int tries = 3; tries >= 0 && random2.nextDouble() < probability; tries--) {
							if (tries == 0) {
								patient.die("died of old age");
							}
						}

						// wait 1 second
						Thread.sleep(500);
					}
				} catch (final Exception ex) {
					Log.showException(ex);
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Patient patient = factory.get(patientId);

						if (patient == null) {
							// patient doesnt exist any more,
							// but maybe he will be added again,
							// so dont terminate the thread
							Thread.sleep(1000);
							continue;
						}

						if (patient.isDeleted() || !patient.isAlive()) {
							Thread.sleep(1000);
							continue;
						}

						// simulate breathing
						patient.getLungs().breathe();

						// wait until next breath
						Thread.sleep(patient.getLungs().getMillisecondsUntilNextBreath());
					}
				} catch (final Exception ex) {
					Log.showException(ex);
				}
			}
		}).start();
	}
}
