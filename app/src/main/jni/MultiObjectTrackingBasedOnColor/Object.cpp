#include "Object.h"

Object::Object()
{
	//set values for default constructor
	setType("Object");
	setColor(Scalar(0,0,0));

}

Object::Object(int h1, int h2, int s1, int s2, int v1, int v2) {
	setType("Object");
	setHSVmin(Scalar(h1, s1, v1));
	setHSVmax(Scalar(h2, s2, v2));

	setColor(Scalar(255,255,255));
}

Object::Object(string name){

	setType(name);

	if(name=="green"){
		setHSVmin(Scalar(22,10,75));
		setHSVmax(Scalar(80,255,255));

		setColor(Scalar(0,255,0));
	}
	else if(name=="blue"){
		setHSVmin(Scalar(0, 50, 75));
		setHSVmax(Scalar(20,255,255));

		setColor(Scalar(0,0,255));
	}
	else if(name=="yellow"){
		setHSVmin(Scalar(83, 50, 75));
		setHSVmax(Scalar(90,255,255));

		setColor(Scalar(255,255,0));
	}
	else if(name=="red"){
		setHSVmin(Scalar(105, 50, 75));
		setHSVmax(Scalar(125,255,255));

		setColor(Scalar(255,0,0));
	}
	else if(name=="black"){
		setHSVmin(Scalar(0, 0, 0));
		setHSVmax(Scalar(179,255,10));

		setColor(Scalar(0,0,0));
	}
//	if(name=="red"){
//		setHSVmin(Scalar(92,0,0));
//		setHSVmax(Scalar(124,256,256));
//
//		setColor(Scalar(255,0,0));
//	}

//	if(name=="blue2"){
//		setHSVmin(Scalar(20,124,123));
//		setHSVmax(Scalar(30,256,256));
//
//		setColor(Scalar(0,255,255));
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
