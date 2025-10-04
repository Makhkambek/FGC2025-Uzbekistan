package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Clutch extends SubsystemBase {
    private Servo clutchServo_left;
    private Servo clutchServo_right;

    private static final double SERVO_OPEN = 1.0;
    private static final double SERVO_CLOSED = 0.0;

    public Clutch(HardwareMap hardwareMap) {
        clutchServo_left = hardwareMap.get(Servo.class, "clutchServo_left");
        clutchServo_right = hardwareMap.get(Servo.class, "clutchServo_right");

        close();
    }

    public void open() {
        clutchServo_left.setPosition(SERVO_OPEN);
        clutchServo_right.setPosition(SERVO_OPEN);
    }

    public void close() {
        clutchServo_left.setPosition(SERVO_CLOSED);
        clutchServo_right.setPosition(SERVO_CLOSED);
    }

    public void stop() {
        close();
    }
}