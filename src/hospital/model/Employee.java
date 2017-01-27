package hospital.model;

public abstract class Employee extends Person {

	public Employee(int id) {
		super(id);
	}

	String password;

	public String getPassword() {
		return password != null ? password : "";
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
