/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubsystemDrive extends SubsystemBase {

  XboxController controller;
  MecanumDrive mecanumDrive;

  /**
   * Creates a new Drive.
   */
  public SubsystemDrive(XboxController controller) {
    this.controller = controller;
    mecanumDrive = new MecanumDrive(
      new CANSparkMax(Constants.PORT_MOTOR_DRIVE_FRONTLEFT, MotorType.kBrushless),
      new CANSparkMax(Constants.PORT_MOTOR_DRIVE_REARLEFT, MotorType.kBrushless),
      new CANSparkMax(Constants.PORT_MOTOR_DRIVE_FRONTRIGHT, MotorType.kBrushless),
      new CANSparkMax(Constants.PORT_MOTOR_DRIVE_REARRIGHT, MotorType.kBrushless));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive() {
    mecanumDrive.driveCartesian(controller.getRawAxis(0), controller.getRawAxis(1), controller.getRawAxis(4));
  }
}
