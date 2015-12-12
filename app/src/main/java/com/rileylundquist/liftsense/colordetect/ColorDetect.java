package com.rileylundquist.liftsense.colordetect;

import android.graphics.Color;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

/**
 * Created by rlundquist3 on 12/11/15.
 */
public class ColorDetect {

    private int hMin, sMin, vMin = 0, hMax, sMax, vMax = 256;

    private final int FRAME_WIDTH = 640;
    private final int FRAME_HEIGHT = 480;
    private final int MAX_NUM_OBJECTS=50;
    private final int MIN_OBJECT_AREA = 20*20;
    private final double MAX_OBJECT_AREA = FRAME_HEIGHT*FRAME_WIDTH/1.5;

    private Mat dst, detected_edges;
    private Mat src, src_gray;
    private int edgeThresh = 1;
    private int lowThreshold;
    private final int MAX_LOW_THRESHOLD = 100;
    private int ratio = 3;
    private int kernel_size = 3;

    public ColorDetect() {

    }

    private void drawObject(ArrayList<ColorObject> objects, Mat frame, Mat temp, ArrayList<MatOfPoint> contours, Mat hierarchy) {
        for (int i = 0; i < objects.size(); i++) {
            Imgproc.drawContours(frame, contours, i, objects.get(i).getColor(), 3);
            Imgproc.circle(frame, new Point(objects.get(i).getxPos(), objects.get(i).getyPos()), 5, objects.get(i).getColor());
        }
    }

    private void drawObject(ArrayList<ColorObject> objects, Mat frame) {
        for (int i = 0; i < objects.size(); i++) {
            Imgproc.circle(frame, new Point(objects.get(i).getxPos(), objects.get(i).getyPos()), 10, new Scalar(0, 0, 255));
        }
    }

    private void morphOps(Mat threshold) {
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));

        Imgproc.erode(threshold, threshold, erodeElement);
        Imgproc.erode(threshold, threshold, erodeElement);

        Imgproc.dilate(threshold, threshold, dilateElement);
        Imgproc.dilate(threshold, threshold, dilateElement);
    }

    private void trackObject(Mat threshold, Mat hsv, Mat cameraFeed) {
        ArrayList<ColorObject> objects;
        Mat temp = new Mat();
        threshold.copyTo(temp);

        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(temp, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        double refArea = 0;
        boolean objectFound = false;

        //Figure out how to do Moments
    }

    private void trackObject(ColorObject object, Mat threshold, Mat hsv, Mat cameraFeed) {
        ArrayList<ColorObject> objects;
        Mat temp = new Mat();
        threshold.copyTo(temp);

        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(temp, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        double refArea = 0;
        boolean objectFound = false;

        //Figure out how to do Moments
    }

    public Mat detect(Mat cameraFeed) {
        Mat hsv = new Mat();
        Mat threshold = new Mat();

        Imgproc.cvtColor(cameraFeed, hsv, Imgproc.COLOR_BGR2HSV);

        ColorObject blue = new ColorObject("blue");
        Imgproc.cvtColor(cameraFeed, hsv, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsv, blue.getHsvMin(), blue.getHsvMax(), threshold);
        morphOps(threshold);
        trackObject(blue, threshold, hsv, cameraFeed);

        ColorObject green = new ColorObject("green");
        Imgproc.cvtColor(cameraFeed, hsv, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsv, green.getHsvMin(), green.getHsvMax(), threshold);
        morphOps(threshold);
        trackObject(green, threshold, hsv, cameraFeed);

        ColorObject yellow = new ColorObject("yellow");
        Imgproc.cvtColor(cameraFeed, hsv, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsv, yellow.getHsvMin(), yellow.getHsvMax(), threshold);
        morphOps(threshold);
        trackObject(yellow, threshold, hsv, cameraFeed);

        ColorObject red = new ColorObject("red");
        Imgproc.cvtColor(cameraFeed, hsv, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsv, red.getHsvMin(), red.getHsvMax(), threshold);
        morphOps(threshold);
        trackObject(red, threshold, hsv, cameraFeed);

        return cameraFeed;
    }
}
