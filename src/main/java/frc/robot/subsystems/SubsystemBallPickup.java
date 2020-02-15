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

public class SubsystemBallPickup extends SubsystemBase {

  private final TalonSRX spinner = new TalonSRX(Constants.MOTOR_BALL_PICKUP_SPINNER);

  /**
   * Creates a new SubsystemBallPickup.
   */
  public SubsystemBallPickup() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void set(double output){
    spinner.set(ControlMode.PercentOutput, output);
  }
}
