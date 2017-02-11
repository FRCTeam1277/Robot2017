package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.RobotMap;
import org.usfirst.frc.team1277.robot.commands.TurretDrive;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A testbed subsystem
 * 
 * @author laptop
 *
 */
public class Turret extends Subsystem {
    public void rotate(double x) {
		RobotMap.driveTrainRearLeftMotor.set(x);
    }
    
    public void initDefaultCommand() {
    	// This will happen if nothing else is happening.
//    	setDefaultCommand(new TurretDrive());
    }
}
