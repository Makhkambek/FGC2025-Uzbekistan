package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;
import org.firstinspires.ftc.teamcode.Commands.ArmUpCommand;
import org.firstinspires.ftc.teamcode.Commands.ArmDownCommand;
import org.firstinspires.ftc.teamcode.Commands.ArmTakeCommand;

public class ArmIntakeController {
    private final ArmIntake armIntake;
    private final GamepadEx gamepad;
    private int leftBumperToggle = 0;
    private boolean wasLeftBumperPressed = false;
    private boolean wasRightBumperPressed = false;

    public ArmIntakeController(ArmIntake armIntake, GamepadEx gamepad) {
        this.armIntake = armIntake;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isLeftBumperPressed = gamepad.getButton(GamepadKeys.Button.LEFT_BUMPER);
        boolean isRightBumperPressed = gamepad.getButton(GamepadKeys.Button.RIGHT_BUMPER);

        if (isLeftBumperPressed && !wasLeftBumperPressed) {
            leftBumperToggle = (leftBumperToggle + 1) % 2;

            if (leftBumperToggle == 0) {
                CommandScheduler.getInstance().schedule(new ArmUpCommand(armIntake));
            } else {
                CommandScheduler.getInstance().schedule(new ArmDownCommand(armIntake));
            }
        }

        if (isRightBumperPressed && !wasRightBumperPressed) {
            CommandScheduler.getInstance().cancelAll();
            CommandScheduler.getInstance().schedule(new ArmTakeCommand(armIntake));
        }

        double leftTrigger = gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
        if (leftTrigger > 0.2) {
            armIntake.retract();
        } else {
            armIntake.offGrippers();
        }

        wasLeftBumperPressed = isLeftBumperPressed;
        wasRightBumperPressed = isRightBumperPressed;
    }
}