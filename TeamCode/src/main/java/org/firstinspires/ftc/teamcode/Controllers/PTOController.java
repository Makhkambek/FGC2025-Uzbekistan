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
    private boolean wasRightTriggerPressed = false;
    private boolean wasLeftTriggerPressed = false;

    public PTOController(PTO pto, GamepadEx gamepad) {
        this.pto = pto;
        this.gamepad = gamepad;
    }

    public void update() {
        double rightTrigger = gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
        double leftTrigger = gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);

        boolean isRightTriggerPressed = rightTrigger > 0.2;
        boolean isLeftTriggerPressed = leftTrigger > 0.2;

        if (isRightTriggerPressed && !wasRightTriggerPressed) {
            CommandScheduler.getInstance().schedule(new PTOForwardCommand(pto, 1.0));
        }

        if (isLeftTriggerPressed && !wasLeftTriggerPressed) {
            CommandScheduler.getInstance().schedule(new PTOReversedCommand(pto, 1.0));
        }

        wasRightTriggerPressed = isRightTriggerPressed;
        wasLeftTriggerPressed = isLeftTriggerPressed;
    }
}