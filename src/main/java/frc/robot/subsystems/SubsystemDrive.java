/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubsystemDrive extends SubsystemBase {

  private static final double DEADZONE_THRESHOLD = 0.1;
  private static final double ACCELERATION_MAX = 0.01;

  double m_speedHorizontal = 0;
  double m_speedVertical = 0;
  double m_speedRotational = 0;

  MecanumDrive mecanumDrive = new MecanumDrive(
    new CANSparkMax(Constants.MOTOR_DRIVE_FRONT_LEFT, MotorType.kBrushless),
    new CANSparkMax(Constants.MOTOR_DRIVE_REAR_LEFT, MotorType.kBrushless),
    new CANSparkMax(Constants.MOTOR_DRIVE_FRONT_RIGHT, MotorType.kBrushless),
    new CANSparkMax(Constants.MOTOR_DRIVE_REAR_RIGHT, MotorType.kBrushless));

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double speedHorizontal, double speedVertical, double speedRotational) {
    m_speedHorizontal = calculateOuput(speedHorizontal, m_speedHorizontal);
    m_speedVertical = calculateOuput(speedVertical, m_speedVertical);
    m_speedRotational = calculateOuput(speedRotational, m_speedRotational);

    mecanumDrive.driveCartesian(m_speedHorizontal, m_speedVertical, m_speedRotational);
  }

  private static double calculateOuput(double input, double currentOutput) {
    input = Math.abs(input) < DEADZONE_THRESHOLD ? 0 : input;
    double difference = input - currentOutput;
    difference = Math.min(difference, ACCELERATION_MAX);
    difference = Math.max(difference, -ACCELERATION_MAX);
    return currentOutput + difference;
  }
}
