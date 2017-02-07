package hospital.helper;

import java.util.HashSet;
import java.util.Random;

public class RandomGenerator {

	Random random = new Random();
	double erraticFactor = 0.01;

	static double problemFactor = 0;

	public static void causeProblems() {
		problemFactor += 5;
	}

	public static HashSet<Integer> currentPatientIds = new HashSet<Integer>();

	public static void addPatientId(int patientId) {
		currentPatientIds.add(patientId);
	}

	public double nextDouble() {

		// 5 % chance that something weird happens
		if (random.nextInt(100) < 2 && erraticFactor <= 0.1) {
			double e = (random.nextDouble()) * 2;
			if (erraticFactor > 0) {
				erraticFactor = -e;
			} else {
				erraticFactor = e;
			}
		}

		double d = random.nextDouble() - 0.5;
		return d;
	}

	public double nextDouble(int patientId) {
		double d = this.nextDouble();
		
		if (currentPatientIds.contains((Integer)patientId)) {
			d += erraticFactor;
			erraticFactor /= 2;
			d += problemFactor;
			problemFactor *= 0.70;
		}
		
		return d;
	}
}
