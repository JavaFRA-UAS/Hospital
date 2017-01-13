package hospital.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class Nurse {
	private static int LastId = 3000;

	int id = ++LastId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	String name;
	String address;
	long birthday;
	String gender;
	String phone;
	ArrayList<Room> rooms;

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

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return name;
	}
}
