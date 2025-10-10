package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;

public class ArmIntakeCommand extends CommandBase {
    private final ArmIntake armIntake;
    private int targetPosition;
    private boolean runGrippers;
    private boolean retractGrippers;

    public ArmIntakeCommand(ArmIntake armIntake, int targetPosition, boolean runGrippers, boolean retractGrippers) {
        this.armIntake = armIntake;
        this.targetPosition = targetPosition;
        this.runGrippers = runGrippers;
        this.retractGrippers = retractGrippers;
        addRequirements(armIntake);
    }

    @Override
    public void initialize() {
        armIntake.setTarget(targetPosition);
        if (runGrippers) {
            armIntake.runGrippers();
        } else if (retractGrippers) {
            armIntake.retract();
        } else {
            armIntake.offGrippers();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(armIntake.getPosition() - armIntake.getTarget()) <= 5;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            armIntake.offGrippers();
        }
    }
}