/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Camera;
import frc.robot.subsystems.SubsystemDrive;

public class CommandCenterShooter extends CommandBase {
  private SubsystemDrive m_drive;
  private Camera m_camera;
  private PIDController pid = new PIDController(-0.013, 0, 0);
  /**
   * Creates a new CommandCenterShooter.
   */
  public CommandCenterShooter(SubsystemDrive drive, Camera camera) {
    m_drive = drive;
    m_camera = camera;
    addRequirements(m_drive);
    pid.setSetpoint(0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Vector2d coordinates = m_camera.getCoordinates();
    if (coordinates != null) {
      double rotation = pid.calculate(coordinates.x);
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
    Vector2d coordinates = m_camera.getCoordinates();
    return coordinates == null ? true : Math.abs(coordinates.x) < 0.2;
  }
}
