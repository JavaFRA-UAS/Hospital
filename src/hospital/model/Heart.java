package hospital.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import hospital.helper.RandomGenerator;

public class Heart {

	Vitals vitals;

	public Heart(Vitals vitals) {
		this.vitals = vitals;

		// when starting, fill the queue of heartbeats with default data
		long currentTimestamp = System.currentTimeMillis();
		for (long i = currentTimestamp - 60000; i < currentTimestamp; i += millisecondsUntilNextHeartbeat) {
			lastHeartbeats.offer(i);
		}
	}

	RandomGenerator random = new RandomGenerator();
	long millisecondsUntilNextHeartbeat = 1000;
	Queue<Long> lastHeartbeats = new LinkedList<Long>();

	public void beat() {

		// calculate time to next heartbeat (random)
		long diff = (long) (random.nextDouble(vitals.getPatientId()) * 300);
		millisecondsUntilNextHeartbeat += diff;

		// make sure that the time is realistic
		millisecondsUntilNextHeartbeat = Math.max(millisecondsUntilNextHeartbeat, 500);
		millisecondsUntilNextHeartbeat = Math.min(millisecondsUntilNextHeartbeat, 5000);

		// calculate the current pulse rate
		long currentTimestamp = System.currentTimeMillis();
		lastHeartbeats.offer(currentTimestamp);
		while (lastHeartbeats.peek() < currentTimestamp - 60000) {
			lastHeartbeats.poll();
		}
		double pulserate = lastHeartbeats.size();
		vitals.setPulserate(pulserate);
	}

	public long getMillisecondsUntilNextHeartbeat() {
		return millisecondsUntilNextHeartbeat;
	}
}
