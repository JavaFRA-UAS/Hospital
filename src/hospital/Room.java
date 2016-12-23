package hospital;

public class Room {

	private static int LastId = 100;

	int id = ++LastId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
