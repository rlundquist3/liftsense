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
    string intToString(int number);

    void drawObject(vector<Object> theObjects, Mat &frame, Mat &temp, vector<vector<Point> > contours, vector<Vec4i> hierarchy);

    void morphOps(Mat &thresh);

    void trackFilteredObject(Object theObject,Mat threshold,Mat HSV, Mat &cameraFeed);

    jfloat detect(jlong imageRgba);
    jfloat detect(jlong imageRgba, jint h1, jint h2, jint s1, jint s2, jint v1, jint v2);

private:
    //default capture width and height
    const int FRAME_WIDTH = 640;
    const int FRAME_HEIGHT = 480;
    //max number of objects to be detected in frame
    const int MAX_NUM_OBJECTS=50;
    //minimum and maximum object area
    const int MIN_OBJECT_AREA = 100*100;
    const int MAX_OBJECT_AREA = FRAME_HEIGHT*FRAME_WIDTH/1.5;

    float totalWeight = 0;
};


#endif //LIFTSENSE_MULTIPLEOBJECTTRACKING_H
