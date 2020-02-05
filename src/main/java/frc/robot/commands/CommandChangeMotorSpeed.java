/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemShooter;

public class CommandChangeMotorSpeed extends CommandBase {

  private final double SPEED_STEP = 0.01;

  boolean m_speedUp;
  SubsystemShooter m_shooter;

  /**
   * Creates a new CommandChangeMotorSpeed.
   */
  public CommandChangeMotorSpeed(boolean speedUp, SubsystemShooter shooter) {
    m_speedUp = speedUp;
    m_shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.changeMotorSpeedBy(m_speedUp ? SPEED_STEP : -SPEED_STEP);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}