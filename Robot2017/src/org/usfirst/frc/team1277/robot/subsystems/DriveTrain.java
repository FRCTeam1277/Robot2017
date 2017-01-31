package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.RobotMap;
import org.usfirst.frc.team1277.robot.commands.Drive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
    public void drive(double x, double y, double rotation, double gyroAngle) {
    	RobotMap.driveTrainRobotDrive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new Drive());
    }
}
