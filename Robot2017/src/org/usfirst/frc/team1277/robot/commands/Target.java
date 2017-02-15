package org.usfirst.frc.team1277.robot.commands;

import org.usfirst.frc.team1277.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Target extends Command {
	public Target() {
        requires(Robot.driveTrain);	// Controlled directly by Robot.camera
        requires(Robot.camera);		// See above
        requires(Robot.shooter);
   }

	@Override
	protected void initialize() {
		Robot.driveTrain.initialize();
		Robot.camera.enable();
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
		Robot.camera.disable();
    	Robot.driveTrain.drive(0, 0, 0);
	}

	@Override
	protected void interrupted() {
		Robot.camera.disable();
    	Robot.driveTrain.drive(0, 0, 0);
	}
}
