/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Drivetrain motor ports

    public static final int MOTOR_DRIVE_REAR_RIGHT = 1;
    public static final int MOTOR_DRIVE_FRONT_RIGHT = 2;
    public static final int MOTOR_DRIVE_REAR_LEFT = 3;
    public static final int MOTOR_DRIVE_FRONT_LEFT = 4;

    // Xbox controller mappings

    public static final int CONTROLLER_BUTTON_A = 1;
    public static final int CONTROLLER_BUTTON_B = 2;
    public static final int CONTROLLER_BUTTON_X = 3;
    public static final int CONTROLLER_BUTTON_Y = 4;
    public static final int CONTROLLER_BUTTON_BUMPER_LEFT = 5;
    public static final int CONTROLLER_BUTTON_BUMPER_RIGHT = 6;
    public static final int CONTROLLER_BUTTON_SELECT = 7;
    public static final int CONTROLLER_BUTTON_START = 8;
    public static final int CONTROLLER_BUTTON_STICK_LEFT = 9;
    public static final int CONTROLLER_BUTTON_STICK_RIGHT = 10;

    public static final int CONTROLLER_DPAD_NONE = -1;
    public static final int CONTROLLER_DPAD_UP = 0;
    public static final int CONTROLLER_DPAD_UP_RIGHT = 45;
    public static final int CONTROLLER_DPAD_RIGHT = 90;
    public static final int CONTROLLER_DPAD_DOWN_RIGHT = 135;
    public static final int CONTROLLER_DPAD_DOWN = 180;
    public static final int CONTROLLER_DPAD_DOWN_LEFT = 225;
    public static final int CONTROLLER_DPAD_LEFT = 270;
    public static final int CONTROLLER_DPAD_UP_LEFT = 315;

    public static final int CONTROLLER_STICK_LEFT_X = 0;
    public static final int CONTROLLER_STICK_LEFT_Y = 1;
    public static final int CONTROLLER_STICK_RIGHT_X = 4;
    public static final int CONTROLLER_STICK_RIGHT_Y = 5;

    public static final int CONTROLLER_TRIGGER_LEFT = 2;
    public static final int CONTROLLER_TRIGGER_RIGHT = 3;

    // Ball belt mappings

    public static final int MOTOR_BALL_MOVER_0 = 1;
    public static final int MOTOR_BALL_MOVER_1 = 2;
    public static final int MOTOR_BALL_MOVER_2 = 3;
    public static final int MOTOR_BALL_MOVER_3 = 4;
    public static final int MOTOR_BALL_MOVER_4 = 5;

    public static final int SENSOR_BALL_MOVER_0 = 0;
    public static final int SENSOR_BALL_MOVER_1 = 1;
    public static final int SENSOR_BALL_MOVER_2 = 2;
    public static final int SENSOR_BALL_MOVER_3 = 3;
    public static final int SENSOR_BALL_MOVER_4 = 4;

    public static final int MOTOR_BALL_PICKUP_SPINNER = 7;
}
