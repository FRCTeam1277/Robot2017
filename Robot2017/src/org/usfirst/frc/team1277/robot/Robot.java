
package org.usfirst.frc.team1277.robot;

import org.usfirst.frc.team1277.robot.commands.DriveBackwards;
import org.usfirst.frc.team1277.robot.commands.DriveToBaseline;
import org.usfirst.frc.team1277.robot.commands.ShootLeft;
import org.usfirst.frc.team1277.robot.commands.ShootRight;
import org.usfirst.frc.team1277.robot.commands.Target;
import org.usfirst.frc.team1277.robot.subsystems.Climber;
import org.usfirst.frc.team1277.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1277.robot.subsystems.Shooter;
import org.usfirst.frc.team1277.robot.subsystems.Targeting;
import org.usfirst.frc.team1277.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    public static DriveTrain driveTrain;
    public static Turret turret;	// Test bed
    public static Shooter shooter;
    public static Targeting targeting;
    public static Climber climber;
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		RobotMap.init();
		
        driveTrain = new DriveTrain();
        turret = new Turret();
        targeting = new Targeting();
        shooter = new Shooter();
        climber = new Climber();
        
        oi = new OI();
		chooser.addDefault("Drive To Baseline", new DriveToBaseline());
		chooser.addObject("Shoot to the Left", new ShootLeft());
		chooser.addObject("Shoot to the Right", new ShootRight());
		
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
