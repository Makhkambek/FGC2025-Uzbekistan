package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.ArmIntake;

public class ArmTakeCommand extends CommandBase {
    private final ArmIntake armIntake;
    private static final int POSITION_TOLERANCE = 5; // Допуск для достижения цели (±5 тиков)
    private static final int TAKE_POSITION = 80; // Целевая позиция — 80 тиков

    public ArmTakeCommand(ArmIntake armIntake) {
        this.armIntake = armIntake;
        addRequirements(armIntake);
    }

    @Override
    public void initialize() {
        armIntake.setTarget(TAKE_POSITION); // 80 тиков для TAKE
        armIntake.runGrippers(); // Включаем grippers
    }

    @Override
    public boolean isFinished() {
        int currentPosition = armIntake.getPosition();
        int target = TAKE_POSITION;
        return Math.abs(currentPosition - target) <= POSITION_TOLERANCE;
    }

    @Override
    public void end(boolean interrupted) {
        // PID продолжает удерживать позицию
    }
}