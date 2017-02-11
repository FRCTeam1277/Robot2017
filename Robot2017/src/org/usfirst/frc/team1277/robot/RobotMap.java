package org.usfirst.frc.team1277.robot;

import org.usfirst.frc.team1277.Team1277RobotDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static Preferences prefs;
	
    public static SpeedController driveTrainFrontLeftMotor;
    public static SpeedController driveTrainRearLeftMotor;
    public static SpeedController driveTrainFrontRightMotor;
    public static SpeedController driveTrainRearRightMotor;

	public static NetworkTable contours;

    public static Team1277RobotDrive driveTrainRobotDrive;
    
    public static AHRS ahrs;
    
    public static void init() {    	
		prefs = Preferences.getInstance();

		driveTrainFrontLeftMotor = new Spark(1);
        driveTrainRearLeftMotor = new Spark(0);        
    	driveTrainFrontRightMotor = new Spark(2);
        driveTrainRearRightMotor = new Spark(3);
        
        driveTrainRobotDrive = new Team1277RobotDrive(driveTrainFrontLeftMotor, driveTrainRearLeftMotor, driveTrainFrontRightMotor, driveTrainRearRightMotor);

		contours = NetworkTable.getTable("GRIP/myContoursReport");
		
		ahrs = new AHRS(SPI.Port.kMXP);
		if (ahrs.isConnected()) {
			ahrs.zeroYaw();
		}
    }
}
