package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Controllers.BoxArmController;
import org.firstinspires.ftc.teamcode.Controllers.ClutchController;
import org.firstinspires.ftc.teamcode.Controllers.PTOController;
import org.firstinspires.ftc.teamcode.Controllers.ArmIntakeController;
import org.firstinspires.ftc.teamcode.Controllers.ResetController;
import org.firstinspires.ftc.teamcode.Controllers.VisionController;
import org.firstinspires.ftc.teamcode.SubSystems.BoxArm;
import org.firstinspires.ftc.teamcode.SubSystems.Clutch;
import org.firstinspires.ftc.teamcode.SubSystems.PTO;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.SubSystems.Vision;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        PTO pto = new PTO(hardwareMap);
        ArmIntake armIntake = new ArmIntake(hardwareMap);
        Clutch clutch = new Clutch(hardwareMap);
        BoxArm boxArm = new BoxArm(hardwareMap, clutch);
        DriveTrain driveTrain = new DriveTrain(hardwareMap);
//        Vision vision = new Vision(hardwareMap);

        GamepadEx gamepad1Ex = new GamepadEx(gamepad1);
        GamepadEx gamepad2Ex = new GamepadEx(gamepad2);

        PTOController ptoController = new PTOController(pto, gamepad2Ex);
//        VisionController visionController = new VisionController(vision, telemetry, gamepad1);
        ArmIntakeController armIntakeController = new ArmIntakeController(armIntake, gamepad1Ex);
        BoxArmController boxArmController = new BoxArmController(boxArm, gamepad2Ex);
        ClutchController clutchController = new ClutchController(clutch, gamepad2Ex);
        ResetController resetController = new ResetController(pto, armIntake, boxArm, clutch, gamepad2Ex);

        waitForStart();

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();

            ptoController.update();
            armIntakeController.update();
            clutchController.update();
            boxArmController.update();
//            visionController.update();
            resetController.update();

            boolean isRightTriggerPressed = gamepad1Ex.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.4;
            driveTrain.setSlowMode(isRightTriggerPressed);

            double drive = gamepad1Ex.getLeftY();
            double turn = gamepad1Ex.getRightX();
            driveTrain.tankDrive(drive, turn);

            telemetry.update();
        }
    }
}