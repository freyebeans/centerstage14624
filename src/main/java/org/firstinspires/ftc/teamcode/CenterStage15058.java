package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "CenterStage15058", group = "Linear Opmode")
public class CenterStage15058 extends LinearOpMode {

    double X1I; double Y1I; double X2I; double Y2I;

    //Initialize the drivetrain
    Drivetrain dt;

    @Override
    public void runOpMode() {
        //Create an object for the drivetrain
        dt = new Drivetrain(hardwareMap);

        waitForStart();
        resetRuntime();

        while (opModeIsActive()) {
            //Send commands from Gamepad to Drivetrain class
            X1I = -gamepad1.right_stick_x;
            Y1I = gamepad1.right_stick_y;
            X2I = -gamepad1.left_stick_x;
            Y2I = gamepad1.left_stick_y;

            //Update all necessary resources
            dt.update(X1I, Y1I, X2I, Y2I);
        }
    }
}
