package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import java.util.List;
import java.util.Objects;

@Autonomous
public class NewTestAuto extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "14624Props.tflite";
    private static final String[] LABELS = {
            "blueJag",
            "redJag",
    };
    private TfodProcessor tfod;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain dt = new Drivetrain();
        initTfod();
        dt.initializeMotors(hardwareMap);
        dt.update(0);
        boolean color = true;
        int spikeMark = 2;
        boolean plus5 = true;
        boolean bool = false;
        boolean done = false;

        waitForStart();
        List<Recognition> currentRecognitions = tfod.getRecognitions();

        for (Recognition recognition : currentRecognitions) {
            if (Objects.equals(recognition.getLabel(), "blueJag")) {
                dt.spikeMark(color, spikeMark, plus5);
                dt.update(1f);
                done = true;
                break;
            } else if (Objects.equals(recognition.getLabel(), "redJag")) {
                color = false;
                dt.spikeMark(color, spikeMark, plus5);
                dt.update(1f);
                done = true;
                break;
            } else {
                spikeMark = 1;
                break;
            }

        }
        spikeMark = 1;
        dt.update(-0.3f, 0);
        sleep(300);
        dt.update(0f, 0);
        sleep(3000);
        currentRecognitions = tfod.getRecognitions();
        if (!done) {
            for (Recognition recognition : currentRecognitions) {
                if (Objects.equals(recognition.getLabel(), "blueJag")) {
                    dt.spikeMark(color, spikeMark, plus5);
                    dt.update(1f);
                    done = true;
                    break;
                } else if (Objects.equals(recognition.getLabel(), "redJag")) {
                    color = false;
                    dt.spikeMark(color, spikeMark, plus5);
                    dt.update(1f);
                    done = true;
                    break;
                } else {
                    spikeMark = 3;
                    break;
                }

            }
        }
        spikeMark = 3;
        dt.update(0.3f, 0);
        sleep(600);
        dt.update(0f, 0);
        sleep(3000);
        currentRecognitions = tfod.getRecognitions();
        if (!done) {

            for (Recognition recognition : currentRecognitions) {
                if (Objects.equals(recognition.getLabel(), "blueJag")) {
                    dt.spikeMark(color, spikeMark, plus5);
                    dt.update(1f);
                    done = true;
                    break;
                } else if (Objects.equals(recognition.getLabel(), "redJag")) {
                    color = false;
                    dt.spikeMark(color, spikeMark, plus5);
                    dt.update(1f);
                    done = true;
                    break;
                } else {
                    spikeMark = 3;
                    break;
                }

            }
        }
    }
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .build();
        // Set and enable the processor.

        // Build the Vision Portal, using the above settings.
        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tfod)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .build();
        tfod.setMinResultConfidence(0.60f);
    }
}
