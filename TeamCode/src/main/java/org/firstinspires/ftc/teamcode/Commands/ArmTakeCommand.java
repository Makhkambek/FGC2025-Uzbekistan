package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;

public class ArmTakeCommand extends CommandBase {
    private final ArmIntake armIntake;
    private static final int POSITION_TOLERANCE = 5;
    private static final int TAKE_POSITION = 80;

    public ArmTakeCommand(ArmIntake armIntake) {
        this.armIntake = armIntake;
        addRequirements(armIntake);
    }

    @Override
    public void initialize() {
        armIntake.setTarget(TAKE_POSITION);
        armIntake.runGrippers();
    }

    @Override
    public boolean isFinished() {
        int currentPosition = armIntake.getPosition();
        int target = TAKE_POSITION;
        return Math.abs(currentPosition - target) <= POSITION_TOLERANCE;
    }

    @Override
    public void end(boolean interrupted) {
    }
}