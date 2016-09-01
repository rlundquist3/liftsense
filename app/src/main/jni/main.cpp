#include "com_rileylundquist_liftsense_CameraActivity.h"
#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/objdetect.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <android/log.h>
#include "MultiObjectTrackingBasedOnColor/MultipleObjectTracking.h"

#define LOG_TAG "JNI_main"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

using namespace std;
using namespace cv;

JNIEXPORT jfloat JNICALL Java_com_rileylundquist_liftsense_CameraActivity_nativeColorDetect
(JNIEnv *jenv, jobject obj, jlong imageRgba)
{
    MultipleObjectTracking tracker;
    return tracker.detect(imageRgba);

}

/*JNIEXPORT jfloat JNICALL Java_com_rileylundquist_liftsense_CameraActivity_nativeColorDetect2
(JNIEnv *jenv, jobject obj, jlong imageRgba, jint h1, jint h2, jint s1, jint s2, jint v1, jint v2)
{
    MultipleObjectTracking tracker;
    return tracker.detect(imageRgba, h1, h2, s1, s2, v1, v2);

}*/