package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Controllers.DrumIntakeController;
import org.firstinspires.ftc.teamcode.Controllers.HangController;
import org.firstinspires.ftc.teamcode.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.Controllers.ResetController;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;
import org.firstinspires.ftc.teamcode.SubSystems.DrumIntake;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.SubSystems.Hang;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        DrumIntake drumIntake = new DrumIntake(hardwareMap);
        DriveTrain driveTrain = new DriveTrain(hardwareMap);
        Hang hang = new Hang(hardwareMap);
        ArmIntake armIntake = new ArmIntake(hardwareMap);

        GamepadEx gamepad1Ex = new GamepadEx(gamepad1);
        GamepadEx gamepad2Ex = new GamepadEx(gamepad2);

        DrumIntakeController drumController = new DrumIntakeController(drumIntake, gamepad2Ex);
        HangController hangController = new HangController(hang, gamepad2Ex);
        IntakeController intakeController = new IntakeController(armIntake, gamepad2Ex);
        ResetController resetController = new ResetController(drumIntake, hang, armIntake, gamepad2Ex);

        boolean wasRightTriggerPressed = false;

        waitForStart();

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();

            drumController.update();
            hangController.update();
            intakeController.update();
            resetController.update();

            boolean isRightTriggerPressed = gamepad1Ex.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.2;
            if (isRightTriggerPressed && !wasRightTriggerPressed) {
                driveTrain.setSlowMode(!driveTrain.isSlowMode());
            }
            wasRightTriggerPressed = isRightTriggerPressed;

            double drive = -gamepad1Ex.getLeftY();
            double turn = gamepad1Ex.getRightX();
            driveTrain.tankDrive(drive, turn);

            telemetry.update();
        }
    }
}