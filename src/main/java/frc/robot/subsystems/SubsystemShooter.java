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
  private double m_speed = 0.2;
  private boolean m_running = false;

  /**
   * Creates a new SubsystemShooter.
   */
  public SubsystemShooter() {
    m_talon2.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void changeMotorSpeedBy(double speedChange) {
    m_speed += speedChange;
    m_speed = Math.max(0, m_speed);
    m_speed = Math.min(1, m_speed);
    if (m_running) {
      set(m_speed);
    }
  }

  public void start() {
    set(m_speed);
    m_running = true;
  }

  public void stop() {
    set(0);
    m_running = false;
  }

  private void set(double speed) {
    m_talon1.set(ControlMode.PercentOutput, speed);
    m_talon2.set(ControlMode.PercentOutput, speed);
  }
}
