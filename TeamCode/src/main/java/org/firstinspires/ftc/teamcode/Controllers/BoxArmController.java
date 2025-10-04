package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.BoxArm;

public class BoxArmController {
    private final BoxArm boxArm;
    private final GamepadEx gamepad;
    private int rightTriggerToggle = 0;
    private boolean wasRightTriggerPressed = false;

    public BoxArmController(BoxArm boxArm, GamepadEx gamepad) {
        this.boxArm = boxArm;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isRightTriggerPressed = gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.2;

        if (isRightTriggerPressed && !wasRightTriggerPressed) {
            CommandScheduler.getInstance().cancelAll(); // Опционально, если нужно отменять другие команды

            rightTriggerToggle = (rightTriggerToggle + 1) % 2;

            if (rightTriggerToggle == 0) {
                boxArm.openBox(); // Прямой вызов open()
            } else {
                boxArm.closeBox(); // Прямой вызов close()
            }
        }

        wasRightTriggerPressed = isRightTriggerPressed;
    }
}