package hospital;

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
	ArrayList<Room> rooms;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
