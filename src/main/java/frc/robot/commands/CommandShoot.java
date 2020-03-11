/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemShooter;

public class CommandShoot extends CommandBase {

  private SubsystemShooter m_shooter;
  private Timer m_timer;
  private double m_time;

  /**
   * Creates a new CommandShoot.
   */
  public CommandShoot(SubsystemShooter shooter, double time) {
    m_shooter = shooter;
    m_time = time;
    m_timer = new Timer();
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.startShooter();
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_shooter.readyToShoot()) {
      m_shooter.startFeeder();
    } else {
      m_shooter.stopFeeder();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_time < 0 ? false : m_timer.get() > m_time;
  }
}
