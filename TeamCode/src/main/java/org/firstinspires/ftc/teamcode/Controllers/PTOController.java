package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.PTO;

public class PTOController {
    private final PTO pto;
    private final GamepadEx gamepad;

    public PTOController(PTO pto, GamepadEx gamepad) {
        this.pto = pto;
        this.gamepad = gamepad;
    }

    public void update() {
        double rightTrigger = gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
        double leftTrigger = gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);

        if (rightTrigger > 0.2) {
            pto.setPower(-rightTrigger);
        } else if (leftTrigger > 0.2) {
            pto.setPower(leftTrigger);
        } else {
            pto.stop();
        }
    }
}