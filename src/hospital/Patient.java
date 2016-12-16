package hospital;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public abstract class Patient {

	private static int LastId = 2000;
	
	int id = ++LastId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	Doctor doctor;
	String name;
	String address;
	long birthday;
	String gender;
	String problem;
	String phone;
	BloodPressure bloodpressure;
	int ratebreathing;
	int pulserate;
	int bodytemperature;
	
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

	public BloodPressure getBloodpressure() {
		return bloodpressure;
	}

	public void setBloodpressure(BloodPressure bloodpressure) {
		this.bloodpressure = bloodpressure;
	}

	public int getRatebreathing() {
		return ratebreathing;
	}

	public void setRatebreathing(int ratebreathing) {
		this.ratebreathing = ratebreathing;
	}

	public int getPulserate() {
		return pulserate;
	}

	public void setPulserate(int pulserate) {
		this.pulserate = pulserate;
	}

	public int getBodytemperature() {
		return bodytemperature;
	}

	public void setBodytemperature(int bodytemperature) {
		this.bodytemperature = bodytemperature;
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
		return Instant.ofEpochMilli(this.birthday).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public void setBirthdayAsLocalDate(LocalDate birthday) {
		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = birthday.atStartOfDay(zoneId).toEpochSecond();
		this.birthday = epoch;
	}


	public long getAge() {
		return java.time.temporal.ChronoUnit.YEARS.between(getBirthdayAsLocalDate(), LocalDate.now());
	}


	@Override
	public String toString() {
		return name;
	}

}
