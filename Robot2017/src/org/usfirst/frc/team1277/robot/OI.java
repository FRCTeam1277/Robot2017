package org.usfirst.frc.team1277.robot;

import org.usfirst.frc.team1277.robot.commands.Climb;
import org.usfirst.frc.team1277.robot.commands.Target;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick joystick = new Joystick(0);
    public static Button button1 = new JoystickButton(joystick, 1);
    public static Button button3 = new JoystickButton(joystick, 3);
    
    public OI() {
    	// Run targeting while button1 is pressed.
    	button1.whileHeld(new Target());
    	
    	// Run climber while button 3 is pressed.
    	button3.whileHeld(new Climb());
    }
    
    public static Joystick getJoystick() {
        return joystick;
    }
}
