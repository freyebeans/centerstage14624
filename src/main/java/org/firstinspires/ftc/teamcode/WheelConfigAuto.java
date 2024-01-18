package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "WheelConfigAuto", group = "Autonomous")
public class WheelConfigAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        Drivetrain dt = new Drivetrain(hardwareMap);
        waitForStart();
        resetRuntime();

        while (opModeIsActive()) {
            dt.resetLift();
        }
    }
}
