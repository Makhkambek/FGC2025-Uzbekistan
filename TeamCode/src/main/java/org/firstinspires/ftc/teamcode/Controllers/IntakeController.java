package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;
import org.firstinspires.ftc.teamcode.Commands.ArmUpCommand;
import org.firstinspires.ftc.teamcode.Commands.ArmDownCommand;

public class IntakeController {
    private final ArmIntake armIntake;
    private final GamepadEx gamepad;
    private int leftBumperToggle = 0;
    private boolean wasLeftBumperPressed = false;

    public IntakeController(ArmIntake armIntake, GamepadEx gamepad) {
        this.armIntake = armIntake;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isLeftBumperPressed = gamepad.getButton(GamepadKeys.Button.LEFT_BUMPER);

        if (isLeftBumperPressed && !wasLeftBumperPressed) {
//            CommandScheduler.getInstance().cancelAll();

            leftBumperToggle = (leftBumperToggle + 1) % 2;

            if (leftBumperToggle == 0) {
                CommandScheduler.getInstance().schedule(new ArmUpCommand(armIntake));
            } else {
                CommandScheduler.getInstance().schedule(new ArmDownCommand(armIntake));
            }
        }

        wasLeftBumperPressed = isLeftBumperPressed;
    }
}