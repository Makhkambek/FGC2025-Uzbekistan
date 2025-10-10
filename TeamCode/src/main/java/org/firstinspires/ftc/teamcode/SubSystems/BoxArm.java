package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BoxArm extends SubsystemBase {
    private Servo boxServo_left;
    private Servo boxServo_right;
    private Clutch clutch;
    private Telemetry telemetry; // Added for debugging

    private static final double SERVO_OPEN = 0.0;
    private static final double SERVO_CLOSED = 1.0;

    private int openSubState = 0;
    private int closeSubState = 0;
    private ElapsedTime timer = new ElapsedTime();
    private boolean isOpening = false;
    private boolean isClosing = false;

    public BoxArm(HardwareMap hardwareMap, Clutch clutch, Telemetry telemetry) {
        this.clutch = clutch;
        this.telemetry = telemetry; // Initialize telemetry
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
            telemetry.addData("BoxArm", "Starting openBox");
            telemetry.update();
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
                telemetry.addData("BoxArm Open", "State 0, Servo Left: %.2f, Servo Right: %.2f",
                        boxServo_left.getPosition(), boxServo_right.getPosition());
                telemetry.update();
                break;

            case 1:
                if (timer.seconds() > 1.0) {
                    clutch.close();
                    isOpening = false;
                    openSubState = 0;
                    telemetry.addData("BoxArm Open", "State 1 Complete, Clutch Closed");
                    telemetry.update();
                }
                break;
        }
    }

    public void startCloseBox() {
        if (!isOpening && !isClosing) {
            closeSubState = 0;
            timer.reset();
            isClosing = true;
            telemetry.addData("BoxArm", "Starting closeBox");
            telemetry.update();
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
                telemetry.addData("BoxArm Close", "State 0, Servo Left: %.2f, Servo Right: %.2f",
                        boxServo_left.getPosition(), boxServo_right.getPosition());
                telemetry.update();
                break;

            case 1:
                if (timer.seconds() > 0.5) {
                    clutch.close();
                    isClosing = false;
                    closeSubState = 0;
                    telemetry.addData("BoxArm Close", "State 1 Complete, Clutch Closed");
                    telemetry.update();
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
        closeBox(); // Start the closing process
        isOpening = false; // Prevent opening
        openSubState = 0; // Reset open state
        // Do not reset isClosing or closeSubState; let executeCloseBox() handle it
        telemetry.addData("BoxArm Stop", "Called, isClosing: %b, closeSubState: %d",
                isClosing, closeSubState);
        telemetry.addData("Servo Positions", "Left: %.2f, Right: %.2f",
                boxServo_left.getPosition(), boxServo_right.getPosition());
        telemetry.update();
    }
}