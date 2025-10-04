package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;

public class ArmDownCommand extends CommandBase {
    private final ArmIntake armIntake;
    private static final int POSITION_TOLERANCE = 5;

    public ArmDownCommand(ArmIntake armIntake) {
        this.armIntake = armIntake;
        addRequirements(armIntake);
    }

    @Override
    public void initialize() {
        armIntake.setTarget(0);
        armIntake.offGrippers();
    }

    @Override
    public boolean isFinished() {
        int currentPosition = armIntake.getPosition();
        int target = 0;
        return Math.abs(currentPosition - target) <= POSITION_TOLERANCE;
    }

    @Override
    public void end(boolean interrupted) {
    }
}