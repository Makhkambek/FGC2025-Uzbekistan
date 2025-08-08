package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;
import org.firstinspires.ftc.teamcode.SubSystems.DrumIntake;
import org.firstinspires.ftc.teamcode.SubSystems.Hang;

public class ResetController {
    private final DrumIntake drumIntake;
    private final Hang hang;
    private final ArmIntake armIntake;
    private final GamepadEx gamepad;
    private boolean wasOptionsPressed = false;

    public ResetController(DrumIntake drumIntake, Hang hang, ArmIntake armIntake, GamepadEx gamepad) {
        this.drumIntake = drumIntake;
        this.hang = hang;
        this.armIntake = armIntake;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isOptionsPressed = gamepad.getButton(GamepadKeys.Button.START);

        if (isOptionsPressed && !wasOptionsPressed) {
            CommandScheduler.getInstance().cancelAll();
            drumIntake.stop();
            hang.stop();
            armIntake.stop();
        }

        wasOptionsPressed = isOptionsPressed;
    }
}