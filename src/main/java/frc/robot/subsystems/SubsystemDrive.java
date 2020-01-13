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

  private static final double DEADZONE_THRESHOLD = 0.1;
  private static final double ACCELERATION_MAX = 0.01;

  XboxController controller;
  MecanumDrive mecanumDrive;
  double xSpeed = 0;
  double ySpeed = 0;
  double zRotation = 0;

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
    ySpeed = calculateOuput(controller.getRawAxis(0), ySpeed);
    xSpeed = calculateOuput(-controller.getRawAxis(1), xSpeed);
    zRotation = calculateOuput(controller.getRawAxis(4), zRotation);

    mecanumDrive.driveCartesian(ySpeed, xSpeed, zRotation);
  }

  private static double calculateOuput(double input, double currentOutput) {
    input = Math.abs(input) < DEADZONE_THRESHOLD ? 0 : input;

    double nextOutput = currentOutput;
    if (Math.abs(input - currentOutput) < ACCELERATION_MAX) {
      nextOutput = input;
    } else {
      nextOutput += input > currentOutput ? ACCELERATION_MAX : -ACCELERATION_MAX;
    }

    return nextOutput;
  }


}
