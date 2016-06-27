//Written by  Kyle Hounslow 2013

// modified by: Ahmad Kaifi, Hassan Althobaiti

//Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software")
//, to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
//and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
//The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//IN THE SOFTWARE.

#include "MultipleObjectTracking.h"

string MultipleObjectTracking::intToString(int number) {

    std::stringstream ss;
    ss << number;
    return ss.str();
}

void MultipleObjectTracking::drawObject(vector<Object> theObjects,Mat &frame, Mat &temp, vector<vector<Point> > contours, vector<Vec4i> hierarchy) {

	for(int i =0; i<theObjects.size(); i++) {
		cv::drawContours(frame,contours,i,theObjects.at(i).getColor(),3,8,hierarchy);
		cv::circle(frame,cv::Point(theObjects.at(i).getXPos(),theObjects.at(i).getYPos()),5,theObjects.at(i).getColor());
		cv::putText(frame,intToString(theObjects.at(i).getXPos())+ " , " + intToString(theObjects.at(i).getYPos()),cv::Point(theObjects.at(i).getXPos(),theObjects.at(i).getYPos()+20),1,1,theObjects.at(i).getColor());
		cv::putText(frame,theObjects.at(i).getType(),cv::Point(theObjects.at(i).getXPos(),theObjects.at(i).getYPos()-20),1,2,theObjects.at(i).getColor());

		if (theObjects.at(i).getType() == "green")
			totalWeight += 25;
		else if (theObjects.at(i).getType() == "yellow")
			totalWeight += 35;
		else if (theObjects.at(i).getType() == "blue")
			totalWeight += 45;
		else if (theObjects.at(i).getType() == "red")
			totalWeight += 55;
	}
}

void MultipleObjectTracking::morphOps(Mat &thresh) {

	//create structuring element that will be used to "dilate" and "erode" image.
	//the element chosen here is a 3px by 3px rectangle
	Mat erodeElement = getStructuringElement( MORPH_RECT,Size(3,3));
	//dilate with larger element so make sure object is nicely visible
	Mat dilateElement = getStructuringElement( MORPH_RECT,Size(8,8));

	erode(thresh,thresh,erodeElement);
	erode(thresh,thresh,erodeElement);

	dilate(thresh,thresh,dilateElement);
	dilate(thresh,thresh,dilateElement);
}

void MultipleObjectTracking::trackFilteredObject(Object theObject,Mat threshold,Mat HSV, Mat &cameraFeed) {

	vector <Object> objects;
	Mat temp;
	threshold.copyTo(temp);
	//these two vectors needed for output of findContours
	vector< vector<Point> > contours;
	vector<Vec4i> hierarchy;
	//find contours of filtered image using openCV findContours function
	findContours(temp, contours, hierarchy, CV_RETR_CCOMP, CV_CHAIN_APPROX_SIMPLE );
	//use moments method to find our filtered object
	double refArea = 0;
	bool objectFound = false;
	if (hierarchy.size() > 0) {
		int numObjects = hierarchy.size();
		//if number of objects greater than MAX_NUM_OBJECTS we have a noisy filter
		if(numObjects<MAX_NUM_OBJECTS) {
			for (int index = 0; index >= 0; index = hierarchy[index][0]) {

				Moments moment = moments((cv::Mat)contours[index]);
				double area = moment.m00;

		//if the area is less than 20 px by 20px then it is probably just noise
		//if the area is the same as the 3/2 of the image size, probably just a bad filter
		//we only want the object with the largest area so we safe a reference area each
				//iteration and compare it to the area in the next iteration.
				if(area>MIN_OBJECT_AREA){

					Object object;

					object.setXPos(moment.m10/area);
					object.setYPos(moment.m01/area);
					object.setType(theObject.getType());
					object.setColor(theObject.getColor());

					objects.push_back(object);

					objectFound = true;

				}else objectFound = false;
			}
			//let user know you found an object
			if(objectFound ==true){
				//draw object location on screen
				drawObject(objects,cameraFeed,temp,contours,hierarchy);}

		} else
			putText(cameraFeed,"TOO MUCH NOISE! ADJUST FILTER",Point(0,50),1,2,Scalar(0,0,255),2);
	}
}

/**
 * Formerly main--changed to be function called by main.cpp
 */
//int main(int argc, char* argv[])
//jfloat MultipleObjectTracking::detect(jlong imageRgba) {
//	bool calibrationMode = false;
//
//	//Matrix to store each frame of the webcam feed
//	Mat& cameraFeed = *(Mat*) imageRgba;
//	Mat threshold;
//	Mat hsv;
//
//	//convert frame from BGR to HSV colorspace
//	cvtColor(cameraFeed, hsv, COLOR_BGR2HSV);
//
//    vector<Object> colors;
//    colors.push_back(Object("blue"));
//    colors.push_back(Object("yellow"));
//    colors.push_back(Object("green"));
//
//    for (int i=0; i<colors.size(); i++) {
//        inRange(hsv, colors[i].getHSVmin(), colors[i].getHSVmax(), threshold);
////	  inRange(imageRgba, colors[i].getHSVmin(), colors[i].getHSVmax(), threshold);
//        morphOps(threshold);
////	  cvtColor(cameraFeed, hsv, COLOR_BGR2HSV);
//        trackFilteredObject(colors[i], threshold, hsv, cameraFeed);
//    }
//
//	return totalWeight;
//}

/**
 * Formerly main--changed to be function called by main.cpp
 */
//int main(int argc, char* argv[])
jfloat MultipleObjectTracking::detect(jlong imageRgba, jint h1, jint h2, jint s1, jint s2, jint v1, jint v2) {
	bool calibrationMode = false;

	//Matrix to store each frame of the webcam feed
	Mat& cameraFeed = *(Mat*) imageRgba;
	Mat threshold;
	Mat hsv;

	//convert frame from BGR to HSV colorspace
	cvtColor(cameraFeed, hsv, COLOR_BGR2HSV);

    vector<Object> colors;
    colors.push_back(Object("blue"));
    colors.push_back(Object("yellow"));
    colors.push_back(Object("green"));
	colors.push_back(Object("red"));
	colors.push_back(Object("black"));
    colors.push_back(Object(h1, h2, s1, s2, v1, v2));

    for (int i=0; i<colors.size(); i++) {
        inRange(hsv, colors[i].getHSVmin(), colors[i].getHSVmax(), threshold);
//	    inRange(imageRgba, colors[i].getHSVmin(), colors[i].getHSVmax(), threshold);
        morphOps(threshold);
//		cvtColor(cameraFeed, hsv, COLOR_BGR2HSV);
        trackFilteredObject(colors[i], threshold, hsv, cameraFeed);
    }

	return totalWeight;
}