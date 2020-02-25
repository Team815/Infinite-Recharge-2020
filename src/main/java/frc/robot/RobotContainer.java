/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.CommandChangeMotorSpeed;
import frc.robot.commands.CommandDrive;
import frc.robot.commands.CommandRunBallBelt;
import frc.robot.commands.CommandShoot;
import frc.robot.commands.CommandStartBallSpinner;
import frc.robot.subsystems.SubsystemBallBelt;
import frc.robot.subsystems.SubsystemBallPickup;
import frc.robot.subsystems.SubsystemDrive;
import frc.robot.subsystems.SubsystemShooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final XboxController m_controller = new XboxController(0);
  private final SubsystemDrive m_subsystemDrive = new SubsystemDrive();
  private final SubsystemBallPickup m_subsystemBallPickup = new SubsystemBallPickup();
  private final SubsystemBallBelt m_subsystemBallBelt = new SubsystemBallBelt();
  private final SubsystemShooter m_subsystemShooter = new SubsystemShooter();
  private final CommandDrive m_commandDrive = new CommandDrive(
    m_subsystemDrive,
    () -> m_controller.getX(Hand.kLeft),
    () -> -m_controller.getY(Hand.kLeft),
    () -> m_controller.getX(Hand.kRight));

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_subsystemDrive.setDefaultCommand(m_commandDrive);
    m_subsystemBallBelt.setDefaultCommand(new CommandRunBallBelt(m_subsystemBallBelt));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    final TriggerButton triggerLeft = new TriggerButton(() -> m_controller.getTriggerAxis(Hand.kLeft));
    final TriggerButton triggerRight = new TriggerButton(() -> m_controller.getTriggerAxis(Hand.kRight));

    final JoystickButton buttonA = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_A);
    final JoystickButton buttonB = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_B);
    final JoystickButton buttonX = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_X);
    final JoystickButton buttonY = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_Y);
    final JoystickButton buttonBumperLeft = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_BUMPER_LEFT);
    final JoystickButton buttonBumperRight = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_BUMPER_RIGHT);
    final JoystickButton buttonSelect = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_SELECT);
    final JoystickButton buttonStart = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_START);
    final JoystickButton buttonJoystickLeft = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_STICK_LEFT);
    final JoystickButton buttonJoystickRight = new JoystickButton(m_controller, Constants.CONTROLLER_BUTTON_STICK_RIGHT);

    final POVButton dpadNone = new POVButton(m_controller, Constants.CONTROLLER_DPAD_NONE);
    final POVButton dpadUp = new POVButton(m_controller, Constants.CONTROLLER_DPAD_UP);
    final POVButton dpadUpLeft = new POVButton(m_controller, Constants.CONTROLLER_DPAD_UP_LEFT);
    final POVButton dpadLeft = new POVButton(m_controller, Constants.CONTROLLER_DPAD_LEFT);
    final POVButton dpadDownLeft = new POVButton(m_controller, Constants.CONTROLLER_DPAD_DOWN_LEFT);
    final POVButton dpadDown = new POVButton(m_controller, Constants.CONTROLLER_DPAD_DOWN);
    final POVButton dpadDownRight = new POVButton(m_controller, Constants.CONTROLLER_DPAD_DOWN_RIGHT);
    final POVButton dpadRight = new POVButton(m_controller, Constants.CONTROLLER_DPAD_RIGHT);
    final POVButton dpadUpRight = new POVButton(m_controller, Constants.CONTROLLER_DPAD_UP_RIGHT);

    triggerRight.whenHeld(new CommandShoot(m_subsystemShooter));
    //dpadUp.whenPressed(new CommandChangeMotorSpeed(true, m_subsystemShooter));
    //dpadDown.whenPressed(new CommandChangeMotorSpeed(false, m_subsystemShooter));
    triggerLeft.whenHeld(new CommandStartBallSpinner(m_subsystemBallPickup));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
