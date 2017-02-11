package org.usfirst.frc.team1277.robot.commands;

import org.usfirst.frc.team1277.robot.OI;
import org.usfirst.frc.team1277.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OperatorDrive extends Command {
	public OperatorDrive() {
         requires(Robot.driveTrain);
    }

	@Override
	protected void initialize() {
		Robot.driveTrain.initialize();
	}

	@Override
	protected void execute() {
		double r = OI.getJoystick().getRawAxis(0);
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
