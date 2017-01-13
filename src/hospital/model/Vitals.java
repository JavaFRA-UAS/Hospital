package hospital.model;

import hospital.alert.Alert;
import hospital.alert.AlertHelper;

public class Vitals {

	int patientId;
	BloodPressure bloodpressure = new BloodPressure();
	double ratebreathing;
	double pulserate;
	double bodytemperature = 37;

	public Vitals(int patientId) {
		this.patientId = patientId;
	}

	public BloodPressure getBloodpressure() {
		return bloodpressure;
	}

	public void setBloodpressure(BloodPressure bloodpressure) {
		this.bloodpressure = bloodpressure;

		String problem = null;
		if (bloodpressure.getSys() > 160) {
			problem = "severe hypertension";
		} else if (bloodpressure.getSys() > 140) {
			problem = "hypertension";
		} else if (bloodpressure.getSys() > 125) {
			problem = "prehypertension";
		} else if (bloodpressure.getSys() < 70) {
			problem = "severe hypotension";
		} else if (bloodpressure.getSys() < 80) {
			problem = "hypotension";
		} else if (bloodpressure.getSys() < 90) {
			problem = "prehypotension";
		}
		if (problem != null) {
			AlertHelper.getInstance().createAlert(
					new Alert(patientId, "blood pressure (systolic)", bloodpressure.getSys(), 100, 120, problem));
		}

		problem = null;
		if (bloodpressure.getDias() > 100) {
			problem = "severe hypertension";
		} else if (bloodpressure.getDias() > 90) {
			problem = "hypertension";
		} else if (bloodpressure.getDias() > 85) {
			problem = "prehypertension";
		} else if (bloodpressure.getDias() < 40) {
			problem = "severe hypotension";
		} else if (bloodpressure.getDias() < 50) {
			problem = "hypotension";
		} else if (bloodpressure.getDias() < 60) {
			problem = "prehypotension";
		}
		if (problem != null) {
			AlertHelper.getInstance().createAlert(
					new Alert(patientId, "blood pressure (diastolic)", bloodpressure.getDias(), 70, 80, problem));
		}
	}

	public double getRatebreathing() {
		return ratebreathing;
	}

	public void setRatebreathing(double ratebreathing) {
		this.ratebreathing = ratebreathing;

		String problem = null;
		if (ratebreathing > 20) {
			problem = "too high";
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
		if (pulserate > 100) {
			problem = "too high";
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
		} else if (bodytemperature > 41) {
			problem = "very high fever";
		} else if (bodytemperature > 40) {
			problem = "high fever";
		} else if (bodytemperature > 38) {
			problem = "fever";
		} else if (bodytemperature < 30) {
			problem = "death by hypothermia";
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

}
