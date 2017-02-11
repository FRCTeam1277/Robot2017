package org.usfirst.frc.team1277.robot.subsystems;

import org.usfirst.frc.team1277.robot.Robot;
import org.usfirst.frc.team1277.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TargetingCamera extends PIDSubsystem {

	private static double VIEW_CENTER = 160;

	public TargetingCamera() {
		super("Camera",	// Name
				RobotMap.prefs.getDouble("Targeting P", 0.001),		// Scale input to approx -0.2 -> 0.2
				RobotMap.prefs.getDouble("Targeting I", 0.00001), 	// This multiples total sum of all errors, so it needs to be several orders of magnitude smaller than P
				RobotMap.prefs.getDouble("Targeting D", 0.0)		// This multiplies current error - previous error.
				);
	}
	
	@Override
	protected double returnPIDInput() {
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
			
			input = center - VIEW_CENTER;
		}
		
		SmartDashboard.putNumber("PID input", input);
		return input;
	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("PID Output", output);
		
		// This breaks encapsulation.
		Robot.driveTrain.drive(0, 0, output);
	}

	@Override
	protected void initDefaultCommand() {
		// There is no default for this.
	}
}
