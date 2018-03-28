import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class BehaviorScanForObstacles implements Behavior {

	private EV3UltrasonicSensor sensor;
	private MovePilot pilot;
	private Boolean suppressed;
	private float[] data;

	public BehaviorScanForObstacles(MovePilot pilot, EV3UltrasonicSensor sensor) {
		this.sensor = sensor;
		this.pilot = pilot;
		suppressed = false;
		data = new float[] { 999 };
	}

	@Override
	public boolean takeControl() {
		sensor.getDistanceMode();
		sensor.fetchSample(data, 0);
		int retval = Float.compare(data[0], new Float(0.4));
		if (retval > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.stop();
		suppress();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
