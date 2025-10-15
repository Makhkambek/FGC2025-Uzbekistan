package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.arcrobotics.ftclib.command.CommandScheduler;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.Vision;
import org.firstinspires.ftc.teamcode.Commands.DetectTagCommand;

public class VisionController {
    private final DetectTagCommand detectTagCommand;
    private final GamepadEx gamepadEx;

    public VisionController(Vision vision, Telemetry telemetry, Gamepad gamepad) {
        this.gamepadEx = new GamepadEx(gamepad);
        this.detectTagCommand = new DetectTagCommand(vision, telemetry, gamepad);

        new Trigger(() -> gamepadEx.getButton(GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(() -> CommandScheduler.getInstance().schedule(detectTagCommand))
                .whenInactive(() -> CommandScheduler.getInstance().cancel(detectTagCommand));
    }

    public void update() {

    }
}