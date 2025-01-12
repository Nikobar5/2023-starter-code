// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.REVPhysicsSim
import edu.wpi.first.math.system.plant.*
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
class Robot : TimedRobot() {
  val controller = XboxController(0)
  val motorFrontLeft = CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless)
  val motorFrontRight = CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless)
  val motorBackLeft = CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless)
  val motorBackRight = CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless)

  val motorsLeft = MotorControllerGroup(motorFrontLeft, motorBackLeft)
  val motorsRight =  MotorControllerGroup(motorFrontRight, motorBackRight)


  private var m_autonomousCommand: Command? = null
  private var m_robotContainer: RobotContainer? = null

  fun getAutonomousCommand(): Command? {
    return m_autonomousCommand
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  override fun robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = RobotContainer()
  }
  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   *
   * This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */

  override fun robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run()
  }

  /** This function is called once each time the robot enters Disabled mode.  */
  override fun disabledInit() {
  }

  override fun disabledPeriodic() {
  }

  /** This autonomous runs the autonomous command selected by your [RobotContainer] class.  */
  override fun autonomousInit() {
    m_autonomousCommand = m_robotContainer?.autonomousCommand

    // schedule the autonomous command (example)
    m_autonomousCommand?.schedule()
  }

  /** This function is called periodically during autonomous.  */
  override fun autonomousPeriodic() {
  }

  override fun teleopInit() {
    val forward = controller.rightTriggerAxis
            val rotation =  controller.rightX
    motorFrontLeft.setVoltage(forward)
    motorFrontLeft.setVoltage(forward)
    motorFrontLeft.setVoltage(forward)
    motorFrontLeft.setVoltage(forward)
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    m_autonomousCommand?.cancel()
  }

  /** This function is called periodically during operator control.  */
  override fun teleopPeriodic() {
    val scalar = 3.0
    val forward = controller.rightTriggerAxis
    val backward = controller.leftTriggerAxis
    val rotation = controller.rightX
    motorsLeft.setVoltage((forward - backward - rotation)* scalar)
    motorsRight.setVoltage((forward - backward + rotation)* scalar)
  }






  override fun testInit() {
    // Cancels all running commands at the start of test mode.
    //CommandScheduler.getInstance().cancelAll()
  }

  /** This function is called periodically during test mode.  */
  override fun testPeriodic() {
  }
}