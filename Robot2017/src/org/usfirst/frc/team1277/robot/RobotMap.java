package org.usfirst.frc.team1277.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static SpeedController driveTrainFrontLeftMotor;
    public static SpeedController driveTrainRearLeftMotor;
    public static SpeedController driveTrainFrontRightMotor;
    public static SpeedController driveTrainRearRightMotor;

    public static RobotDrive driveTrainRobotDrive;
    
    public static void init() {    	
    	driveTrainFrontLeftMotor = new Spark(0);
        LiveWindow.addActuator("Drive Train", "Front Left Motor", (Spark) driveTrainFrontLeftMotor);
        driveTrainRearLeftMotor = new Spark(1);
        LiveWindow.addActuator("Drive Train", "Rear Left Motor", (Spark) driveTrainRearLeftMotor);
        
    	driveTrainFrontRightMotor = new Spark(2);
        LiveWindow.addActuator("Drive Train", "Front Right Motor", (Spark) driveTrainFrontRightMotor);
        driveTrainRearRightMotor = new Spark(3);
        LiveWindow.addActuator("Drive Train", "Rear Right Motor", (Spark) driveTrainRearRightMotor);
        
        driveTrainRobotDrive = new RobotDrive(driveTrainFrontLeftMotor, driveTrainRearLeftMotor, driveTrainFrontRightMotor, driveTrainRearRightMotor);
    }
}
