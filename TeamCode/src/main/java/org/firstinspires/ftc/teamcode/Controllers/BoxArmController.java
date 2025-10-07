package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.BoxArm;

public class BoxArmController {
    private final BoxArm boxArm;
    private final GamepadEx gamepad;
    private int rightBumperToggle = 0;
    private boolean wasRightBumperPressed = false;

    public BoxArmController(BoxArm boxArm, GamepadEx gamepad) {
        this.boxArm = boxArm;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isRightBumperPressed = gamepad.getButton(GamepadKeys.Button.RIGHT_BUMPER);

        if (isRightBumperPressed && !wasRightBumperPressed) {
            CommandScheduler.getInstance().cancelAll();

            rightBumperToggle = (rightBumperToggle + 1) % 2;

            if (rightBumperToggle == 0) {
                boxArm.openBox();
            } else {
                boxArm.closeBox();
            }
        }

        wasRightBumperPressed = isRightBumperPressed;
    }
}