package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.DrumIntake;

public class DrumIntakeReverseCommand extends CommandBase {
    private final DrumIntake drumIntake;
    private final double power;

    public DrumIntakeReverseCommand(DrumIntake drumIntake, double power) {
        this.drumIntake = drumIntake;
        this.power = power;
        addRequirements(drumIntake);
    }

    @Override
    public void initialize() {
        drumIntake.setPower(-power);
    }


    @Override
    public void end(boolean interrupted) {
        drumIntake.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
