#include "Object.h"

Object::Object()
{
	//set values for default constructor
	setType("Object");
	setColor(Scalar(0,0,0));

}

Object::Object(string name){

	setType(name);

	if(name=="green"){

		//TODO: use "calibration mode" to find HSV min
		//and HSV max values

		setHSVmin(Scalar(34,50,50));
		setHSVmax(Scalar(80,220,200));

		setColor(Scalar(0,255,0));

	}
	else if(name=="blue"){

		//TODO: use "calibration mode" to find HSV min
		//and HSV max values

		setHSVmin(Scalar(102, 97, 89));
		setHSVmax(Scalar(125,255,255));

		setColor(Scalar(0,0,255));

	}
	else if(name=="yellow"){

		//TODO: use "calibration mode" to find HSV min
		//and HSV max values

		setHSVmin(Scalar(20, 100, 100));
		setHSVmax(Scalar(34,255,255));

		setColor(Scalar(255,255,0));

	}
//	if(name=="red"){
//
//		//TODO: use "calibration mode" to find HSV min
//		//and HSV max values
//
//		setHSVmin(Scalar(92,0,0));
//		setHSVmax(Scalar(124,256,256));
//
//		setColor(Scalar(255,0,0));
//
//	}

//	if(name=="blue2"){
//
//		//TODO: use "calibration mode" to find HSV min
//		//and HSV max values
//
//		setHSVmin(Scalar(20,124,123));
//		setHSVmax(Scalar(30,256,256));
//
//		setColor(Scalar(0,255,255));
//
//	}

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
