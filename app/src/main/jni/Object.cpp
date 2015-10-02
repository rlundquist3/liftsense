//Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software")
//, to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
//and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
//The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//IN THE SOFTWARE.

#include "Object.h"

Object::Object()
{
	//set values for default constructor
	setType("Object");
	setColor(Scalar(0,0,0));

}

Object::Object(string name){

	setType(name);
	
	if(name=="blue"){

		//TODO: use "calibration mode" to find HSV min
		//and HSV max values

		setHSVmin(Scalar(92,0,0));
		setHSVmax(Scalar(124,256,256));

		//BGR value for Green:
		setColor(Scalar(255,0,0));

	}
	if(name=="green"){

		//TODO: use "calibration mode" to find HSV min
		//and HSV max values

		setHSVmin(Scalar(34,50,50));
		setHSVmax(Scalar(80,220,200));

		//BGR value for Yellow:
		setColor(Scalar(0,255,0));

	}
	if(name=="yellow"){

		//TODO: use "calibration mode" to find HSV min
		//and HSV max values

		setHSVmin(Scalar(20,124,123));
		setHSVmax(Scalar(30,256,256));

		//BGR value for Red:
		setColor(Scalar(0,255,255));

	}
	if(name=="red"){

		//TODO: use "calibration mode" to find HSV min
		//and HSV max values

		setHSVmin(Scalar(0,200,0));
		setHSVmax(Scalar(19,255,255));

		//BGR value for Red:
		setColor(Scalar(0,0,255));

	}
}

Object::~Object(void)
{
}

int Object::getXPos(){

	return Object::xPos;

}

void Object::setXPos(int x){

	Object::xPos = x;

}

int Object::getYPos(){

	return Object::yPos;

}

void Object::setYPos(int y){

	Object::yPos = y;

}

Scalar Object::getHSVmin(){

	return Object::HSVmin;

}
Scalar Object::getHSVmax(){

	return Object::HSVmax;
}

void Object::setHSVmin(Scalar min){

	Object::HSVmin = min;
}


void Object::setHSVmax(Scalar max){

	Object::HSVmax = max;
}
