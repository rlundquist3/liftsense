# Color Info

Colors are detected using a range of HSV values (cv::inRange()). The OpenCV scale for HSV is:
	* H: 0-179
	* S: 0-255
	* V: 0-255

## Adding a new color
* New colors must be added as constructors in Object.cpp and instantiated in MultipleObjectTracking.cpp
* For now, colors can be tested using the (temporary) text fields in CameraView. The left column is the minimum HSV triplet, the right is the maximum. It is helpful to tweak the values, then point the camera at an image of the color wheel to see what the new range picks up (which will be selected in white)
* Test new color ranges in various lighting to make sure the range consistently picks up the color

## Ideas for use
* very distinctly colored tape with white borders will help differentiate between sizes and between adjacent plates of the same size
