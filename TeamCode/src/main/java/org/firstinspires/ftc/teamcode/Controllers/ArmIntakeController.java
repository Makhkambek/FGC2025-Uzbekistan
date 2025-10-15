package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;
import org.firstinspires.ftc.teamcode.Commands.ArmIntakeCommand;

public class ArmIntakeController {
    private final ArmIntake armIntake;
    private final GamepadEx gamepad;
    private static final int MAX_POSITION = 165;
    private static final int MIN_POSITION = 0;
    private static final int MIDDLE_POSITION = 80;
    private static final int HIGH_POSITION = 150;
    private boolean wasDpadDownPressed = false; 

    public ArmIntakeController(ArmIntake armIntake, GamepadEx gamepad) {
        this.armIntake = armIntake;
        this.gamepad = gamepad;
    }

    public void update() {
        if (gamepad.getButton(GamepadKeys.Button.Y)) {
            CommandScheduler.getInstance().cancelAll();
            CommandScheduler.getInstance().schedule(
                    new ArmIntakeCommand(armIntake, HIGH_POSITION, false, false)
            );
        } else if (gamepad.getButton(GamepadKeys.Button.A)) {
            CommandScheduler.getInstance().cancelAll();
            CommandScheduler.getInstance().schedule(
                    new ArmIntakeCommand(armIntake, MIN_POSITION, false, false)
            );
        }

        boolean isDpadDownPressed = gamepad.getButton(GamepadKeys.Button.DPAD_DOWN);
        if (isDpadDownPressed) {
            armIntake.setManualMode(true);
            armIntake.setManualPower(-0.2);
        } else if (wasDpadDownPressed) {
            armIntake.setManualMode(false);
            armIntake.setTarget(armIntake.getPosition());
        } else {
            armIntake.setManualMode(false);
        }
        wasDpadDownPressed = isDpadDownPressed;

        boolean isDpadPressed = gamepad.getButton(GamepadKeys.Button.DPAD_UP) ||
                gamepad.getButton(GamepadKeys.Button.DPAD_LEFT) ||
                gamepad.getButton(GamepadKeys.Button.DPAD_RIGHT);

        boolean isRightSideButtonPressed = gamepad.getButton(GamepadKeys.Button.X) ||
                gamepad.getButton(GamepadKeys.Button.B);

        if (isDpadPressed) {
            armIntake.retract();
        } else if (isRightSideButtonPressed) {
            armIntake.runGrippers();
        } else {
            armIntake.offGrippers();
        }

        armIntake.periodic();
    }
}