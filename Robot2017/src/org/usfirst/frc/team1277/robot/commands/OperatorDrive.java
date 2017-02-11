package org.usfirst.frc.team1277.robot.commands;

import org.usfirst.frc.team1277.robot.OI;
import org.usfirst.frc.team1277.robot.Robot;
import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OperatorDrive extends Command {
	double supposedAngle = 0.0;
	double rJitter = 0.1;
	double rAdjust = 0.3;
	double gyroJitter = 3;
	
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
	}

	@Override
	protected void execute() {
		SmartDashboard.putString("OperatorDrive", "execute");
		
		double r = OI.getJoystick().getRawAxis(0);
		
		//rotation stabilization (only when r is within specified range)
		if (r > -rJitter && r < rJitter) {
			//if angle is less than desired, rotate in positive direction
			if (RobotMap.ahrs.getAngle() < (supposedAngle - gyroJitter)) {
				r = 0.3;
			//if angle is greater than desired, rotate in negative direction
			} else if (RobotMap.ahrs.getAngle() > (supposedAngle + gyroJitter)) {
				r = -0.3;
			}
			
		//if r is outside of specified range (if rotation joystick is being pushed), set the desired angle to the actual angle
		} else {
			supposedAngle = RobotMap.ahrs.getAngle();
		}

		double x = OI.getJoystick().getRawAxis(3);
		double y = OI.getJoystick().getRawAxis(2);
		
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
