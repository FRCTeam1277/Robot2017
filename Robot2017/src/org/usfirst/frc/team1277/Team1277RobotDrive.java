package org.usfirst.frc.team1277;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class Team1277RobotDrive extends RobotDrive {
	public Team1277RobotDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	}

	public Team1277RobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
			SpeedController frontRightMotor, SpeedController rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	}

	public void mecanumDrive_team1277(double x, double y, double r) {
		//find magnitude of direction vector
		double v = Math.sqrt((x * x) + (y * y));
		
		//find angle of direction vector
		double a = Math.atan(y / x);
		
		//change range of arctan from [-pi/2,pi/2] to [0,2pi]
		if (x < 0) {
			a += Math.PI;
		} else if (a < 0) {
			a += 2.0 * Math.PI;
		}
		
		//get wheel values without rotation
		double v1 = v * Math.sin(a + (Math.PI / 4));
		double v2 = v * Math.sin(a - (Math.PI / 4));
		
		//add rotation and set motor values
		m_frontLeftMotor.set((v2 - r) / (1 + r));
		m_frontRightMotor.set((v1 + r) / (1 + r));
		m_rearLeftMotor.set((v1 - r) / (1 + r));
		m_rearRightMotor.set((v2 + r) / (1 + r));
	}
}
