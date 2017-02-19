package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
	PIDController mainController;
	PIDController feedController;

	class DebugPIDOutput implements PIDOutput {
		private PIDOutput pidOutput;
		private String key;
		
		public DebugPIDOutput(String key, PIDOutput pidOutput) {
			this.key = key;
			this.pidOutput = pidOutput;
		}
		
		@Override
		public void pidWrite(double output) {
			SmartDashboard.putNumber(key, output);
			pidOutput.pidWrite(output);
		}		
	}
	
	public void initialize() {
//		mainController = new PIDController(
//				RobotMap.prefs.getDouble("Shooter P", 0.001),		// Scale input to approx -0.2 -> 0.2
//				RobotMap.prefs.getDouble("Shooter I", 0.00001), 	// This multiples total sum of all errors, so it needs to be several orders of magnitude smaller than P
//				RobotMap.prefs.getDouble("Shooter D", 0.0),
//				RobotMap.prefs.getDouble("Shooter F", 0.7),
//				RobotMap.shooterMainEncoder,
//				new DebugPIDOutput("Shooter Main PID Output", RobotMap.shooterMainMotor)
//				);
//		
//		mainController.setSetpoint(RobotMap.prefs.getDouble("Shooter setPoint", 0.7));
		
//		feedController = new PIDController(
//				RobotMap.prefs.getDouble("Feed P", 0.001),		// Scale input to approx -0.2 -> 0.2
//				RobotMap.prefs.getDouble("Feed I", 0.00001), 	// This multiples total sum of all errors, so it needs to be several orders of magnitude smaller than P
//				RobotMap.prefs.getDouble("Feed D", 0.0),
//				RobotMap.prefs.getDouble("Feed F", 0.7),
//				RobotMap.shooterFeedEncoder,
//				new DebugPIDOutput("Shooter Feed PID Output", RobotMap.shooterFeedMotor)
//				);
//		
//		feedController.setSetpoint(RobotMap.prefs.getDouble("Feed setPoint", 0.7));;
				
//		mainController.enable();
//		feedController.enable();
		numSamples = 0;
		feedDelay = 0;
	}
	
	public void disable() {
//		mainController.disable();
		RobotMap.shooterMainMotor.set(0.0);
		RobotMap.shooterFeedMotor.set(0.0);
		numSamples = 0;
//		feedController.disable();
//		RobotMap.rangeFinder.setEnabled(false);
	}

    public void initDefaultCommand() {
    }

    private double distance[] = {
    		62, 72, 82, 92, 102
    };
    
    private double settings[] = {
    		0.7, 0.705, 0.714, 0.732, 0.75
    };
    
    private static final double V_MAX = 12.5;
    
	private double getSetPoint(double rangeInches) {
//		return RobotMap.prefs.getDouble("Speed", 100);
		
		if (rangeInches < distance[0]) {
			return 0.0;
		}
		
		if (rangeInches > distance[distance.length - 1]) {
			return 0.0;
		}
		
		double power = (V_MAX - RobotMap.power.getVoltage()) / V_MAX;
		
		for (int i=0; i<distance.length-1; i++) {
			if (rangeInches == distance[i]) {
				return settings[i] + power;
			}
			
			if (rangeInches > distance[i] && rangeInches < distance[i+1]) {
				double slope = (settings[i+1] - settings[i]) / (distance[i+1] - distance[i]);
				
				return ((rangeInches - distance[i]) * slope + settings[i]) + power;
			}
		}
		
		return 0.0;
	}
	
	int numSamples = 0;
	double maxRange = 0.0;
	int feedDelay = 0;
	
	public void shoot(boolean shoot) {
		SmartDashboard.putBoolean("Shoot", shoot);
		if (shoot) {
			SmartDashboard.putNumber("CIM speed", RobotMap.shooterMainEncoder.getRate());
			SmartDashboard.putNumber("RangeFinder", RobotMap.rangeFinder.getRangeInches());
			double range = RobotMap.rangeFinder.getRangeInches();
			
			if (numSamples < 10) {
				numSamples++;
				maxRange = Math.max(range, maxRange);
			} else {
				SmartDashboard.putNumber("Max Range", maxRange);
				double setPoint = getSetPoint(maxRange);
				SmartDashboard.putNumber("Shooter speed", setPoint);
				
				RobotMap.shooterMainMotor.set(setPoint);
	//			mainController.enable();
				
				if (feedDelay > RobotMap.prefs.getDouble("Feed Delay", 15)) {
					if ((feedDelay % 10) / 5 <= 0) {
						RobotMap.shooterFeedMotor.set(1.0);
					}
		//			feedController.enable();
				}
				
				feedDelay++;
			}
		} else {
			numSamples = 0;
			feedDelay = 0;
			
			RobotMap.shooterMainMotor.set(1.0);
//			mainController.disable();
			RobotMap.shooterFeedMotor.set(0.0);
//			feedController.disable();
		}
	}
}
