package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Math.*;
import static org.firstinspires.ftc.teamcode.UtilMeth.*;

public class Drivetrain {

    public static final int MIN_POS = 0;
    public static final int MAX_POS = -7075;
    public final DcMotor topLeft;
    public final DcMotor topRight;
    public final DcMotor bottomLeft;
    public final DcMotor bottomRight;
    public final DcMotor leftLift;
    public final DcMotor rightLift;
    public final DcMotor topLift;
    private final DcMotor[] motors;

    public final Servo pew;

    double LF; double RF; double LR; double RR; double LLF; double RLF; double TLF;

    double X1; double Y1; double X2; double Y2;
    CRServo servo;
    CRServo fservo;

    double joyScale = 0.8;
    double motorMax = 2.0;

    private float lift;

    public Drivetrain(HardwareMap map) {
        lift = 0;
        //help me help me help me help me help me help me
        topLeft = map.get(DcMotor.class, "TL");
        topRight = map.get(DcMotor.class, "TR");
        bottomLeft = map.get(DcMotor.class, "BL");
        bottomRight = map.get(DcMotor.class, "BR");
        leftLift = map.get(DcMotor.class, "LLift");
        rightLift = map.get(DcMotor.class, "RLift");
        topLift = map.get(DcMotor.class, "TLift");
        servo = map.get(CRServo.class, "LServo");
        fservo = map.get(CRServo.class, "RServo");
        motors = new DcMotor[] {topLeft,topRight,bottomLeft,bottomRight,leftLift,rightLift,topLift};
        pew = map.get(Servo.class,"pew");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        topRight.setDirection(DcMotor.Direction.FORWARD);
        bottomLeft.setDirection(DcMotor.Direction.REVERSE);
        bottomRight.setDirection(DcMotor.Direction.FORWARD);
        leftLift.setDirection(DcMotor.Direction.REVERSE);
        rightLift.setDirection(DcMotor.Direction.REVERSE);
        topLift.setDirection(DcMotor.Direction.REVERSE);
        servo.setDirection(DcMotorSimple.Direction.FORWARD);
        for (DcMotor motor: motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    @Deprecated
    public void update(double X1I, double Y1I, double X2I, double Y2I) {
        LF = 0; RF = 0; LR = 0; RR = 0; //Prevents any random movment

        X1 = X1I * joyScale; Y1 = Y1I * joyScale; X2 = X2I * joyScale; Y2 = Y2I * joyScale; //Give variables joystick input

        LF += Y1; RF += Y1; LR += Y1; RR += Y1; //Forward and back

        LF += X1; RF -= X1; LR -= X1; RR += X1; //Angled

        LF += X2; RF -= Y1; LR += X2; RR -= X2; //Turning

        LF = Math.max(-motorMax, Math.min(LF, motorMax));
        RF = Math.max(-motorMax, Math.min(RF, motorMax));
        LR = Math.max(-motorMax, Math.min(LR, motorMax));
        RR = Math.max(-motorMax, Math.min(RR, motorMax));
        //Set powers
        update();
    }


    public void update(double theta, double power, double turn) {
        double msin = sin(theta - PI/4);
        double mcos = cos(theta - PI/4);
        double mmax = max(abs(msin),abs(mcos));
        LF = power*mcos/mmax+turn;
        RF = power*msin/mmax-turn;
        LR = power*msin/mmax+turn;
        RR = power*mcos/mmax-turn;
        if((power+abs(turn))>1) {
            double a=power+turn;
            LF/=a;
            RF/=a;
            LR/=a;
            RR/=a;
        }
        update();
    }

    public void update() {
        topLeft.setPower(LF);
        topRight.setPower(RF);
        bottomLeft.setPower(LR);
        bottomRight.setPower(RR);
    }

    public void updatePrecise(double a,double b,double c, double d) {
        LF = a;
        RF = b;
        LR = c;
        RR = d;
    }

    public void updateLift(boolean a,boolean b, float r, boolean x) {
        if (a&&b||!a&&!b) {
            TLF = 0;
        } else {
            if (a) {
                TLF = 1;
                lift += 0.005;
            } else {
                TLF = -1;
                lift -= 0.005;
            }
        }
        lift = UtilMeth.clamp(lift,0,1);
        LLF=-r/3f;
        RLF=r/3f;


        //topLift.setTargetPosition(0);
        topLift.setTargetPosition((int)(lift*MAX_POS+(1-lift)*MIN_POS));
        topLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int distance = topLift.getCurrentPosition()-topLift.getTargetPosition();
        topLift.setPower(abs(distance)>7 ?1:0);
        if(x){
            rightLift.setTargetPosition(-186);
            leftLift.setTargetPosition(186);
            rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            int distancer = rightLift.getCurrentPosition()-rightLift.getTargetPosition();
            int distancel = leftLift.getCurrentPosition()-leftLift.getTargetPosition();
            leftLift.setPower(
                    abs(distancel)>7 ?
                            1 :
                            abs(distancel)>3 ?
                                    0.5 :
                                    0
            );
            rightLift.setPower(
                    abs(distancer)>7 ?
                            1 :
                            abs(distancer)>3 ?
                                    0.5 :
                                    0
            );
        } else {
            rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftLift.setPower(LLF);
            rightLift.setPower(RLF);
        }
    }

    public void updateServo(boolean a,boolean b) {
        float c = 0;
        if(a) c = b?2:-2;
        servo.setPower(c);
        fservo.setPower(-c);
    }

    public void resetLift() {
        topLift.setTargetPosition(-MAX_POS);
        topLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int distance = topLift.getCurrentPosition()-topLift.getTargetPosition();
        topLift.setPower(abs(distance)>7 ?0.5:0);
    }
}


