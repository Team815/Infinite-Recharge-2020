/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemDrive;

public class CommandDrive extends CommandBase {

  SubsystemDrive m_drive;
  DoubleSupplier m_speedHorizontal;
  DoubleSupplier m_speedVertical;
  DoubleSupplier m_speedRotational;

  /**
   * Creates a new CommandDrive.
   */
  public CommandDrive(SubsystemDrive drive, DoubleSupplier speedHorizontal, DoubleSupplier speedVertical, DoubleSupplier speedRotational) {
    m_drive = drive;
    m_speedHorizontal = speedHorizontal;
    m_speedVertical = speedVertical;
    m_speedRotational = speedRotational;

    addRequirements(drive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.driveManual(m_speedHorizontal.getAsDouble(), m_speedVertical.getAsDouble(), m_speedRotational.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
