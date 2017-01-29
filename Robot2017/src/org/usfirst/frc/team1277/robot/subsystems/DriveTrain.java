package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.RobotMap;
import org.usfirst.frc.team1277.robot.commands.Drive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
    public void drive(double magnitude, double direction, double rotation) {
    	RobotMap.driveTrainRobotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new Drive());
    }
}
