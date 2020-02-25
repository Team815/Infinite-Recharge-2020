/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubsystemShooter extends SubsystemBase {

  private TalonSRX m_talon1 = new TalonSRX(Constants.MOTOR_BALL_SHOOTER_0);
  private TalonSRX m_talon2 = new TalonSRX(Constants.MOTOR_BALL_SHOOTER_1);
  private TalonSRX m_shooter_feeder = new TalonSRX(Constants.MOTOR_BALL_SHOOTER_FEEDER); 
  private double m_speed = 20000;
  private boolean m_running = false;
  private boolean m_readyToFire = false;

  /**
   * Creates a new SubsystemShooter.
   */
  public SubsystemShooter() {
    m_talon2.setInverted(true);
    m_shooter_feeder.setInverted(true);
    m_talon1.set(ControlMode.Follower, Constants.MOTOR_BALL_SHOOTER_1);
  }

  @Override
  public void periodic() {

    if (m_running) {

      if (SubsystemBallBelt.readyToShoot() && readyToFire()) {
        SubsystemBallBelt.startAssistBallShooter();
        fireShooter();  //This will shoot the ball!
      }

      //System.out.println(m_speed);
      //m_speed = NetworkTableInstance.getDefault().getTable("data").getEntry("speedShooter").getNumber(0.2).doubleValue() / 100;
      setShooterVelocity(m_speed);
    }
  }

  public void changeMotorSpeedBy(double speedChange) {
    m_speed += speedChange;
    m_speed = Math.max(0, m_speed);
    m_speed = Math.min(1, m_speed);
    if (m_running)
      setShooterVelocity(m_speed);
  }

  public void start() {
    if (SubsystemBallBelt.readyToShoot()) {
      setShooterVelocity(m_speed);    
      m_running = true;
    }
  }

  public void stop() {
    SubsystemBallBelt.stopAssistBallShooter();
    stopFiringShooter();
    stopShooter();
    m_running = false;
  }

  private void fireShooter() {
    m_shooter_feeder.set(ControlMode.PercentOutput, 0.5);
  }

  private void stopFiringShooter() {
    m_shooter_feeder.set(ControlMode.PercentOutput, 0);
  }

  private void stopShooter() {
    m_talon2.set(ControlMode.PercentOutput, 0);
  }

  private void setShooterVelocity(double speed) {
    m_talon2.set(ControlMode.Velocity, speed);
    System.out.println("Target speed: " + speed + ", Sensed speed: " + m_talon2.getSelectedSensorVelocity());
  }

  private boolean readyToFire() {
    if (m_talon2.getSelectedSensorVelocity() >= m_speed)
      m_readyToFire = true; 
    else 
      m_readyToFire = false; 
    
    return m_readyToFire;
  }
}
