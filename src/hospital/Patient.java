package hospital;

public abstract class Patient {

	private static int LastId = 2000;
	
	int id = ++LastId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	Doctor doctor;
	
	int age;
	String name;
	String gender;
	String problem;
	BloodPressure bloodpressure;
	int ratebreathing;
	int pulserate;
	int bodytemperature;
	
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public BloodPressure getBloodpressure() {
		return bloodpressure;
	}

	public void setBloodpressure(BloodPressure bloodpressure) {
		this.bloodpressure = bloodpressure;
	}

	public int getRatebreathing() {
		return ratebreathing;
	}

	public void setRatebreathing(int ratebreathing) {
		this.ratebreathing = ratebreathing;
	}

	public int getPulserate() {
		return pulserate;
	}

	public void setPulserate(int pulserate) {
		this.pulserate = pulserate;
	}

	public int getBodytemperature() {
		return bodytemperature;
	}

	public void setBodytemperature(int bodytemperature) {
		this.bodytemperature = bodytemperature;
	}
}
