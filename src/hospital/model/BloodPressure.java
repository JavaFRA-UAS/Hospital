package hospital.model;

import java.util.Random;

import hospital.helper.RandomGenerator;

public class BloodPressure {
	int sys = 110;
	int dias = 75;

	public int getSys() {
		return sys;
	}

	public void setSys(int sys) {
		this.sys = sys;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	@Override
	public String toString() {
		return sys + " / " + dias;
	}

	RandomGenerator random = new RandomGenerator();
	long millisecondsUntilNextHeartbeat = 1000;

	public void randomChange() {
		sys += (int) (random.nextDouble() * 5);
		dias += (int) (random.nextDouble() * 3);
	}
}
