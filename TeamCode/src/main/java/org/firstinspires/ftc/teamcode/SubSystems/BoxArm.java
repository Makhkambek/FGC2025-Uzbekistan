package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class BoxArm extends SubsystemBase {
    private Servo boxServo_left;
    private Servo boxServo_right;
    private Clutch clutch;

    private static final double SERVO_OPEN = 0.0;
    private static final double SERVO_CLOSED = 1.0;

    private int openSubState = 0;
    private int closeSubState = 0;
    private ElapsedTime timer = new ElapsedTime();
    private boolean isOpening = false;
    private boolean isClosing = false;

    public BoxArm(HardwareMap hardwareMap, Clutch clutch) {
        this.clutch = clutch;
        boxServo_left = hardwareMap.get(Servo.class, "boxServo_left");
        boxServo_right = hardwareMap.get(Servo.class, "boxServo_right");

        boxServo_right.setDirection(Servo.Direction.REVERSE);

        closeBox();
    }

    public void startOpenBox() {
        if (!isOpening && !isClosing) {
            openSubState = 0;
            timer.reset();
            isOpening = true;
        }
    }

    private void executeOpenBox() {
        switch (openSubState) {
            case 0:
                clutch.intermediate();
                boxServo_left.setPosition(SERVO_OPEN);
                boxServo_right.setPosition(SERVO_OPEN);
                timer.reset();
                openSubState++;
                break;

            case 1:
                if (timer.seconds() > 1.0) {
                    clutch.close();
                    isOpening = false;
                    openSubState = 0;
                }
                break;
        }
    }

    public void startCloseBox() {
        if (!isOpening && !isClosing) {
            closeSubState = 0;
            timer.reset();
            isClosing = true;
        }
    }

    private void executeCloseBox() {
        switch (closeSubState) {
            case 0:
                clutch.intermediate();
                boxServo_left.setPosition(SERVO_CLOSED);
                boxServo_right.setPosition(SERVO_CLOSED);
                timer.reset();
                closeSubState++;
                break;

            case 1:
                if (timer.seconds() > 0.5) {
                    clutch.close();
                    isClosing = false;
                    closeSubState = 0;
                }
                break;
        }
    }

    @Override
    public void periodic() {
        if (isOpening) {
            executeOpenBox();
        }
        if (isClosing) {
            executeCloseBox();
        }
    }

    public void openBox() {
        startOpenBox();
    }

    public void closeBox() {
        startCloseBox();
    }

    public void stop() {
        closeBox();
        isOpening = false;
        isClosing = false;
        openSubState = 0;
        closeSubState = 0;
    }
}