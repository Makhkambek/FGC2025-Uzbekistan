package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PTO extends SubsystemBase {
    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;
    private DcMotorEx middleMotor;

    public PTO(HardwareMap hardwareMap) {
        super();
        leftMotor = hardwareMap.get(DcMotorEx.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotorEx.class, "rightMotor");
        middleMotor = hardwareMap.get(DcMotorEx.class, "middleMotor");

        leftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightMotor.setDirection(DcMotorEx.Direction.FORWARD);
        middleMotor.setDirection(DcMotorEx.Direction.FORWARD);

        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        middleMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        leftMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        middleMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        stop();
    }

    public void setPower(double power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
        middleMotor.setPower(power);
    }

    public void stop() {
        setPower(0);
    }
}