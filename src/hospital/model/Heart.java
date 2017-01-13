package hospital.model;

import java.util.Random;

public class Heart {
	
	Vitals vitals;

	public Heart(Vitals vitals) {
		this.vitals = vitals;
	}
	
	Random random = new Random();
	long millisecondsUntilNextHeartbeat = 1000;

	public void beat() {
		// calculate time to next heartbeat (random)
		long diff = (long) (random.nextDouble() * 300);
		millisecondsUntilNextHeartbeat += diff;
		// make sure that the time is realistic
		millisecondsUntilNextHeartbeat = Math.max(millisecondsUntilNextHeartbeat, 150);
		millisecondsUntilNextHeartbeat = Math.min(millisecondsUntilNextHeartbeat, 5000);
	}

	public long getMillisecondsUntilNextHeartbeat() {
		return millisecondsUntilNextHeartbeat;
	}
}
