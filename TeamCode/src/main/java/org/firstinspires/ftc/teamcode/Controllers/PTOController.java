package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.PTO;
import org.firstinspires.ftc.teamcode.Commands.PTOForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.PTOReversedCommand;

public class PTOController {
    private final PTO pto;
    private final GamepadEx gamepad;
    private int rightBumperToggle = 0;
    private boolean wasRightBumperPressed = false;

    public PTOController(PTO pto, GamepadEx gamepad) {
        this.pto = pto;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isRightBumperPressed = gamepad.getButton(GamepadKeys.Button.RIGHT_BUMPER);

        if (isRightBumperPressed && !wasRightBumperPressed) {
            // CommandScheduler.getInstance().cancelAll();

            rightBumperToggle = (rightBumperToggle + 1) % 2;

            if (rightBumperToggle == 0) {
                CommandScheduler.getInstance().schedule(new PTOForwardCommand(pto, 1.0));
            } else {
                CommandScheduler.getInstance().schedule(new PTOReversedCommand(pto, 1.0));
            }
        }

        wasRightBumperPressed = isRightBumperPressed;
    }
}