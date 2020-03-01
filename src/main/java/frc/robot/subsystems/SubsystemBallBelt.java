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
  * Use by SubsystemBallPickup to see if the BallBelt is ready for another ball.
  * Returns true if first ball position is empty.
  */
  public static boolean readyForBallPickup() {
    return !m_ballMoveGroups[0].seesBall();
  }

  /**
  * Use by SubsystemBallPickup to start Ball Pickup.
  * Starts first ball position motor if it is empty.
  */
  public static void startBallPickup() {
    if (!m_ballMoveGroups[0].seesBall()) {
      m_pickingUpBall = true;
      m_ballMoveGroups[0].start();
    }
  }

  /**
  * Use by SubsystemBallPickup to stop Ball Pickup.
  * Stops first ball position motor if it is running.
  */
  public static void stopBallPickup() {
    m_ballMoveGroups[0].stop();
    m_pickingUpBall = false;
  }

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
    m_ballMoveGroups[4].stop();
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

  private void printBallStatus() {
    for (int i = 0; i < m_ballMoveGroups.length; i++) {
      NetworkTableInstance.getDefault().getTable("data").getEntry("ballSensor" + i).setBoolean(m_ballMoveGroups[i].seesBall());
    }
  }

  private void sortBalls() {
    //Get the last ball position (this will be 4 since we have 5 postions)
    int lastPostion = m_ballMoveGroups.length - 1;

    //Check all ball positions starting with the second from last moving backward
    //We don't need to check the last ball position because there are no slots in front of it
    for(int i = lastPostion - 1; i >= 0; i--) {

      //If current Ball Group sees a ball
      BallMoveGroup currentBallGroup = m_ballMoveGroups[i];
      if (currentBallGroup.seesBall()) {

        //Used to track what position the ball needs to move to
        int moveToPosition = -1;

        //Check all the ball positions in front starting with the furtest moving backward
        for (int j=lastPostion; j>i; j--) {

          //If group in front of does not see a ball
          BallMoveGroup ballMoveGroupInFrontOfMe = m_ballMoveGroups[j];
          if (!ballMoveGroupInFrontOfMe.seesBall()) {

            //Turn on all motors in front that do not see a ball
            ballMoveGroupInFrontOfMe.start();

            //If the moveToPosition has not been set, set it to the current position
            //this will be the furtheset position out
            if (moveToPosition == -1)
              moveToPosition = j;
          }
          //else {
          //  ballMoveGroupInFrontOfMe.stop(); //Not sure if this line is needed
          //}
        }

        //If there is an open ball group in front of currentBallGroup
        //Turn on currentBallGroup and wait until the moveToPosition sees a ball
        //This will ensure the ball does not get stuck between sensors
        if (moveToPosition != -1) {
          currentBallGroup.start();

          //Use timer to make sure we don't loop forever
          m_timer.start();
          while (!m_ballMoveGroups[moveToPosition].seesBall() && m_timer.get() < 1);
          m_timer.stop();
          m_timer.reset();
        }
        else {
          if (i > 0 || (i == 0 && !m_pickingUpBall))
            currentBallGroup.stop();
        }
      }
      else { //Current Ball group does not see a ball

        //If currentBallGroup doesn't see a ball, stop it
        //But don't stop the first position if the robot is picking up a ball
        if (i > 0 || (i == 0 && !m_pickingUpBall))
          currentBallGroup.stop();

        //Stop everything in front of currentBallGroup
        //But don't stop the last ball position if the robot is shooting
        for (int j=lastPostion; j>i; j--) {
          if ((j != lastPostion) || (j == lastPostion && !m_shootingBall)) {
            BallMoveGroup ballMoveGroupInFrontOfMe = m_ballMoveGroups[j];
            ballMoveGroupInFrontOfMe.stop();
          }
        }
      }
    }
  }
}
