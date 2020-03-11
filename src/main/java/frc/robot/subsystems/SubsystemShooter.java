/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubsystemShooter extends SubsystemBase {

  private TalonSRX m_shooter1 = new TalonSRX(Constants.MOTOR_BALL_SHOOTER_0);
  private TalonSRX m_shooter2 = new TalonSRX(Constants.MOTOR_BALL_SHOOTER_1);
  private VictorSPX m_feeder = new VictorSPX(Constants.MOTOR_BALL_SHOOTER_FEEDER);
  private double m_speedShooter = -33500;
  private double m_speedFeeder = 0.8;

  /**
   * Creates a new SubsystemShooter.
   */
  public SubsystemShooter() {
    m_shooter2.setInverted(true);
    m_shooter2.set(ControlMode.Follower, Constants.MOTOR_BALL_SHOOTER_0);
  }

  public void startShooter() {
    setShooterVelocity(m_speedShooter);
  }

  public void startFeeder() {
    setFeeder(m_speedFeeder);
  }

  public void stop() {
    stopFeeder();
    m_shooter1.set(ControlMode.PercentOutput, 0);
  }

  public void stopFeeder() {
    setFeeder(0);
  }

  public boolean readyToShoot() {
    return m_shooter1.getSelectedSensorVelocity() <= (m_speedShooter + 500);
  }

  private void setFeeder(double output) {
    m_feeder.set(ControlMode.PercentOutput, output);
  }

  private void setShooterVelocity(double speed) {
    m_shooter1.set(ControlMode.Velocity, speed);
  }
}
