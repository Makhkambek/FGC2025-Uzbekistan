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
    private boolean wasXPressed = false;

    public ArmIntakeController(ArmIntake armIntake, GamepadEx gamepad) {
        this.armIntake = armIntake;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isLeftBumperPressed = gamepad.getButton(GamepadKeys.Button.LEFT_BUMPER);
        boolean isXPressed = gamepad.getButton(GamepadKeys.Button.X);

        // Обработка левого бампера (UP/DOWN)
        if (isLeftBumperPressed && !wasLeftBumperPressed) {
            // CommandScheduler.getInstance().cancelAll(); // Закомментировано, как в вашем коде

            leftBumperToggle = (leftBumperToggle + 1) % 2;

            if (leftBumperToggle == 0) {
                CommandScheduler.getInstance().schedule(new ArmUpCommand(armIntake));
            } else {
                CommandScheduler.getInstance().schedule(new ArmDownCommand(armIntake));
            }
        }

        // Обработка кнопки X (TAKE)
        if (isXPressed && !wasXPressed) {
            CommandScheduler.getInstance().cancelAll(); // Отменяем предыдущие команды для TAKE
            CommandScheduler.getInstance().schedule(new ArmTakeCommand(armIntake));
        }

        wasLeftBumperPressed = isLeftBumperPressed;
        wasXPressed = isXPressed;
    }
}