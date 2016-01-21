//
// Created by Riley Lundquist on 12/8/15.
//

#ifndef LIFTSENSE_MULTIPLEOBJECTTRACKING_H
#define LIFTSENSE_MULTIPLEOBJECTTRACKING_H

#include <string>
//#include <cv.h>
#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/objdetect.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <sstream>
#include <iostream>
#include <vector>

#include "Object.h"

using namespace std;
using namespace cv;

class MultipleObjectTracking {
public:
//    MultipleObjectTracking();
//    ~MultipleObjectTracking();

    string intToString(int number);

    void drawObject(vector<Object> theObjects, Mat &frame, Mat &temp, vector<vector<Point> > contours, vector<Vec4i> hierarchy);
    void drawObject(vector<Object> theObjects, Mat &frame);

    void morphOps(Mat &thresh);

    void trackFilteredObject(Mat threshold,Mat HSV, Mat &cameraFeed);
    void trackFilteredObject(Object theObject,Mat threshold,Mat HSV, Mat &cameraFeed);

    void detect(jlong imageRgba);

private:
    int H_MIN = 0;
    int H_MAX = 256;
    int S_MIN = 0;
    int S_MAX = 256;
    int V_MIN = 0;
    int V_MAX = 256;
    //default capture width and height
    const int FRAME_WIDTH = 640;
    const int FRAME_HEIGHT = 480;
    //max number of objects to be detected in frame
    const int MAX_NUM_OBJECTS=50;
    //minimum and maximum object area
    const int MIN_OBJECT_AREA = 20*20;
    const int MAX_OBJECT_AREA = FRAME_HEIGHT*FRAME_WIDTH/1.5;
    //names that will appear at the top of each window
    const string windowName = "Original Image";
    const string windowName1 = "HSV Image";
    const string windowName2 = "Thresholded Image";
    const string windowName3 = "After Morphological Operations";
    const string trackbarWindowName = "Trackbars";

    //The following for canny edge detec
    Mat dst, detected_edges;
    Mat src, src_gray;
    int edgeThresh = 1;
    int lowThreshold;
    int const max_lowThreshold = 100;
    int ratio = 3;
    int kernel_size = 3;
    char* window_name = "Edge Map";
};


#endif //LIFTSENSE_MULTIPLEOBJECTTRACKING_H
