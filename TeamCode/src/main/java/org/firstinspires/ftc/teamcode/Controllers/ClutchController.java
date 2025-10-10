package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.Clutch;
public class ClutchController {
    private final Clutch clutch;
    private final GamepadEx gamepad;
    private int leftBumperToggle = 0;
    private boolean wasLeftBumperPressed = false;

    public ClutchController(Clutch clutch, GamepadEx gamepad) {
        this.clutch = clutch;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isLeftBumperPressed = gamepad.getButton(GamepadKeys.Button.LEFT_BUMPER);

        if (isLeftBumperPressed && !wasLeftBumperPressed) {
            CommandScheduler.getInstance().cancelAll();

            leftBumperToggle = (leftBumperToggle + 1) % 2;

            if (leftBumperToggle == 0) {
                clutch.close();
            } else {
                clutch.open();
            }
        }

        wasLeftBumperPressed = isLeftBumperPressed;
    }
}