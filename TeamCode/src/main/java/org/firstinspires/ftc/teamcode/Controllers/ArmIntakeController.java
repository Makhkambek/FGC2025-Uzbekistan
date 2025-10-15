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
    private static final int LEFT_STICK_TARGET = 80;
    private static final double JOYSTICK_SCALE_FACTOR = 5.0;
    private static final double JOYSTICK_DEADZONE = 0.1;

    public ArmIntakeController(ArmIntake armIntake, GamepadEx gamepad) {
        this.armIntake = armIntake;
        this.gamepad = gamepad;
    }

    public void update() {
        double rightStickY = -gamepad.getRightY();
        double leftStickY = -gamepad.getLeftY();

        if (Math.abs(leftStickY) > JOYSTICK_DEADZONE) {
            CommandScheduler.getInstance().cancelAll();
            CommandScheduler.getInstance().schedule(
                    new ArmIntakeCommand(armIntake, LEFT_STICK_TARGET, false, false)
            );
        }

        if (Math.abs(rightStickY) > JOYSTICK_DEADZONE) {
            CommandScheduler.getInstance().cancelAll();
            int currentTarget = armIntake.getTarget();
            int delta = (int) (rightStickY * JOYSTICK_SCALE_FACTOR);
            int newTarget = currentTarget + delta;
            newTarget = Math.max(MIN_POSITION, Math.min(MAX_POSITION, newTarget));
            CommandScheduler.getInstance().schedule(
                    new ArmIntakeCommand(armIntake, newTarget, false, false)
            );
        }

        boolean isDpadPressed = gamepad.getButton(GamepadKeys.Button.DPAD_UP) ||
                gamepad.getButton(GamepadKeys.Button.DPAD_DOWN) ||
                gamepad.getButton(GamepadKeys.Button.DPAD_LEFT) ||
                gamepad.getButton(GamepadKeys.Button.DPAD_RIGHT);

        boolean isRightSideButtonPressed = gamepad.getButton(GamepadKeys.Button.A) ||
                gamepad.getButton(GamepadKeys.Button.B) ||
                gamepad.getButton(GamepadKeys.Button.X) ||
                gamepad.getButton(GamepadKeys.Button.Y);

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