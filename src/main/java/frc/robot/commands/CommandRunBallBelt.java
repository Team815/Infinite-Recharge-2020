/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemBallBelt;

public class CommandRunBallBelt extends CommandBase {

  SubsystemBallBelt m_subsystemBallBelt;
  BooleanSupplier m_pickupRunning;
  BooleanSupplier m_shooterReady;

  /**
   * Creates a new CommandMoveBallBelt.
   */
  public CommandRunBallBelt(SubsystemBallBelt subsystemBallBelt, BooleanSupplier pickupRunning, BooleanSupplier shooterReady) {
    m_subsystemBallBelt = subsystemBallBelt;
    m_pickupRunning = pickupRunning;
    m_shooterReady = shooterReady;
    addRequirements(subsystemBallBelt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystemBallBelt.run();

    if (m_pickupRunning.getAsBoolean()) {
      m_subsystemBallBelt.startEntryMotor();
    }

    if (m_shooterReady.getAsBoolean()) {
      m_subsystemBallBelt.startExitMotor();
    }
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
