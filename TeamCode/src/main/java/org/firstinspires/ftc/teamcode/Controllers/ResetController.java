package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.SubSystems.BoxArm;
import org.firstinspires.ftc.teamcode.SubSystems.Clutch;
import org.firstinspires.ftc.teamcode.SubSystems.PTO;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;

public class ResetController {
    private final PTO pto;
    private final ArmIntake armIntake;
    private final BoxArm boxArm;
    private final Clutch clutch;
    private final GamepadEx gamepad;
    private boolean wasOptionsPressed = false;

    public ResetController(PTO pto, BoxArm boxArm, ArmIntake armIntake, Clutch clutch, GamepadEx gamepad) {
        this.pto = pto;
        this.armIntake = armIntake;
        this.boxArm = boxArm;
        this.clutch = clutch;
        this.gamepad = gamepad;
    }

    public void update() {
        boolean isOptionsPressed = gamepad.getButton(GamepadKeys.Button.START);

        if (isOptionsPressed && !wasOptionsPressed) {
            CommandScheduler.getInstance().cancelAll();
            pto.stop();
            armIntake.stop();
            boxArm.stop();
            clutch.stop();
        }

        wasOptionsPressed = isOptionsPressed;
    }
}