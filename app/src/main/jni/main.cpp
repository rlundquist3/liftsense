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

JNIEXPORT void JNICALL Java_com_rileylundquist_liftsense_CameraActivity_nativeColorDetect
(JNIEnv *jenv, jobject obj, jlong imageRgba)
{
    MultipleObjectTracking tracker;
    tracker.detect(imageRgba);

}