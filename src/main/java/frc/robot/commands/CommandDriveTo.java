/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemDrive;

public class CommandDriveTo extends CommandBase {
  SubsystemDrive m_drive;
  double m_horizontal;
  double m_vertical;
  double m_rotation;
  double m_time;
  Timer m_timer;

  /**
   * Creates a new CommandDriveTo.
   */
  public CommandDriveTo(SubsystemDrive drive, double horizontal, double vertical, double rotation, double time) {
    m_drive = drive;
    m_horizontal = horizontal;
    m_vertical = vertical;
    m_rotation = rotation;
    m_time = time;
    m_timer = new Timer();
    addRequirements(drive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.drive(m_horizontal, m_vertical, m_rotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.drive(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_timer.get() > m_time;
  }
}
