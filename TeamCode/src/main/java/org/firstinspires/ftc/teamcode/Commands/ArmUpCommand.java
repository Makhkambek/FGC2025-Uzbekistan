package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;

public class ArmUpCommand extends CommandBase {
    private final ArmIntake armIntake;
    private final ElapsedTime timer = new ElapsedTime();
    private static final double DELAY_SECONDS = 0.5;

    public ArmUpCommand(ArmIntake armIntake) {
        this.armIntake = armIntake;
        addRequirements(armIntake);
    }

    @Override
    public void initialize() {
        armIntake.setArmState(ArmIntake.ArmState.UP);
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        return armIntake.getCurrentState() == ArmIntake.ArmState.IDLE && armIntake.getSubState() == 0
                && timer.seconds() >= DELAY_SECONDS;
    }
}