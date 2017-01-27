package hospital.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class Person {

	int id;
	boolean isDeleted;
	String name;
	String address;
	long timeOfBirth;
	String gender;
	String phone;

	public Person(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void delete() {
		isDeleted = true;
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

	public long getTimeOfBirth() {
		return timeOfBirth;
	}

	public void setTimeOfBirth(long timeOfBirth) {
		this.timeOfBirth = timeOfBirth;
	}

	public LocalDate getTimeOfBirthAsLocalDate() {
		return Instant.ofEpochMilli(this.timeOfBirth * 1000).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public LocalDateTime getTimeOfBirthAsLocalDateTime() {
		return Instant.ofEpochMilli(this.timeOfBirth * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public void setTimeOfBirthAsLocalDate(LocalDate timeOfBirth) {
		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = timeOfBirth.atStartOfDay(zoneId).toEpochSecond();
		this.timeOfBirth = epoch;
	}

	public long getAge() {
		return java.time.temporal.ChronoUnit.YEARS.between(getTimeOfBirthAsLocalDate(), LocalDate.now());
	}
	
	public String getSearchString() {
		return name + gender + address;
	}

	@Override
	public String toString() {
		return name;
	}
}
