import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class BehaviorMove implements Behavior {

	private MovePilot pilot;
	private Boolean suppressed;

	public BehaviorMove(MovePilot pilot) {
		this.pilot = pilot;
		suppressed = false;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.forward();
		while (!suppressed) {
			Thread.yield();
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
