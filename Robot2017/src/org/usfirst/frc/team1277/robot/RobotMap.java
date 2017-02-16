package org.usfirst.frc.team1277.robot;

import org.usfirst.frc.team1277.Team1277RobotDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;
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

    public static SpeedController shooterMainMotor;
    public static Encoder shooterMainEncoder;
    public static SpeedController shooterFeedMotor;
    public static Encoder shooterFeedEncoder;

    public static SpeedController climberMotor;
    public static DigitalInput climberLimit;

    public static Ultrasonic rangeFinder;
    
	public static NetworkTable contours;

    public static Team1277RobotDrive driveTrainRobotDrive;
    
    public static Servo gearServo;
    
    public static AHRS ahrs;
    
    public static final int PWM_DRIVE_REAR_LEFT = 0;
    public static final int PWM_DRIVE_FRONT_LEFT = 1;
    public static final int PWM_DRIVE_FRONT_RIGHT = 2;
    public static final int PWM_DRIVE_REAR_RIGHT = 3;
    public static final int PWM_SHOOTER_MAIN  = 4;
    public static final int PWM_SHOOTER_FEED  = 5;
    public static final int PWM_GEAR_SERVO  = 6;
    public static final int PWM_CLIMBER  = 7;
    
    public static final int DIO_PING = 0;
    public static final int DIO_ECHO = 1;
    public static final int DIO_MAIN_ENCODER_A = 2;
    public static final int DIO_MAIN_ENCODER_B = 3;
    public static final int DIO_FEED_ENCODER_A = 4;
    public static final int DIO_FEED_ENCODER_B = 5;
    public static final int DIO_CLIMBER_LIMIT = 6;
    
    public static void init() {    	
		prefs = Preferences.getInstance();

		driveTrainFrontLeftMotor = new Spark(PWM_DRIVE_FRONT_LEFT);
        driveTrainRearLeftMotor = new Spark(PWM_DRIVE_REAR_LEFT);        
    	driveTrainFrontRightMotor = new Spark(PWM_DRIVE_FRONT_RIGHT);
        driveTrainRearRightMotor = new Spark(PWM_DRIVE_REAR_RIGHT);
        
        rangeFinder = new Ultrasonic(DIO_PING, DIO_ECHO); // DigitalOutput 0 = ping,  DigitalInput 0 = echo
        
        shooterMainMotor = new Spark(PWM_SHOOTER_MAIN);
        shooterMainEncoder = new Encoder(DIO_MAIN_ENCODER_A, DIO_MAIN_ENCODER_B, false, Encoder.EncodingType.k4X);
        shooterFeedMotor = new Spark(PWM_SHOOTER_FEED);
        shooterFeedEncoder = new Encoder(DIO_FEED_ENCODER_A, DIO_FEED_ENCODER_B, false, Encoder.EncodingType.k4X);
        
        climberMotor = new Spark(PWM_CLIMBER);
        climberLimit = new DigitalInput(DIO_CLIMBER_LIMIT);

        driveTrainRobotDrive = new Team1277RobotDrive(driveTrainFrontLeftMotor, driveTrainRearLeftMotor, driveTrainFrontRightMotor, driveTrainRearRightMotor);

		contours = NetworkTable.getTable("GRIP/myContoursReport");
		
		gearServo = new Servo(PWM_GEAR_SERVO);
		
		ahrs = new AHRS(SPI.Port.kMXP);
		if (ahrs.isConnected()) {
			ahrs.zeroYaw();
		}
    }
}
