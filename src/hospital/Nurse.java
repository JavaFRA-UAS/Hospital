package hospital;

import java.util.ArrayList;

public class Nurse {
	private static int LastId = 3000;
	
	int id = ++LastId;
	
	public int getId() {
		return id;
	}

	ArrayList<Room> rooms;

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
}
