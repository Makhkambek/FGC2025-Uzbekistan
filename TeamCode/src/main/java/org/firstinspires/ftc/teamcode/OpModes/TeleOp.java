package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Controllers.PTOController;
import org.firstinspires.ftc.teamcode.Controllers.ArmIntakeController;
import org.firstinspires.ftc.teamcode.Controllers.ResetController;
import org.firstinspires.ftc.teamcode.SubSystems.BoxArm;
import org.firstinspires.ftc.teamcode.SubSystems.Clutch;
import org.firstinspires.ftc.teamcode.SubSystems.PTO;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        PTO pto = new PTO(hardwareMap);
        ArmIntake armIntake = new ArmIntake(hardwareMap);
        BoxArm boxArm = new BoxArm(hardwareMap);
        Clutch clutch = new Clutch(hardwareMap);
        DriveTrain driveTrain = new DriveTrain(hardwareMap);

        GamepadEx gamepad1Ex = new GamepadEx(gamepad1);
        GamepadEx gamepad2Ex = new GamepadEx(gamepad2);

        PTOController ptoController = new PTOController(pto, gamepad2Ex);
        ArmIntakeController armIntakeController = new ArmIntakeController(armIntake, gamepad2Ex);
        ResetController resetController = new ResetController(pto, armIntake, boxArm, clutch,  gamepad2Ex);

        boolean wasRightTriggerPressed = false;

        waitForStart();

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();

            ptoController.update();
            armIntakeController.update();
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