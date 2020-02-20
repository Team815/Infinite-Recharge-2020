/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BallMoveGroup;
import frc.robot.Constants;

public class SubsystemBallBelt extends SubsystemBase {

  private final BallMoveGroup[] m_ballMoveGroups = new BallMoveGroup[5];
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

    m_ballMoveGroups[0] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_0, motor0, motor1);
    m_ballMoveGroups[1] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_1, motor1, motor2);
    m_ballMoveGroups[2] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_2, motor2, motor3);
    m_ballMoveGroups[3] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_3, motor3, motor4);
    m_ballMoveGroups[4] = new BallMoveGroup(Constants.SENSOR_BALL_MOVER_4, motor4);
  }

  public void run() {
    String sensorReads = "";
    for (BallMoveGroup ballGroup : m_ballMoveGroups) {
      sensorReads += ballGroup.seesBall() + " ";
    }
    System.out.println(sensorReads);
    for(int i = 0; i < m_ballMoveGroups.length; i++) {
      BallMoveGroup ballMoveGroup = m_ballMoveGroups[i];
      boolean run = false;
      if (ballMoveGroup.seesBall()) {
        // Check if any ball group in front of this one does not see a ball
        for (int j = i + 1; j < m_ballMoveGroups.length; j++) {
          if (!m_ballMoveGroups[j].seesBall()) {
            run = true;
          }
        }
      }
      if (run) {
        ballMoveGroup.start();
      } else {
        ballMoveGroup.stop();
      }
    }
  }
}
