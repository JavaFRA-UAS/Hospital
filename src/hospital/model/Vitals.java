package hospital.model;

public class Vitals {

	BloodPressure bloodpressure = new BloodPressure();
	double ratebreathing;
	double pulserate;
	double bodytemperature = 37;

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
	}

	public double getPulserate() {
		return pulserate;
	}

	public void setPulserate(double pulserate) {
		this.pulserate = pulserate;
	}

	public double getBodytemperature() {
		return bodytemperature;
	}

	public void setBodytemperature(double bodytemperature) {
		this.bodytemperature = bodytemperature;
	}

}
