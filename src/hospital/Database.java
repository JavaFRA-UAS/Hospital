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
}
