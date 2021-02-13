/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubsystemDrive extends SubsystemBase {

  private static final double DEADZONE_THRESHOLD = 0.1;

  private static final AnalogGyro m_gyro = new AnalogGyro(Constants.SENSOR_GYRO_DRIVE);

  private double m_maxSpeed = 1;
  private double m_accelerationMax = 0.01;

  private double m_speedHorizontal = 0;
  private double m_speedVertical = 0;
  private double m_speedRotational = 0;

  private MecanumDriveOdometry m_odometry = new MecanumDriveOdometry(
    new MecanumDriveKinematics(
      new Translation2d(0.3048, -0.2413),
      new Translation2d(0.3048, 0.2413),
      new Translation2d(-0.3048, -0.2413),
      new Translation2d(-0.3048, 0.2413)),
    m_gyro.getRotation2d());

  private Pose2d m_pose = new Pose2d();
  
  private CANEncoder m_encoderFrontLeft;
  private CANEncoder m_encoderRearLeft;
  private CANEncoder m_encoderFrontRight;
  private CANEncoder m_encoderRearRight;

  private MecanumDrive mecanumDrive;
  
  public SubsystemDrive() {
    var motorFrontLeft =  new CANSparkMax(Constants.MOTOR_DRIVE_FRONT_LEFT, MotorType.kBrushless);
    var motorRearLeft = new CANSparkMax(Constants.MOTOR_DRIVE_REAR_LEFT, MotorType.kBrushless);
    var motorFrontRight = new CANSparkMax(Constants.MOTOR_DRIVE_FRONT_RIGHT, MotorType.kBrushless);
    var motorRearRight = new CANSparkMax(Constants.MOTOR_DRIVE_REAR_RIGHT, MotorType.kBrushless);

    mecanumDrive = new MecanumDrive(
      motorFrontLeft, 
      motorRearLeft,
      motorFrontRight,
      motorRearRight);
    
    m_encoderFrontLeft = motorFrontLeft.getEncoder();
    m_encoderRearLeft = motorRearLeft.getEncoder();
    m_encoderFrontRight = motorFrontRight.getEncoder();
    m_encoderRearRight = motorRearRight.getEncoder();

    var factor = 0.1016 * Math.PI / 30;
    m_encoderFrontLeft.setVelocityConversionFactor(factor);
    m_encoderRearLeft.setVelocityConversionFactor(factor);
    m_encoderFrontRight.setVelocityConversionFactor(factor);
    m_encoderRearRight.setVelocityConversionFactor(factor);
  }

  @Override
  public void periodic() {
    var wheelSpeeds = new MecanumDriveWheelSpeeds(
      m_encoderFrontLeft.getVelocity(),
      m_encoderFrontRight.getVelocity(),
      m_encoderRearLeft.getVelocity(),
      m_encoderRearRight.getVelocity());

      m_pose = m_odometry.update(m_gyro.getRotation2d(), wheelSpeeds);

      System.out.println(m_pose.getX() + ", " + m_pose.getY());
    // This method will be called once per scheduler run
  }

  public void driveManual(double speedHorizontal, double speedVertical, double speedRotational) {
    drive(
      calculateOuput(speedHorizontal, m_speedHorizontal),
      calculateOuput(speedVertical, m_speedVertical),
      calculateOuput(speedRotational, m_speedRotational));
  }

  public void drive(double speedHorizontal, double speedVertical, double speedRotational) {
    m_speedHorizontal = speedHorizontal;
    m_speedVertical = speedVertical;
    m_speedRotational = speedRotational;

    mecanumDrive.driveCartesian(m_speedHorizontal, m_speedVertical, m_speedRotational, -m_gyro.getAngle());
  }

  private double calculateOuput(double input, double currentOutput) {
    input = Math.abs(input) < DEADZONE_THRESHOLD ? 0 : input;
    double difference = (input * m_maxSpeed) - currentOutput;
    difference = Math.min(difference, m_accelerationMax);
    difference = Math.max(difference, -m_accelerationMax);
    return currentOutput + difference;
  }

  public void tortoiseMode() {
    System.out.println("Tortoise");
    m_maxSpeed = 0.1;
    m_accelerationMax = 1;
  }

  public void hareMode() {
    m_maxSpeed = 1;
    m_accelerationMax = 0.02;
  }

  public double getAngle() {
    return m_gyro.getAngle();
  }
}
