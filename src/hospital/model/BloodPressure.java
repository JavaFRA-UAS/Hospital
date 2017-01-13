package hospital.model;

import java.util.Random;

import hospital.alert.Alert;
import hospital.alert.AlertHelper;
import hospital.database.Database;
import hospital.helper.RandomGenerator;

public class BloodPressure {

	int patientId;
	int sys = 110;
	int dias = 75;

	public BloodPressure() {
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int i) {
		patientId = i;
	}

	public Patient getPatient() {
		return Database.getInstance().getPatientMap().get(this.patientId);
	}

	public int getSys() {
		return sys;
	}

	public void setSys(int sys) {
		this.sys = sys;

		String problem = null;
		if (sys > 170) {
			problem = "death by hypertension";
			getPatient().die();
		} else if (sys > 160) {
			problem = "severe hypertension";
		} else if (sys > 140) {
			problem = "hypertension";
		} else if (sys > 125) {
			problem = "prehypertension";
		} else if (sys < 50) {
			problem = "death by hypotension";
			getPatient().die();
		} else if (sys < 70) {
			problem = "severe hypotension";
		} else if (sys < 80) {
			problem = "hypotension";
		} else if (sys < 90) {
			problem = "prehypotension";
		}
		if (problem != null) {
			AlertHelper.getInstance()
					.createAlert(new Alert(patientId, "blood pressure (systolic)", sys, 100, 120, problem));
		}
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;

		String problem = null;
		if (dias > 130) {
			problem = "death by hypertension";
			getPatient().die();
		} else if (dias > 100) {
			problem = "severe hypertension";
		} else if (dias > 90) {
			problem = "hypertension";
		} else if (dias > 85) {
			problem = "prehypertension";
		} else if (dias < 35) {
			problem = "death by hypotension";
			getPatient().die();
		} else if (dias < 40) {
			problem = "severe hypotension";
		} else if (dias < 50) {
			problem = "hypotension";
		} else if (dias < 60) {
			problem = "prehypotension";
		}
		if (problem != null) {
			AlertHelper.getInstance()
					.createAlert(new Alert(patientId, "blood pressure (diastolic)", dias, 70, 80, problem));
		}
	}

	@Override
	public String toString() {
		return sys + " / " + dias;
	}

	RandomGenerator random = new RandomGenerator();
	long millisecondsUntilNextHeartbeat = 1000;

	public void randomChange() {
		if (getPatient().isAlive()) {
			setSys(getSys() + (int) (random.nextDouble() * 5));
		}
		if (getPatient().isAlive()) {
			setDias(getDias() + (int) (random.nextDouble() * 3));
		}
	}
}
