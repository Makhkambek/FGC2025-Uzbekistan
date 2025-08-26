package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DrumIntake extends SubsystemBase {
    private DcMotorEx drumMotor;

    public DrumIntake(HardwareMap hardwareMap) {
        super();
        drumMotor = hardwareMap.get(DcMotorEx.class, "drumLeft");

        drumMotor.setDirection(DcMotorEx.Direction.FORWARD);

        drumMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        stop();
    }

    public void setPower(double power) {
        drumMotor.setPower(power);
    }

    public void stop() {
        setPower(0);
    }
}