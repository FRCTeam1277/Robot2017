package org.usfirst.frc.team1277.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick joystick;
    
    public OI() {
    	joystick = new Joystick(0);
    }
    
    public static Joystick getJoystick() {
        return joystick;
    }
}
