import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Main {

	static MovePilot pilot;
	static EV3UltrasonicSensor sensor;

	public static void main(String[] args) {

		Wheel wheel1 = WheeledChassis.modelWheel(Motor.A, 5.5).offset(-6);
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.D, 5.5).offset(6);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 },
				WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);

		sensor = new EV3UltrasonicSensor(SensorPort.S1);

		pilot.setLinearSpeed(500);

		setUpButtonListener();

		final Behavior[] behaviors = { new BehaviorMove(pilot),
				new BehaviorScanForObstacles(pilot, sensor),
				new BehaviorDoAction(pilot, sensor) };
		final Arbitrator arbitrator = new Arbitrator(behaviors);

		arbitrator.go();
	}

	private static void setUpButtonListener() {
		Button.ESCAPE.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(final Key key) {
				System.exit(1);
			}

			@Override
			public void keyReleased(Key k) {
			}
		});
	}
}
