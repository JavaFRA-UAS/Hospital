package hospital.alert;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Alert {

	long time;
	int patientId;
	String entityName;
	double entityValue;
	double expectedMin;
	double expectedMax;
	String problem;

	public Alert(int patientId, String entityName, double entityValue, double expectedMin, double expectedMax,
			String problem) {
		this.time = System.currentTimeMillis();
		this.patientId = patientId;
		this.entityName = entityName != null ? entityName : null;
		this.entityValue = entityValue;
		this.expectedMin = expectedMin;
		this.expectedMax = expectedMax;
		this.problem = problem != null ? problem : null;
	}

	public long getTime() {
		return time;
	}

	public String getFormattedTime() {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		return sdf.format(date);
	}

	public int getPatientId() {
		return patientId;
	}

	public String getEntityName() {
		return entityName;
	}

	public double getEntityValue() {
		return entityValue;
	}

	public double getExpectedMin() {
		return expectedMin;
	}

	public double getExpectedMax() {
		return expectedMax;
	}

	public String getProblem() {
		return problem;
	}

}
