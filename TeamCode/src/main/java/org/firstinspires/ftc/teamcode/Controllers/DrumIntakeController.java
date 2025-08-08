package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.DrumIntake;
import org.firstinspires.ftc.teamcode.Commands.DrumIntakeForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.DrumIntakeReverseCommand;

public class DrumIntakeController {
    private final DrumIntake drumIntake;
    private final GamepadEx gamepad;
    private int rightBumperToggle = 0;
    private boolean wasRightBumperPressed = false;

    public DrumIntakeController(DrumIntake drumIntake, GamepadEx gamepad) {
        this.drumIntake = drumIntake;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isRightBumperPressed = gamepad.getButton(GamepadKeys.Button.RIGHT_BUMPER);

        if (isRightBumperPressed && !wasRightBumperPressed) {
//            CommandScheduler.getInstance().cancelAll();

            rightBumperToggle = (rightBumperToggle + 1) % 2;

            if (rightBumperToggle == 0) {
                CommandScheduler.getInstance().schedule(new DrumIntakeForwardCommand(drumIntake, 1.0));
            } else {
                CommandScheduler.getInstance().schedule(new DrumIntakeReverseCommand(drumIntake, 1.0));
            }
        }

        wasRightBumperPressed = isRightBumperPressed;
    }
}