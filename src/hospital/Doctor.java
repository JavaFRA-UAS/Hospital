package hospital;

import java.util.ArrayList;

public class Doctor {
	
	private static int LastId = 1000;
	
	int id = ++LastId;
	
	public int getId() {
		return id;
	}

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
