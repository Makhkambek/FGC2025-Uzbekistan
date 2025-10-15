package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.Hang;

public class HangController {
    private final Hang hang;
    private final GamepadEx gamepad;

    public HangController(Hang hang, GamepadEx gamepad) {
        this.hang = hang;
        this.gamepad = gamepad;
    }

    public void update() {
        double rightTrigger = gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
        double leftTrigger = gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);

        if (rightTrigger > 0.2) {
            hang.setPower(-rightTrigger);
        } else if (leftTrigger > 0.2) {
            hang.setPower(leftTrigger);
        } else {
            hang.stop();
        }
    }
}