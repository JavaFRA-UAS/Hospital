package hospital.helper;

import java.util.Random;

public class RandomGenerator {

	Random random = new Random();
	double erraticFactor = 0.01;

	public double nextDouble() {

		// 5 % chance that something weird happens
		if (random.nextInt(100) < 5 && erraticFactor <= 0.1) {
			double e = (random.nextDouble()) * 2;
			if (erraticFactor > 0) {
				erraticFactor = -e;
			} else {
				erraticFactor = e;
			}
		}

		double d = random.nextDouble() - 0.5;
		d += erraticFactor;
		//System.out.println(String.format("%.2f", erraticFactor));
		erraticFactor /= 2;
		return d;
	}
}
