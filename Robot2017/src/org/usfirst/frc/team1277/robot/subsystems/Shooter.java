package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	PIDController mainController;
	PIDController feedController;
	
	public void initialize() {
		mainController = new PIDController(
				RobotMap.prefs.getDouble("Shooter P", 0.001),		// Scale input to approx -0.2 -> 0.2
				RobotMap.prefs.getDouble("Shooter I", 0.00001), 	// This multiples total sum of all errors, so it needs to be several orders of magnitude smaller than P
				RobotMap.prefs.getDouble("Shooter D", 0.0),
				RobotMap.prefs.getDouble("Shooter Speed", 0.7),
				RobotMap.shooterMainEncoder,
				RobotMap.shooterMainMotor);
		
		mainController = new PIDController(
				RobotMap.prefs.getDouble("Feed P", 0.001),		// Scale input to approx -0.2 -> 0.2
				RobotMap.prefs.getDouble("Feed I", 0.00001), 	// This multiples total sum of all errors, so it needs to be several orders of magnitude smaller than P
				RobotMap.prefs.getDouble("Feed D", 0.0),
				RobotMap.prefs.getDouble("Feed Speed", 0.7),
				RobotMap.shooterFeedEncoder,
				RobotMap.shooterFeedMotor);
	}
	
    public void initDefaultCommand() {
    }

	public void shoot(boolean shoot) {
		if (shoot && !mainController.isEnabled()) {
			mainController.enable();
			feedController.enable();
		} else if (!shoot && mainController.isEnabled()) {
			mainController.disable();
			feedController.disable();
		}
	}
}
