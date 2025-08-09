package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArmIntake extends SubsystemBase {
    private Servo servo1;
    private Servo servo2;
    private CRServo servo3;
    private CRServo servo4;

    public enum ArmState {
        UP,
        DOWN,
        IDLE
    }

    private static final double ARM_UP_POSITION = 1.0;
    private static final double ARM_DOWN_POSITION = 0.0;
    private static final double GRIPPER_OPEN_POSITION = 1.0;
    private static final double GRIPPER_CLOSED_POSITION = 0.0;
    private static final double IDLE_POSITION = 0.0;
    private static final double UP_GRIPPER_DELAY_SECONDS = 2.0;
    private static final double DOWN_ARM_DELAY_SECONDS = 0.5;

    private final ElapsedTime timer = new ElapsedTime();
    private int subState = 0;
    private ArmState currentState = ArmState.IDLE;

    public ArmIntake(HardwareMap hardwareMap) {
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo3 = hardwareMap.get(CRServo.class, "servo3");
        servo4 = hardwareMap.get(CRServo.class, "servo4");

        stop();
    }

    public void setArmState(ArmState state) {
        if (state != currentState) {
            currentState = state;
            subState = 0;
        }

        switch (currentState) {
            case UP:
                switch (subState) {
                    case 0:
                        servo1.setPosition(ARM_UP_POSITION);
                        servo2.setPosition(ARM_UP_POSITION);
                        timer.reset();
                        subState++;
                        break;
                    case 1:
                        if (timer.seconds() > UP_GRIPPER_DELAY_SECONDS) {
                            servo3.setPower(1.0);
                            servo4.setPower(1.0);
                            currentState = ArmState.IDLE;
                            subState = 0;
                        }
                        break;
                }
                break;
            case DOWN:
                switch (subState) {
                    case 0:
                        servo3.setPower(0.0);
                        servo4.setPower(0.0);
                        timer.reset();
                        subState++;
                        break;
                    case 1:
                        if (timer.seconds() > DOWN_ARM_DELAY_SECONDS) {
                            servo1.setPosition(ARM_DOWN_POSITION);
                            servo2.setPosition(ARM_DOWN_POSITION);
                            currentState = ArmState.IDLE;
                            subState = 0;
                        }
                        break;
                }
                break;
            case IDLE:
                stop();
                break;
        }
    }

    public void stop() {
        servo1.setPosition(IDLE_POSITION);
        servo2.setPosition(IDLE_POSITION);
        servo3.setPower(0.0);
        servo4.setPower(0.0);
        currentState = ArmState.IDLE;
        subState = 0;
    }

    public ArmState getCurrentState() {
        return currentState;
    }

    public int getSubState() {
        return subState;
    }

    @Override
    public void periodic() {
        setArmState(currentState);
    }
}