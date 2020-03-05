/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD l the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class BallMoveGroup {

    private enum State {
        RUNNING,
        STOPPING,
        STOPPED
    };

    private double m_stoppingDuration = 1;
    private final DigitalInput m_sensor;
    private final TalonSRX[] m_motors;
    private State m_state = State.STOPPED;
    private final Timer m_timer = new Timer();

    public BallMoveGroup(int portSensor, TalonSRX... motors) {
        m_sensor = new DigitalInput(portSensor);
        m_motors = motors;
    }

    public boolean seesBall() {
        return !m_sensor.get();
    }

    public void start() {
        if (m_state != State.RUNNING) {
            m_state = State.RUNNING;
            m_timer.stop();
            m_timer.reset();
            set(.4);
        }
    }

    public void stop(double delay) {
        if (m_state == State.RUNNING) {
            m_state = State.STOPPING;
            m_timer.start();
        } else if (m_state == State.STOPPING && m_timer.get() > delay) {
            m_state = State.STOPPED;
            set(0);
        }
    }

    private void set(double output) {
        for (TalonSRX motor : m_motors) {
            motor.set(ControlMode.PercentOutput, output);
        }
    }
}
