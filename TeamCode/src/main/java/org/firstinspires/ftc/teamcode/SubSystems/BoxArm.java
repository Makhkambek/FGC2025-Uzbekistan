package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BoxArm extends SubsystemBase {
    private Servo boxServo_left;
    private Servo boxServo_right;

    private static final double SERVO_OPEN = 1.0;
    private static final double SERVO_CLOSED = 0.0;

    public BoxArm(HardwareMap hardwareMap) {
        boxServo_left = hardwareMap.get(Servo.class, "boxServo_left");
        boxServo_right = hardwareMap.get(Servo.class, "boxServo_right");

        closeBox(); // Закрываем коробку при инициализации
    }

    public void openBox() {
        boxServo_left.setPosition(SERVO_OPEN);
        boxServo_right.setPosition(SERVO_OPEN);
    }

    public void closeBox() {
        boxServo_left.setPosition(SERVO_CLOSED);
        boxServo_right.setPosition(SERVO_CLOSED);
    }

    public void stop() {
        closeBox(); // Закрываем коробку
    }
}