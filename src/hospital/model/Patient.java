package hospital.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hospital.alert.Alert;
import hospital.alert.AlertHelper;
import hospital.database.Factory;

public abstract class Patient extends Person {

	int doctorId;
	String problem;
	Vitals vitals;
	Heart heart;
	Lungs lungs;
	boolean isAlive;
	long timeOfDeath;

	public Patient(int id) {
		super(id);
		vitals = new Vitals(id);
		heart = new Heart(vitals);
		lungs = new Lungs(vitals);
		isAlive = true;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	@Override
	public void setId(int id) {
		super.setId(id);
		this.vitals.setPatientId(id);
	}

	public Doctor getDoctor() {
		return Doctor.getFactory().get(doctorId);
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public void setDoctor(Doctor doctor) {
		if (doctor == null)
			return;
		this.doctorId = doctor.getId();
	}

	public Vitals getVitals() {
		return vitals;
	}

	public Heart getHeart() {
		return heart;
	}

	public Lungs getLungs() {
		return lungs;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
		if (isAlive) {
			this.timeOfDeath = 0;
		} else {
			this.timeOfDeath = System.currentTimeMillis() / 1000;
		}
	}

	public void die(final String why) {

		if (this.isAlive) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					AlertHelper.getInstance().createAlert(new Alert(id, "", 0, 1, 1, why));
					JOptionPane.showMessageDialog(null, name + " " + why + ".");
				}
			});

			setAlive(false);
		}
	}

	public void revive(final String why) {

		if (!this.isAlive) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					AlertHelper.getInstance().createAlert(new Alert(id, "", 1, 1, 1, why));
				}
			});
		}

		this.vitals = new Vitals(id);
		this.heart = new Heart(vitals);
		this.lungs = new Lungs(vitals);
		setAlive(true);
	}

	public long getTimeOfDeath() {
		return timeOfDeath;
	}

	public void setTimeOfDeath(long timeOfDeath) {
		this.timeOfDeath = timeOfDeath;
		this.isAlive = timeOfDeath == 0;
	}

	public LocalDate getTimeOfDeathAsLocalDate() {
		return Instant.ofEpochMilli(this.timeOfDeath * 1000).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public LocalDateTime getTimeOfDeathAsLocalDateTime() {
		return Instant.ofEpochMilli(this.timeOfDeath * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public void setTimeOfDeathAsLocalDate(LocalDate timeOfDeath) {
		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = timeOfDeath.atStartOfDay(zoneId).toEpochSecond();
		this.timeOfDeath = epoch;
	}

	@Override
	public String toString() {
		return name != null ? name : "";
	}

}
