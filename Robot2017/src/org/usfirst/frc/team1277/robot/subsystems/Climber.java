package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	public void initialize() {
	}

    public void execute() {		
		RobotMap.climberMotor.set(0.75);
    }
    
	@Override
	protected void initDefaultCommand() {
	}
}
