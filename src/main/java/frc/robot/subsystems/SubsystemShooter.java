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

public class SubsystemShooter extends SubsystemBase {

  private TalonSRX m_talon1 = new TalonSRX(30);
  private TalonSRX m_talon2 = new TalonSRX(31);
  private double m_speed = 20000;
  private boolean m_running = false;
  private boolean m_readyToFire = false;

  /**
   * Creates a new SubsystemShooter.
   */
  public SubsystemShooter() {
    m_talon2.setInverted(true);
    m_talon1.set(ControlMode.Follower, 31);
  }

  @Override
  public void periodic() {
    if (m_running) {
      //System.out.println(m_speed);
      //m_speed = NetworkTableInstance.getDefault().getTable("data").getEntry("speedShooter").getNumber(0.2).doubleValue() / 100;
      set(m_speed);
    }
  }

  public void changeMotorSpeedBy(double speedChange) {
    m_speed += speedChange;
    m_speed = Math.max(0, m_speed);
    m_speed = Math.min(1, m_speed);
    if (m_running)
      set(m_speed);
  }

  public void start() {
    set(m_speed);
    m_running = true;
  }

  public void stop() {
    m_talon2.set(ControlMode.PercentOutput, 0);
    m_running = false;
  }

  private void set(double speed) {
    m_talon2.set(ControlMode.Velocity, speed);
    System.out.println("Target speed: " + speed + ", Sensed speed: " + m_talon2.getSelectedSensorVelocity());
  }
}
