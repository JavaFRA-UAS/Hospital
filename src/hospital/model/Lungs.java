package hospital.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import hospital.helper.RandomGenerator;

public class Lungs {

	Vitals vitals;

	public Lungs(Vitals vitals) {
		this.vitals = vitals;

		// when starting, fill the queue of breaths with default data
		long currentTimestamp = System.currentTimeMillis();
		for (long i = currentTimestamp - 60000; i < currentTimestamp; i += millisecondsUntilNextBreath) {
			lastBreaths.offer(i);
		}
	}

	RandomGenerator random = new RandomGenerator();
	long millisecondsUntilNextBreath = 5000;
	Queue<Long> lastBreaths = new LinkedList<Long>();

	public void breathe() {

		// calculate time to next heartbeat (random)
		long diff = (long) (random.nextDouble(vitals.getPatientId()) * 1700);
		millisecondsUntilNextBreath += diff;

		// make sure that the time is realistic
		millisecondsUntilNextBreath = Math.max(millisecondsUntilNextBreath, 500);
		millisecondsUntilNextBreath = Math.min(millisecondsUntilNextBreath, 10000);

		// calculate the current pulse rate
		long currentTimestamp = System.currentTimeMillis();
		lastBreaths.offer(currentTimestamp);
		while (lastBreaths.peek() < currentTimestamp - 60000) {
			lastBreaths.poll();
		}
		double ratebreathing = lastBreaths.size();
		vitals.setRatebreathing(ratebreathing);
	}

	public long getMillisecondsUntilNextBreath() {
		return millisecondsUntilNextBreath;
	}
}
