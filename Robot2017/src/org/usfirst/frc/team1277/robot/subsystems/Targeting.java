package org.usfirst.frc.team1277.robot.subsystems;

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
	private PIDController sonarPid;
	private StoredOutput cameraPidOutput;
	private StoredOutput sonarPidOutput;
	
	private static double VIEW_CENTER = 160;
	private double epsilon;
	
	public Targeting() {
	}
	
	public void initialize() {
		cameraPidOutput = new StoredOutput();
		sonarPidOutput = new StoredOutput();
		
		cameraPid = new PIDController(
				RobotMap.prefs.getDouble("Targeting P", 0.001),		// Scale input to approx -0.2 -> 0.2
				RobotMap.prefs.getDouble("Targeting I", 0.00001), 	// This multiples total sum of all errors, so it needs to be several orders of magnitude smaller than P
				RobotMap.prefs.getDouble("Targeting D", 0.0),		// This multiplies current error - previous error.
				new CameraPIDSource(),
				cameraPidOutput
				);
		
		sonarPid = new PIDController(
				RobotMap.prefs.getDouble("Sonar P", 0.001),		// Scale input to approx -0.2 -> 0.2
				RobotMap.prefs.getDouble("Sonar I", 0.00001), 	// This multiples total sum of all errors, so it needs to be several orders of magnitude smaller than P
				RobotMap.prefs.getDouble("Sonar D", 0.0),		// This multiplies current error - previous error.
				RobotMap.rangeFinder,
				sonarPidOutput
				);
		
		epsilon = RobotMap.prefs.getDouble("Shooter Epsilon", 0.01);
		
		cameraPid.enable();
		sonarPid.enable();
	}
	
	public void disable() {
		cameraPid.disable();
		sonarPid.disable();
	}
	
	protected void usePIDOutput() {
		SmartDashboard.putNumber("Camera PID Output", cameraPidOutput.output);

		if (cameraPidOutput.output > -0.2 && cameraPidOutput.output < -0.01) {
			cameraPidOutput.output = -0.2;
		}
		
		if (cameraPidOutput.output > 0.01 && cameraPidOutput.output < 0.3) {
			cameraPidOutput.output = 0.3;
		}
		
		SmartDashboard.putNumber("Error", cameraPid.getAvgError());
		
		if (Math.abs(cameraPid.getAvgError()) < 10) {
			cameraPidOutput.output = 0.0;
		}

		SmartDashboard.putNumber("Adjusted Camera PID Output", cameraPidOutput.output);
		SmartDashboard.putNumber("Sonar PID Output", sonarPidOutput.output);
		
		// This breaks encapsulation.
		Robot.driveTrain.drive(0, sonarPidOutput.output, cameraPidOutput.output);
		Robot.shooter.shoot(
				Math.abs(sonarPidOutput.output) <= epsilon &&
				Math.abs(cameraPidOutput.output) <= epsilon
				);
	}

	@Override
	protected void initDefaultCommand() {
		// There is no default for this.
	}
}
