package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DrumIntake extends SubsystemBase {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    public DrumIntake(HardwareMap hardwareMap) {
        super();
        leftMotor = hardwareMap.get(DcMotor.class, "drumLeft");
        rightMotor = hardwareMap.get(DcMotor.class, "drumRight");

        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        stop();
    }

    public void setPower(double power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    public void stop() {
        setPower(0);
    }
}