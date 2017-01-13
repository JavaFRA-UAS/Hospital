package hospital.model;

import hospital.alert.Alert;
import hospital.alert.AlertHelper;
import hospital.database.Database;

public class Vitals {

	int patientId;
	BloodPressure bloodpressure = new BloodPressure();
	double ratebreathing;
	double pulserate;
	double bodytemperature = 37;

	public Vitals(int patientId) {
		this.patientId = patientId;
		this.bloodpressure.setPatientId(patientId);
	}

	public BloodPressure getBloodpressure() {
		return bloodpressure;
	}

	public void setBloodpressure(BloodPressure bloodpressure) {
		this.bloodpressure = bloodpressure;
	}

	public double getRatebreathing() {
		return ratebreathing;
	}

	public void setRatebreathing(double ratebreathing) {
		this.ratebreathing = ratebreathing;

		String problem = null;
		if (ratebreathing >= 60) {
			problem = "death (too high breathing rate)";
			getPatient().die("died of high breathing rate");
		} else if (ratebreathing > 20) {
			problem = "too high";
		} else if (ratebreathing <= 6) {
			problem = "death (stopped breathing)";
			getPatient().die("stopped breathing");
		} else if (ratebreathing < 10) {
			problem = "too low";
		}
		if (problem != null) {
			AlertHelper.getInstance()
					.createAlert(new Alert(patientId, "breathing rate", ratebreathing, 10, 20, problem));
		}
	}

	public double getPulserate() {
		return pulserate;
	}

	public void setPulserate(double pulserate) {
		this.pulserate = pulserate;

		String problem = null;
		if (pulserate > 150) {
			problem = "death (too high pulse)";
			getPatient().die("died of high pulse");
		} else if (pulserate > 100) {
			problem = "too high";
		} else if (pulserate < 20) {
			problem = "too low";
			getPatient().die("died of low pulse");
		} else if (pulserate < 50) {
			problem = "too low";
		}
		if (problem != null) {
			AlertHelper.getInstance().createAlert(new Alert(patientId, "pulse rate", pulserate, 50, 100, problem));
		}
	}

	public double getBodytemperature() {
		return bodytemperature;
	}

	public void setBodytemperature(double bodytemperature) {
		this.bodytemperature = bodytemperature;

		String problem = null;
		if (bodytemperature > 43) {
			problem = "death by fever";
			getPatient().die("died of high fever");
		} else if (bodytemperature > 41) {
			problem = "very high fever";
		} else if (bodytemperature > 40) {
			problem = "high fever";
		} else if (bodytemperature > 38) {
			problem = "fever";
		} else if (bodytemperature < 30) {
			problem = "death by hypothermia";
			getPatient().die("died of severe hypothermia");
		} else if (bodytemperature < 34) {
			problem = "severe hypothermia";
		} else if (bodytemperature < 35.5) {
			problem = "hypothermia";
		}
		if (problem != null) {
			AlertHelper.getInstance()
					.createAlert(new Alert(patientId, "body temperature", bodytemperature, 36, 37, problem));
		}
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
		this.bloodpressure.setPatientId(patientId);
	}

	public Patient getPatient() {
		return Database.getInstance().getPatientMap().get(this.patientId);
	}

}
