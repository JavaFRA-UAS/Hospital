package hospital.alert;

import java.util.ArrayList;
import java.util.List;

public class AlertHelper {

	static AlertHelper instance = new AlertHelper();

	private AlertHelper() {
	}

	public static AlertHelper getInstance() {
		return instance;
	}

	List<Alert> alerts = new ArrayList<Alert>();
	List<AlertListener> alertListeners = new ArrayList<AlertListener>();

	public void createAlert(Alert alert) {
		if (alert == null)
			return;

		// check for similar alert
		synchronized (alerts) {
			if (!alert.getProblem().contains("death")) {
				for (int i = alerts.size() - 1; i > 0; i--) {
					Alert b = alerts.get(i);

					if (b.patientId == alert.patientId && b.time > alert.time - 10000
							&& b.entityName == alert.entityName && b.problem == alert.problem) {
						try {
							double bV = b.getEntityValue();
							double aV = alert.getEntityValue();
							double avgV = (b.expectedMax + b.expectedMin) / 2;
							double bDiff = Math.abs(bV - avgV);
							double aDiff = Math.abs(aV - avgV);
							if (aDiff > bDiff) {
								b.entityValue = alert.entityValue;
								b.problem = alert.problem;
							}
						} catch (Exception ex) {
						}
						b.time = alert.time;
						alert = null;
						break;
					}
				}
			}

			if (alert != null) {
				alerts.add(alert);
			}
		}

		for (AlertListener l : alertListeners) {
			l.onAlertAdded(alert);
		}
	}

	public List<Alert> getAlerts() {
		synchronized (alerts) {
			return alerts;
		}
	}

	public void addAlertListener(AlertListener l) {
		alertListeners.add(l);
	}

}
