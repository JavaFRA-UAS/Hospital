package hospital;

public class Inpatient extends Patient {
	int room_id;

	public Room getRoom() {
		Database db = Database.getInstance();
		for (Room r : db.getRooms()) {
			if (r.getId() == room_id) {
				return r;
			}
		}
		return null;
	}

	public void setRoomId(int room_id) {
		this.room_id = room_id;
	}
}
