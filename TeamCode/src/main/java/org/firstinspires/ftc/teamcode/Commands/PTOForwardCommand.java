package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.PTO;

public class PTOForwardCommand extends CommandBase {
    private final PTO pto;
    private final double power;

    public PTOForwardCommand(PTO pto, double power) {
        this.pto = pto;
        this.power = power;

        addRequirements(pto);
    }

    @Override
    public void initialize() {
        pto.setPower(power);
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