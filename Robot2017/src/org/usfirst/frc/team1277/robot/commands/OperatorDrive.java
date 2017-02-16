package org.usfirst.frc.team1277.robot.commands;

import org.usfirst.frc.team1277.robot.OI;
import org.usfirst.frc.team1277.robot.Robot;
import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OperatorDrive extends Command {
	public double supposedAngle = 0.0;
	public double rJitter = 0.1;
	public double rAdjust = 0.3;
	public double gyroJitter = 3;
	
	public OperatorDrive() {
         requires(Robot.driveTrain);
    }

	@Override
	protected void initialize() {
		if (RobotMap.ahrs.isConnected()) {
			RobotMap.ahrs.zeroYaw();
		}
		
		supposedAngle = 0.0;
		rJitter = RobotMap.prefs.getDouble("Rotation Jitter", 0.1);
		rAdjust = RobotMap.prefs.getDouble("Rotation Adjust", 0.3);
		gyroJitter = RobotMap.prefs.getDouble("Gyro Jitter", 3);

		Robot.driveTrain.initialize();
	}

	@Override
	protected void execute() {
		double r = OI.getJoystick().getRawAxis(0);
		double x = OI.getJoystick().getRawAxis(2);
		double y = OI.getJoystick().getRawAxis(3);
		
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

		Robot.driveTrain.drive(x, y, r);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
    	Robot.driveTrain.drive(0, 0, 0);
	}

	@Override
	protected void interrupted() {
    	Robot.driveTrain.drive(0, 0, 0);
	}
}
