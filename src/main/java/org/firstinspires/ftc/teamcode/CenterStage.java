package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Math.*;

@TeleOp(name = "CenterStage", group = "Linear Opmode")
public class CenterStage extends LinearOpMode {

    Servo servo;
    @Override
    public void runOpMode() {

        Drivetrain dt = new Drivetrain(hardwareMap);
        servo = hardwareMap.get(Servo.class, "servo");
        waitForStart();
        resetRuntime();

        while (opModeIsActive()) {

            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double theta = atan2(y,x);
            double power = hypot(x,y);
            dt.update(theta,power,turn);
            dt.updateLift(gamepad2.b, gamepad2.a, gamepad2.left_stick_y, gamepad2.x);
            dt.updateServo(gamepad2.left_bumper,gamepad2.right_bumper);
            //i9f(gamepad1.y)dt.pew.setPosition(1);
            if (gamepad1.a){
                servo.setPosition(1);
            } else {
                servo.setPosition(0);
            }
            telemetry.addData("Arm Pos",dt.topLift.getCurrentPosition());
            telemetry.addData("Left Pos",dt.leftLift.getCurrentPosition());
            telemetry.addData("Right Pos",dt.rightLift.getCurrentPosition());
            telemetry.update();
        }
    }



}
