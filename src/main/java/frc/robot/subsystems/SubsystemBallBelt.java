/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BallMoveGroup;
import frc.robot.Constants;

public class SubsystemBallBelt extends SubsystemBase {

  private static BallMoveGroup[] m_ballMoveGroups = new BallMoveGroup[5];
  private static boolean m_pickingUpBall = false;
  private static boolean m_shootingBall = false;
  private Timer m_timer;

  /**
  * Use by SubsystemShooter to see if the Ball Belt is ready to shoot.
  * Returns true if last ball position has a ball.
  */
  public static boolean readyToShoot() {
    return m_ballMoveGroups[4].seesBall();
  }

  /**
  * Use by SubsystemShooter to assist Shooter.
  * Starts last ball position motor if it has a ball.
  */
  public static void startAssistBallShooter() {
    if (m_ballMoveGroups[4].seesBall()) {
      m_shootingBall = true;
      m_ballMoveGroups[4].start();
    }
  }

  /**
  * Use by SubsystemShooter to stop assisting Shooter.
  * Stops last ball position motor if it is running.
  */
  public static void stopAssistBallShooter() {
    m_ballMoveGroups[4].stop(0);
    m_shootingBall = false;
  }

  /**
   * Creates a new BallBelt.
   */
  public SubsystemBallBelt() {
    TalonSRX motor0 = new TalonSRX(Constants.MOTOR_BALL_MOVER_0);
    TalonSRX motor1 = new TalonSRX(Constants.MOTOR_BALL_MOVER_1);
    TalonSRX motor2 = new TalonSRX(Constants.MOTOR_BALL_MOVER_2);
    TalonSRX motor3 = new TalonSRX(Constants.MOTOR_BALL_MOVER_3);
    TalonSRX motor4 = new TalonSRX(Constants.MOTOR_BALL_MOVER_4);

    motor0.setInverted(true);
    motor1.setInverted(true);
    motor3.setInverted(true);

    m_ballMoveGroups[0] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_0, motor0);//, motor1);
    m_ballMoveGroups[1] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_1, motor1);//, motor2);
    m_ballMoveGroups[2] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_2, motor2);//, motor3);
    m_ballMoveGroups[3] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_3, motor3);//, motor4);
    //m_ballMoveGroups[3].setStoppingDuration(0.3);
    m_ballMoveGroups[4] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_4, motor4);

    m_timer = new Timer();
  }

  public void run() {
    printBallStatus();
    sortBalls();
  }

  public void runAndPickup() {
    run();
    m_ballMoveGroups[0].start();
  }

  private void printBallStatus() {
    for (int i = 0; i < m_ballMoveGroups.length; i++) {
      NetworkTableInstance.getDefault().getTable("data").getEntry("ballSensor" + i).setBoolean(m_ballMoveGroups[i].seesBall());
    }
  }

  private void sortBalls() {

    // Get first ball in the belt

    BallMoveGroup ballMoveGroupFirst = null;
    for (int i = 0; i < m_ballMoveGroups.length; i++) {
      if (m_ballMoveGroups[i].seesBall()) {
        ballMoveGroupFirst = m_ballMoveGroups[i];
        break;
      } else {
        m_ballMoveGroups[i].stop(1);
      }
    }

    // Null indicates no ball in the belt

    if (ballMoveGroupFirst == null) {
      return;
    }

    boolean emptySpaceFound = false;
    for (int i = m_ballMoveGroups.length - 1; m_ballMoveGroups[i] != ballMoveGroupFirst; i--) {
      if (!m_ballMoveGroups[i].seesBall()) {
        emptySpaceFound = true;
      }
      if (emptySpaceFound) {
        m_ballMoveGroups[i].start();
      } else {
        m_ballMoveGroups[i].stop(0);
      }
    }

    if (emptySpaceFound) {
      ballMoveGroupFirst.start();
    } else {
      ballMoveGroupFirst.stop(0);
    }
  }
}
