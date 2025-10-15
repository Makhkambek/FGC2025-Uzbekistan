package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class IntakeController {
    private final Intake intake;
    private final GamepadEx gamepad;

    public IntakeController(Intake intake, GamepadEx gamepad) {
        this.intake = intake;
        this.gamepad = gamepad;
    }

    public void update() {
        double rightTrigger = gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
        double leftTrigger = gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);

        if (rightTrigger > 0.2) {
            intake.setPower(-rightTrigger);
        } else if (leftTrigger > 0.2) {
            intake.setPower(leftTrigger);
        } else {
            intake.stop();
        }
    }
}