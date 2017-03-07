package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.OI;
import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
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
		powerTotal = 0;
	}
	
	public void disable() {
//		mainController.disable();
		RobotMap.shooterMainMotor.set(0.0);
		RobotMap.shooterFeedMotor.set(0.0);
		numSamples = 0;
		powerTotal = 0;
//		feedController.disable();
//		RobotMap.rangeFinder.setEnabled(false);
	}

    public void initDefaultCommand() {
    }

    private double distance[] = {
    		61.8, 71.8, 82.5, 91.8
    };
    
    private double settings[] = {
    		0.69992, 0.71245, 0.71412, 0.739
    };
    
    
	private double getSetPoint(double rangeInches) {
//		return RobotMap.prefs.getDouble("Speed", 100);
		
		if (rangeInches < distance[0]) {
			return 0.0;
		}
		
		if (rangeInches > distance[distance.length - 1]) {
			return 0.0;
		}
		
		
		
		for (int i=0; i<distance.length-1; i++) {
			if (rangeInches == distance[i]) {
				return settings[i];
			}
			
			if (rangeInches > distance[i] && rangeInches < distance[i+1]) {
				double slope = (settings[i+1] - settings[i]) / (distance[i+1] - distance[i]);
				
				return ((rangeInches - distance[i]) * slope + settings[i]);
			}
		}
		
		return 0.0;
	}
	
	int numSamples = 0;
	double maxRange = 0.0;
	int feedDelay = 0;
	double powerTotal = 0;
	final double V_MAX = 12.8;
	
	public void shoot(boolean shoot) {
		SmartDashboard.putBoolean("Shoot", shoot);
		//shoot = !RobotMap.ahrs.isMoving();
		SmartDashboard.putBoolean("Is Moving?", RobotMap.ahrs.isMoving());
		if (!shoot) {
			HAL.setJoystickOutputs((byte) OI.joystick.getPort(), 0, (short) 65535, (short) 65535);
		}
		if (shoot) {
			SmartDashboard.putNumber("CIM speed", RobotMap.shooterMainEncoder.getRate());
			SmartDashboard.putNumber("RangeFinder", RobotMap.rangeFinder.getRangeInches());
			double range = RobotMap.rangeFinder.getRangeInches();
			SmartDashboard.putBoolean("Range valid", RobotMap.rangeFinder.isRangeValid());
			if (numSamples == 0) maxRange = 0;
			if (numSamples < 10) {
				if (range < 500) {
					numSamples++;
					maxRange = Math.max(range, maxRange);
					powerTotal += (V_MAX - RobotMap.power.getVoltage()) / V_MAX;
				}
			} else {
				double setPoint = getSetPoint(maxRange);
				if (OI.joystick.getRawButton(6)) {
					setPoint += 0.02;
					setPoint = getSetPoint(maxRange + 12);
				} else if (OI.joystick.getRawButton(8)) {
					setPoint -= 0.02;
				}
				double power = powerTotal / 10.0;
				SmartDashboard.putNumber("Measure Power", V_MAX - (power * V_MAX));
				SmartDashboard.putNumber("Max Range", maxRange);
				
				
				SmartDashboard.putNumber("Power Percent Added", power);
				RobotMap.shooterMainMotor.set(setPoint + power);
				
				SmartDashboard.putNumber("Shooter speed", RobotMap.shooterMainMotor.get());
	//			mainController.enable();
				
				if (feedDelay < 50) {
					feedDelay++;
					RobotMap.shooterFeedMotor.set(-0.7);
					RobotMap.shooterMainMotor.set(0.3);
				} else if (feedDelay < 80) {
					feedDelay++;
					RobotMap.shooterFeedMotor.set(1.0);
				} else if (feedDelay < 95) {
					feedDelay++;
					RobotMap.shooterFeedMotor.set(-0.7);
				} else {
					feedDelay = 50;
				}
				
				feedDelay++;
			}
		} else {
			maxRange = 0;
			powerTotal = 0;
			numSamples = 0;
			feedDelay = 0;
			
			RobotMap.shooterMainMotor.set(0.0);
//			mainController.disable();
			RobotMap.shooterFeedMotor.set(0.0);
//			feedController.disable();
		}
	}
	
	public void unjam() {
		RobotMap.shooterFeedMotor.set(-1.0);
		RobotMap.shooterMainMotor.set(-1.0);
	}
}
