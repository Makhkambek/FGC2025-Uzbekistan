package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp
//@Disabled
public class test extends OpMode {

//    private DcMotorEx leftLift;
//    private DcMotorEx rightLift;
//    private DcMotorEx middleLift;
    private Servo servo;
//    private Servo servo_1;


    @Override
    public void init() {

//        intake = hardwareMap.get(DcMotorEx.class, "intake_motor");
//        intake.setDirection(DcMotorEx.Direction.REVERSE);
//
//        leftLift = hardwareMap.get(DcMotorEx.class, "hang_1");
//        rightLift = hardwareMap.get(DcMotorEx.class, "hang_2");
//        middleLift = hardwareMap.get(DcMotorEx.class, "middleLift");
//
//        middleLift.setDirection(DcMotorEx.Direction.REVERSE);
//
//        leftLift.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//        middleLift.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//        rightLift.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

//        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
//        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
//        intake = hardwareMap.get(DcMotorEx.class, "intake");

        servo = hardwareMap.get(Servo.class, "servo");
//        servo_1 = hardwareMap.get(Servo.class, "servo_1");
//        servo.setDirection(Servo.Direction.REVERSE);
//        servo_1.setDirection(Servo.Direction.REVERSE);



    }
    @Override
    public void loop() {


        if(gamepad1.dpad_up) {
            servo.setPosition(0);
//            servo_1.setPosition(1.0);
//            leftLift.setPower(1);
//            rightLift.setPower(1);
//            leftLift.setPower(1);
        }else if(gamepad1.dpad_down) {
            servo.setPosition(1.0);
//            servo_1.setPosition(0);
//            leftLift.setPower(-1);
//            rightLift.setPower(-1);
//            leftLift.setPower(0);
        } else {
            servo.setPosition(0.5);
//            leftLift.setPower(0);
//            rightLift.setPower(0);
        }

    }
}