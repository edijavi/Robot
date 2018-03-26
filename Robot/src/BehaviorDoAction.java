import java.util.Random;

import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class BehaviorDoAction implements Behavior {

	private EV3UltrasonicSensor sensor;
	private MovePilot pilot;
	private Boolean suppressed;
	private float[] data;

	public BehaviorDoAction(MovePilot pilot, EV3UltrasonicSensor sensor) {
		this.sensor = sensor;
		this.pilot = pilot;
		suppressed = false;
		data = new float[] { 999 };
	}

	@Override
	public boolean takeControl() {
		if (!pilot.isMoving()) {
			sensor.getDistanceMode();
			sensor.fetchSample(data, 0);
			int retval = Float.compare(data[0], new Float(0.4));
			if (retval > 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		Sound.beepSequenceUp();
		pilot.travel(-50, true);

		while (pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
		Random rand = new Random();
		pilot.rotate(rand.nextInt(360) + 1);

		pilot.stop();
		suppress();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
