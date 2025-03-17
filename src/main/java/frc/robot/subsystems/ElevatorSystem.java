package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Elevator;

public class ElevatorSystem extends SubsystemBase {
  private final TalonFX left;
  private final TalonFX right;
  private static final double DEFAULT_SPEED = 0.25;

  private double initialPosition;

  // private final DigitalInput toplimitSwitch = new DigitalInput(5);
  // private final DigitalInput bottomlimitSwitch = new DigitalInput(4);

  public ElevatorSystem(int leftID, int rightID) {
    left = new TalonFX(leftID);
    right = new TalonFX(rightID);
    initialPosition = left.getRotorPosition().getValueAsDouble();
    left.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.CounterClockwise_Positive));
  }

  public enum Direction {
    Up,
    Down,
    Stop
  };

  public void log() {
    System.out.println((left.getRotorPosition().getValueAsDouble() - initialPosition));
  }

  public void move(Direction dir) {
    double speed = dir == Direction.Up ? Elevator.MOTOR_SPEED : -Elevator.MOTOR_SPEED;
    if (dir == Direction.Stop) {
      speed = Elevator.ANTI_GRAVITY;
    }

    left.set(speed);
    right.set(speed);
  }

  public void stop() {
    left.set(0);
    right.set(0);
  }

  public void addSpeed() {
    double spd_left = left.get();
    double spd_right = right.get();

    if (spd_left >= 1 || spd_right >= 1) {
      return;
    }

    left.set(spd_left + DEFAULT_SPEED);
    right.set(spd_right + DEFAULT_SPEED);
  }

  public void subtractSpeed() {
    double spd_left = left.get();
    double spd_right = right.get();
    if (spd_left <= -1 || spd_right <= -1) {
      return;
    }

    left.set(spd_left - DEFAULT_SPEED);
    right.set(spd_right - DEFAULT_SPEED);
  }

  @Override
  public void periodic() {
  }
}