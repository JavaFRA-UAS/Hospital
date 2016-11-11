package hospital;

import java.util.ArrayList;

public class Database {
	private ArrayList<Doctor> doctors;
	private ArrayList<Patient> patients;
	private ArrayList<Nurse> nurses;
	private ArrayList<Room> rooms;
	
	public Database(){
		doctors = new ArrayList<Doctor> ();
		patients = new ArrayList<Patient> ();
		nurses = new ArrayList<Nurse> ();
		rooms = new ArrayList<Room> ();

	}
	
	private static Database instance = new Database(); 
	public static Database getInstance(){return instance;}
	
	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}
	public ArrayList<Patient> getPatients() {
		return patients;
	}
	public ArrayList<Nurse> getNurses() {
		return nurses;
	}
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	public void addPatient(Patient p) {
		patients.add(p);
	}
	
	public void addNurse(Nurse n) {
		nurses.add(n);
	}
	
	public void addDoctor(Doctor d) {
		doctors.add(d);
	}
	
	public void addRoom(Room r) {
		rooms.add(r);
	}
}
