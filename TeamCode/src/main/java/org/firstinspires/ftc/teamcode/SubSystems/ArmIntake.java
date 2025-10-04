package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArmIntake extends SubsystemBase {
    private CRServo servo3;
    private CRServo servo4;
    private DcMotorEx ArmIntakeMotor;

    public static final int TARGET_DEFAULT = 100;

    private int target = TARGET_DEFAULT;

    private double integralSum = 0.0;
    private double lastError = 0.0;

    private static final double INTEGRAL_LIMIT = 50.0;
    public static final double kP = 0.02;
    public static final double kI = 0.006;
    public static final double kD = 0.001;
    public static final double kF = 0.1;

    private static final double GRIPPER_ON = 1.0;
    private static final double GRIPPER_OFF = 0.0;

    private final ElapsedTime timer = new ElapsedTime();

    public ArmIntake(HardwareMap hardwareMap) {
        servo3 = hardwareMap.get(CRServo.class, "servo3");
        servo4 = hardwareMap.get(CRServo.class, "servo4");
        ArmIntakeMotor = hardwareMap.get(DcMotorEx.class, "ArmIntakeMotor");

        ArmIntakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetEncoders();
    }

    private void resetEncoders() {
        ArmIntakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        target = TARGET_DEFAULT;
        integralSum = 0.0;
        lastError = 0.0;
        timer.reset();
    }

    public void setTarget(int targetPosition) {
        target = targetPosition;
        integralSum = 0.0;
    }

    public void runGrippers() {
        servo3.setPower(GRIPPER_ON);
        servo4.setPower(GRIPPER_ON);
    }

    public void offGrippers() {
        servo3.setPower(GRIPPER_OFF);
        servo4.setPower(GRIPPER_OFF);
    }

    public void stop() {
        offGrippers();
        ArmIntakeMotor.setPower(0.0);
        resetEncoders();
    }

    @Override
    public void periodic() {
        double deltaTime = timer.seconds();
        if (deltaTime < 0.001) deltaTime = 0.001;

        double position = ArmIntakeMotor.getCurrentPosition();
        double error = target - position;
        integralSum = clampIntegral(integralSum + error * deltaTime);
        double derivative = (error - lastError) / deltaTime;
        double output = (kP * error) + (kI * integralSum) + (kD * derivative) + kF;
        ArmIntakeMotor.setPower(clampPower(output));

        lastError = error;
        timer.reset();
    }

    private double clampPower(double power) {
        return Math.max(-1.0, Math.min(1.0, power));
    }

    private double clampIntegral(double integral) {
        return Math.max(-INTEGRAL_LIMIT, Math.min(INTEGRAL_LIMIT, integral));
    }

    public int getPosition() {
        return ArmIntakeMotor.getCurrentPosition();
    }

    public int getTarget() {
        return target;
    }
}