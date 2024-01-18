package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;




@TeleOp(name="MovementTestOP", group="Linear Opmode")

public class MovementTestOP extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor topLeft = null;
    private DcMotor topRight = null;
    private DcMotor bottomLeft = null;
    private DcMotor bottomRight = null;
    
    double TL; double TR; double BL; double BR;
    
    double X1; double Y1; double X2; double Y2;
    
    double joyScale = 0.8;
    double motorMax = 1.0;
    
    @Override
    public void runOpMode(){
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        topLeft = hardwareMap.get(DcMotor.class, "TL");
        topRight = hardwareMap.get(DcMotor.class, "TR");
        bottomLeft = hardwareMap.get(DcMotor.class, "BL");
        bottomRight = hardwareMap.get(DcMotor.class, "BR");
        
        topLeft.setDirection(DcMotor.Direction.FORWARD);
        topRight.setDirection(DcMotor.Direction.FORWARD);
        bottomLeft.setDirection(DcMotor.Direction.FORWARD);
        bottomRight.setDirection(DcMotor.Direction.FORWARD);
        
        topLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        waitForStart();
        resetRuntime();
        
        while (opModeIsActive()){
            TL = 0; TR = 0; BL = 0; BR = 0;
            
            X1 = -gamepad1.right_stick_x * joyScale;
            Y1 = gamepad1.right_stick_y * joyScale;
            X2 = -gamepad1.left_stick_x * joyScale;
            Y2 = gamepad1.left_stick_y * joyScale;
            
            TL += Y1; TR += Y1; BL += Y1; BR += Y1;
            TL += X1; TR -= X1; BL -= X1; BR += X1; 
            TL += X2; TR -= Y1; BL += X2; BR -= X2; 
            
            TL = Math.max(-motorMax, Math.min(TL, motorMax));
            TR = Math.max(-motorMax, Math.min(TR, motorMax));
            BL = Math.max(-motorMax, Math.min(BL, motorMax));
            BR = Math.max(-motorMax, Math.min(BR, motorMax));
            
            topLeft.setPower(TL);
            topRight.setPower(TR);
            bottomLeft.setPower(BL);
            bottomRight.setPower(BR);
            
        }
    }
    
}
