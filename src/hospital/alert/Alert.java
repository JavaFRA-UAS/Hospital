package hospital.alert;

import java.text.SimpleDateFormat;
import java.util.Date;

import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.model.Room;

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

	public Patient getPatient() {
		Patient p;
		if (Inpatient.getFactory().exists(patientId)) {
			p = Inpatient.getFactory().get(patientId);
		} else if (Outpatient.getFactory().exists(patientId)) {
			p = Outpatient.getFactory().get(patientId);
		} else {
			p = null;
		}
		return p;
	}

	public Room getRoom() {
		Patient p = getPatient();
		if (p != null && p instanceof Inpatient) {
			Room r = ((Inpatient) p).getRoom();
			return r;
		}
		return null;
	}

	public String getRoomName() {
		Room r = getRoom();
		return r != null ? r.getName() : "unassigned";
	}

	public Nurse getNurse() {
		Room r = getRoom();
		return r != null ? r.getNurse() : null;
	}

	public String getNurseName() {
		Nurse n = getNurse();
		return n != null ? n.getName() : "-";
	}

	public Doctor getDoctor() {
		Patient p = getPatient();
		if (p != null) {
			Doctor d = p.getDoctor();
			return d;
		}
		return null;
	}

	public String getDoctorName() {
		Doctor d = getDoctor();
		return d != null ? d.getName() : "unassigned";
	}

}
