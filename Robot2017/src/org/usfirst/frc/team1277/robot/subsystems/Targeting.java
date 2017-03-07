package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.OI;
import org.usfirst.frc.team1277.robot.Robot;
import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Targeting extends Subsystem {
	
	class CameraPIDSource implements PIDSource {
		PIDSourceType sourceType = PIDSourceType.kDisplacement;
		
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			sourceType = pidSource;			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return sourceType;
		}

		@Override
		public double pidGet() {
			double input = 0.0;
			
			double[] defaultValue = new double[1];
			defaultValue[0] = 1e8;
			double[] allCenterX = RobotMap.contours.getNumberArray("centerX", defaultValue);
			if(allCenterX.length > 0) {
				double center = 0.0;
				for (int i=0; i< allCenterX.length; i++) {
					center += allCenterX[i];
				}
				
				center = center / allCenterX.length;
				
				if (OI.joystick.getRawButton(5)) {
					center -= 6;
				} else if (OI.joystick.getRawButton(7)) {
					center += 6;
				}

				input = VIEW_CENTER -  center;
			}
			
			SmartDashboard.putNumber("PID input", input);
			return input;
		}		
	}
	
	class StoredOutput implements PIDOutput {
		private double output;
		
		public double getOutput() {
			return output;
		}
		
		@Override
		public void pidWrite(double output) {
			this.output = output;
			usePIDOutput();
		}		
	}
	
	private PIDController cameraPid;
	private StoredOutput cameraPidOutput;
	
	private static double VIEW_CENTER = 160;
	private double epsilon;
	
	public Targeting() {
	}
	
	public void initialize() {
		
		cameraPidOutput = new StoredOutput();
		
		cameraPid = new PIDController(
				RobotMap.prefs.getDouble("Targeting P", 0.001),		// Scale input to approx -0.2 -> 0.2
				RobotMap.prefs.getDouble("Targeting I", 0.00001), 	// This multiples total sum of all errors, so it needs to be several orders of magnitude smaller than P
				RobotMap.prefs.getDouble("Targeting D", 0.0),		// This multiplies current error - previous error.
				new CameraPIDSource(),
				cameraPidOutput
				);
		
		epsilon = RobotMap.prefs.getDouble("Shooter Epsilon", 0.01);
		
		cameraPid.enable();
	}
	
	public void disable() {
		cameraPid.disable();
	}
	
	protected void usePIDOutput() {
		SmartDashboard.putNumber("Camera PID Output", cameraPidOutput.output);
		SmartDashboard.putBoolean("Sonar enabled", RobotMap.rangeFinder.isEnabled());
		SmartDashboard.putNumber("Sonar value", RobotMap.rangeFinder.getRangeInches());

		// Don't let output drop below a certain threshold, which is different
		// for clockwise and anticlockwise (because)
		if (cameraPidOutput.output > -0.2 && cameraPidOutput.output < -0.01) {
			cameraPidOutput.output = -0.2;
		}
		
		if (cameraPidOutput.output > 0.01 && cameraPidOutput.output < 0.3) {
			cameraPidOutput.output = 0.3;
		}
		
		// Implement camera deadzone
		SmartDashboard.putNumber("Error", cameraPid.getAvgError());
		
		if (Math.abs(cameraPid.getAvgError()) < RobotMap.prefs.getDouble("Deadzone Size", 9)) {
			cameraPidOutput.output = 0.0;
		}

		SmartDashboard.putNumber("Adjusted Camera PID Output", cameraPidOutput.output);
		
		// This breaks encapsulation.
		Robot.driveTrain.drive(0, 0, cameraPidOutput.output);
		
		
		Robot.shooter.shoot(
				Math.abs(cameraPidOutput.output) <= epsilon
				);
	}

	@Override
	protected void initDefaultCommand() {
		// There is no default for this.
	}
}
