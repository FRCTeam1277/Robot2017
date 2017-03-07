package org.usfirst.frc.team1277.robot.commands;

import org.usfirst.frc.team1277.robot.Robot;
import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveForwards extends Command {

	int time;
	int count;
	
    public DriveForwards(int timeCentis) {
    	time = timeCentis;
    	count = 0;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    
    protected void execute() {
    	count++;
    	Robot.driveTrain.drive(0, -0.75, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (count > time) return true;
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.driveTrainFrontLeftMotor.set(0);
    	RobotMap.driveTrainFrontRightMotor.set(0);
    	RobotMap.driveTrainRearLeftMotor.set(0);
    	RobotMap.driveTrainRearRightMotor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
