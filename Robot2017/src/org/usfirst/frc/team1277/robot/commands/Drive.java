package org.usfirst.frc.team1277.robot.commands;

import org.usfirst.frc.team1277.robot.OI;
import org.usfirst.frc.team1277.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command {

	public Drive() {
         requires(Robot.driveTrain);
    }

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.driveTrain.drive(OI.getJoystick().getRawAxis(1), OI.getJoystick().getRawAxis(0), OI.getJoystick().getRawAxis(2));
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
