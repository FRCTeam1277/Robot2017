package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.RobotMap;
import org.usfirst.frc.team1277.robot.commands.OperatorDrive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	public void initialize() {
	}
	
    public void drive(double x, double y, double r) {		
		RobotMap.driveTrainRobotDrive.mecanumDrive_team1277(x, y, r);
    }
    
    public void initDefaultCommand() {
    	// This will happen if nothing else is happening.
    	setDefaultCommand(new OperatorDrive());
    }
}
