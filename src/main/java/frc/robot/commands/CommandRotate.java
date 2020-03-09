/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemDrive;

public class CommandRotate extends CommandBase {
  private static final double TIME_LIMIT = 10;

  private SubsystemDrive m_drive;
  private DoubleSupplier m_supplier;
  private double m_target;
  private double m_maxSpeed;
  private PIDController m_pid = new PIDController(0.02, 0, 0);
  private Timer m_timer = new Timer();
  /**
   * Creates a new CommandCenterShooter.
   */
  public CommandRotate(SubsystemDrive drive, DoubleSupplier supplier, double target, double maxSpeed) {
    m_drive = drive;
    m_supplier = supplier;
    m_target = target;
    m_maxSpeed = maxSpeed;
    m_pid.setSetpoint(target);
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double input = m_supplier.getAsDouble();
    if (input != Double.NaN) {
      System.out.println(input);
      double rotation = m_pid.calculate(input);
      rotation = Math.min(m_maxSpeed, Math.abs(rotation)) * Math.signum(rotation);
      m_drive.drive(0, 0, rotation);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_timer.get() > TIME_LIMIT) {
      return true;
    }

    double input = m_supplier.getAsDouble();
    return input == Double.NaN ? true : Math.abs(m_target - input) < 0.2;
  }
}
