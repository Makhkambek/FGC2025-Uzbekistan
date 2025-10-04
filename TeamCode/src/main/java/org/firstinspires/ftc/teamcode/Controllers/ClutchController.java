package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.Clutch;

public class ClutchController {
    private final Clutch clutch;
    private final GamepadEx gamepad;
    private boolean wasYPressed = false;
    private boolean wasXPressed = false;

    public ClutchController(Clutch clutch, GamepadEx gamepad) {
        this.clutch = clutch;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isYPressed = gamepad.getButton(GamepadKeys.Button.Y);
        boolean isXPressed = gamepad.getButton(GamepadKeys.Button.X);

        if (isYPressed && !wasYPressed) {
            CommandScheduler.getInstance().cancelAll(); // Опционально
            clutch.open(); // Прямой вызов open()
        }

        if (isXPressed && !wasXPressed) {
            CommandScheduler.getInstance().cancelAll(); // Опционально
            clutch.close(); // Прямой вызов close()
        }

        wasYPressed = isYPressed;
        wasXPressed = isXPressed;
    }
}