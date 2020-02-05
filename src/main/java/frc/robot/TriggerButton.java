/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Add your docs here.
 */
public class TriggerButton extends Button {

    DoubleSupplier m_trigger;

    public TriggerButton(DoubleSupplier trigger) {
        m_trigger = trigger;
    }

    @Override
    public boolean get() {
        return m_trigger.getAsDouble() > 0.5;
    }
}
