package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArmIntake extends SubsystemBase {
    private final CRServo servo3, servo4;
    private final DcMotorEx ArmIntakeMotor;

    public static final int ZERO = 0;
    public static final int TARGET = 150;

    private static final double kP = 0.002;
    private static final double kI = 0.00;
    private static final double kD = 0.00;
    private static final double kF = 0.015;
    private static final double INTEGRAL_LIMIT = 50.0;

    private static final double GRIPPER_ON = 1.0;
    private static final double GRIPPER_OFF = 0.0;
    private static final double GRIPPER_RETRACT = -1.0;

    private double integralSum = 0;
    private double lastError = 0;
    private int targetPosition;
    private int doorStartPosition;
    private final ElapsedTime timer = new ElapsedTime();

    public ArmIntake(HardwareMap hardwareMap) {
        servo3 = hardwareMap.get(CRServo.class, "servo3");
        servo4 = hardwareMap.get(CRServo.class, "servo4");
        ArmIntakeMotor = hardwareMap.get(DcMotorEx.class, "ArmIntakeMotor");

        servo3.setDirection(CRServo.Direction.REVERSE);
        ArmIntakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        ArmIntakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmIntakeMotor.setDirection(DcMotorEx.Direction.REVERSE);

        doorStartPosition = ArmIntakeMotor.getCurrentPosition();
        targetPosition = doorStartPosition;
        timer.reset();
    }

    public void stepTargetMinus150() {
        targetPosition -= 150;
    }

    public void resetTargetToZero() {
        targetPosition = ZERO;
    }

    public void setTarget(int targetPosition) {
        this.targetPosition = targetPosition;
    }

    public void runGrippers() {
        servo3.setPower(GRIPPER_ON);
        servo4.setPower(GRIPPER_ON);
    }

    public void offGrippers() {
        servo3.setPower(GRIPPER_OFF);
        servo4.setPower(GRIPPER_OFF);
    }

    public void retract() {
        servo3.setPower(GRIPPER_RETRACT);
        servo4.setPower(GRIPPER_RETRACT);
    }

    public void stop() {
        offGrippers();
        ArmIntakeMotor.setPower(0.0);
        ArmIntakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        targetPosition = ZERO;
        integralSum = 0.0;
        lastError = 0.0;
        timer.reset();
    }

    @Override
    public void periodic() {
        double dt = timer.seconds();
        if (dt < 1e-3) dt = 1e-3;

        double current = ArmIntakeMotor.getCurrentPosition();
        double error = targetPosition - current;

        integralSum += error * dt;
        integralSum = Math.max(-INTEGRAL_LIMIT, Math.min(INTEGRAL_LIMIT, integralSum));

        double derivative = (error - lastError) / dt;
        double output = (kP * error) + (kI * integralSum) + (kD * derivative) + kF;

        output = Math.max(-1.0, Math.min(1.0, output));
        ArmIntakeMotor.setPower(output);

        lastError = error;
        timer.reset();
    }

    public int getPosition() {
        return ArmIntakeMotor.getCurrentPosition();
    }

    public int getTarget() {
        return targetPosition;
    }
}