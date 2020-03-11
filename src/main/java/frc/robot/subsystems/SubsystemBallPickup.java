/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubsystemBallPickup extends SubsystemBase {

  private final TalonSRX spinner = new TalonSRX(Constants.MOTOR_BALL_PICKUP_SPINNER);
  private final Compressor compressor = new Compressor(30);
  private final DoubleSolenoid solenoidRight = new DoubleSolenoid(30, 1, 0);
  private final DoubleSolenoid solenoidLeft = new DoubleSolenoid(30, 3, 2);
  private final DoubleSolenoid solenoidTray = new DoubleSolenoid(30, 6, 7);
  private final Solenoid[] solenoidsOff = new Solenoid[2];

  /**
   * Creates a new SubsystemBallPickup.
   */
  public SubsystemBallPickup() {
    spinner.setInverted(true);
    solenoidsOff[0] = new Solenoid(30, 4);
    solenoidsOff[1] = new Solenoid(30, 5);
    solenoidsOff[0].set(true);
    solenoidsOff[1].set(false);
    setSolendoids(Value.kReverse);
  }

  @Override
  public void periodic() {
  }

  public void start() {
    setSolendoids(Value.kForward);
    setSpinner(0.3);
    NetworkTableInstance.getDefault().getTable("data").getEntry("ballPickup").setBoolean(true);
  }

  public void stop() {
    System.out.println(Integer.toBinaryString(Solenoid.getAll(30)));
    setSolendoids(Value.kReverse);
    setSpinner(0);
    NetworkTableInstance.getDefault().getTable("data").getEntry("ballPickup").setBoolean(false);
  }

  public boolean isRunning() {
    return spinner.getMotorOutputPercent() != 0;
  }

  private void setSpinner(double output){
    spinner.set(ControlMode.PercentOutput, output);
  }

  private void setSolendoids(Value value) {
    solenoidLeft.set(value);
    solenoidRight.set(value);
    solenoidTray.set(value);
  }
}
