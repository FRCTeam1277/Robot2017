package org.usfirst.frc.team1277.robot.commands;

import org.usfirst.frc.team1277.robot.OI;
import org.usfirst.frc.team1277.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A testbed command
 * 
 * @author laptop
 *
 */
public class TurretDrive extends Command {
	public TurretDrive() {
         requires(Robot.turret);
    }

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.turret.rotate(OI.getJoystick().getX());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
