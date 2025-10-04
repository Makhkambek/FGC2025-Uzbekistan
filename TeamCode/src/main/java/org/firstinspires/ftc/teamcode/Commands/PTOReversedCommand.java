package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.PTO;

public class PTOReversedCommand extends CommandBase {
    private final PTO pto;
    private final double power;

    public PTOReversedCommand(PTO pto, double power) {
        this.pto = pto;
        this.power = power;

        addRequirements(pto);
    }

    @Override
    public void initialize() {
        pto.setPower(-power);
    }

    @Override
    public void end(boolean interrupted) {
        pto.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}