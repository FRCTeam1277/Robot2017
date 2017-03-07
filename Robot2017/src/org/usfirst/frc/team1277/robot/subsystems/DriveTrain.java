package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.RobotMap;
import org.usfirst.frc.team1277.robot.commands.OperatorDrive;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {
	
	public double accelerationX;
	public double accelerationY;
	
	public double velocityX;
	public double velocityY;
	
	public double displacementX;
	public double displacementY;
	
	public void initialize() {
		accelerationX = RobotMap.ahrs.getWorldLinearAccelX();
		accelerationY = RobotMap.ahrs.getWorldLinearAccelY();
		
		velocityX = 0;
		velocityY = 0;
		
		displacementX = 0;
		displacementY = 0;
	}
	
    public void drive(double x, double y, double r) {
    	
    	displacementX += velocityX * 0.01;
    	displacementY += velocityY * 0.01;
    	
    	velocityX += accelerationX * 0.01;
    	velocityY += accelerationY * 0.01;
    	
		accelerationX = RobotMap.ahrs.getWorldLinearAccelX();
		accelerationY = RobotMap.ahrs.getWorldLinearAccelY();
    	
		RobotMap.driveTrainRobotDrive.mecanumDrive_team1277(x, y, r);
		
    	SmartDashboard.putNumber("Velocity Y", velocityY);
    	SmartDashboard.putNumber("Velocity X", velocityX);
		
    	SmartDashboard.putNumber("Displacement Y", displacementY);
    	SmartDashboard.putNumber("Displacement X", displacementX);
    }
    
    public void initDefaultCommand() {
    	// This will happen if nothing else is happening.
    	setDefaultCommand(new OperatorDrive());
    }
}
