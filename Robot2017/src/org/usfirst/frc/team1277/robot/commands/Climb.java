package org.usfirst.frc.team1277.robot.commands;

import org.usfirst.frc.team1277.robot.Robot;
import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {
	public Climb() {
        requires(Robot.climber);
    }

	@Override
	protected void initialize() {
		Robot.climber.initialize();
	}

	@Override
	protected void execute() {
		Robot.climber.execute();
	}

	@Override
	protected void end() {
		RobotMap.climberMotor.set(0);
	}

	@Override
	protected boolean isFinished() {
//		return RobotMap.climberLimit.get();
		return false;
	}

}
