package hospital.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hospital.alert.Alert;
import hospital.alert.AlertHelper;

public abstract class Patient {

	private static int LastId = 2000;

	int id;
	Doctor doctor;
	String name;
	String address;
	long birthday;
	String gender;
	String problem;
	String phone;
	Vitals vitals;
	Heart heart;
	Lungs lungs;
	boolean isAlive;

	protected Patient() {
		id = ++LastId;
		vitals = new Vitals(id);
		heart = new Heart(vitals);
		lungs = new Lungs(vitals);
		isAlive = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		this.vitals.setPatientId(id);
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public LocalDate getBirthdayAsLocalDate() {
		return Instant.ofEpochMilli(this.birthday * 1000).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public void setBirthdayAsLocalDate(LocalDate birthday) {
		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = birthday.atStartOfDay(zoneId).toEpochSecond();
		this.birthday = epoch;
	}

	public long getAge() {
		return java.time.temporal.ChronoUnit.YEARS.between(getBirthdayAsLocalDate(), LocalDate.now());
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
		}

		this.isAlive = false;
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
		this.isAlive = true;
	}

	@Override
	public String toString() {
		return name;
	}

}
