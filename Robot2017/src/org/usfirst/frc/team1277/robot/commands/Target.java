package org.usfirst.frc.team1277.robot.commands;

import org.usfirst.frc.team1277.robot.Robot;
import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Target extends Command {
	public Target() {
        requires(Robot.driveTrain);	// Controlled directly by Robot.camera
        requires(Robot.targeting);		// See above
        requires(Robot.shooter);
   }

	@Override
	protected void initialize() {
		SmartDashboard.putBoolean("Is targeting?", true);
		Robot.driveTrain.initialize();
		Robot.shooter.initialize();
		Robot.targeting.initialize();
	}

	@Override
	protected void execute() {
		// It all happens in the background?
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
		// Order is important...
		Robot.targeting.disable();
		Robot.shooter.disable();
    	RobotMap.shooterFeedMotor.set(0.0);
    	RobotMap.shooterMainMotor.set(0.0);
    	Robot.driveTrain.drive(0, 0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
