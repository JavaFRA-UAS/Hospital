package hospital.helper;

import java.util.Random;

public class RandomGenerator {

	Random random = new Random();
	double erraticFactor = 0.01;

	public double nextDouble() {

		// 5 % chance that something weird happens
		if (random.nextInt(100) < 5) {
			double e = (random.nextDouble()) * 1.7;
			if (erraticFactor > 0) {
				erraticFactor = -e;
			} else {
				erraticFactor = e;
			}
		}

		double d = random.nextDouble() - 0.5;
		d += erraticFactor;
		erraticFactor /= 2;
		return d;
	}
}
