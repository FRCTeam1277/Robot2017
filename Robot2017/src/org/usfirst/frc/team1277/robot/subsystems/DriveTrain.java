package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.RobotMap;
import org.usfirst.frc.team1277.robot.commands.OperatorDrive;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {
	public double supposedAngle = 0.0;
	public double rJitter = 0.1;
	public double rAdjust = 0.3;
	public double gyroJitter = 3;
	
	public void initialize() {
		if (RobotMap.ahrs.isConnected()) {
			RobotMap.ahrs.zeroYaw();
		}
		
		supposedAngle = 0.0;
		rJitter = RobotMap.prefs.getDouble("Rotation Jitter", 0.1);
		rAdjust = RobotMap.prefs.getDouble("Rotation Adjust", 0.3);
		gyroJitter = RobotMap.prefs.getDouble("Gyro Jitter", 3);
	}
	
    public void drive(double x, double y, double r) {
		SmartDashboard.putNumber("Input rotation", r);

		double angle = 0.0;
		
		if (RobotMap.ahrs.isConnected()) {
			angle = RobotMap.ahrs.getYaw();
		}
		
		//rotation stabilization (only when r is within specified range)
		if (r > -rJitter && r < rJitter) {
			//if angle is less than desired, rotate in positive direction
			if (angle < (supposedAngle - gyroJitter)) {
				r = 0.3;
			//if angle is greater than desired, rotate in negative direction
			} else if (angle > (supposedAngle + gyroJitter)) {
				r = -0.3;
			}
			
		//if r is outside of specified range (if rotation joystick is being pushed), set the desired angle to the actual angle
		} else {
			supposedAngle = angle;
		}

		SmartDashboard.putNumber("Gyro Angle", angle);
		SmartDashboard.putNumber("Supposed Angle", supposedAngle);
		SmartDashboard.putNumber("Output rotation", r);
		
		RobotMap.driveTrainRobotDrive.mecanumDrive_team1277(x, y, r);
    }
    
    public void initDefaultCommand() {
    	// This will happen if nothing else is happening.
    	setDefaultCommand(new OperatorDrive());
    }
}
