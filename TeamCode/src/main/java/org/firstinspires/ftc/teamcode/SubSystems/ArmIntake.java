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
    private static final double kF = 0.02;
    private static final double INTEGRAL_LIMIT = 50.0;

    private static final double GRIPPER_ON = 1.0;
    private static final double GRIPPER_OFF = 0.0;
    private static final double GRIPPER_RETRACT = -1.0;

    private double integralSum = 0;
    private double lastError = 0;
    private int targetPosition;
    private int doorStartPosition;
    private final ElapsedTime timer = new ElapsedTime();
    private boolean isResetting = false;
    private int resetState = 0;
    private final ElapsedTime resetTimer = new ElapsedTime();

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
        isResetting = true;
        resetState = 0;
        resetTimer.reset();
    }

    private void executeReset() {
        switch (resetState) {
            case 0:
                ArmIntakeMotor.setPower(0.0);
                ArmIntakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
                resetState++;
                break;
            case 1:
                if (resetTimer.seconds() >= 1.0) {
                    ArmIntakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    ArmIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    ArmIntakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
                    targetPosition = ZERO;
                    integralSum = 0.0;
                    lastError = 0.0;
                    timer.reset();
                    isResetting = false;
                    resetState = 0;
                }
                break;
        }
    }

    @Override
    public void periodic() {
        if (isResetting) {
            executeReset();
        } else {
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
    }

    public int getPosition() {
        return ArmIntakeMotor.getCurrentPosition();
    }

    public int getTarget() {
        return targetPosition;
    }
}