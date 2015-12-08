#include "com_rileylundquist_liftsense_MainActivity.h"
#include <opencv2/core/core.hpp>
#include <opencv2/objdetect.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <android/log.h>

#define LOG_TAG "JNI_main"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

using namespace std;
using namespace cv;


inline void vector_Rect_to_Mat(vector<Rect>& v_rect, Mat& mat)
{
    mat = Mat(v_rect, true);
}

class CascadeDetectorAdapter: public DetectionBasedTracker::IDetector
{
public:
    CascadeDetectorAdapter(Ptr<CascadeClassifier> detector):
            IDetector(),
            Detector(detector)
    {
        LOGD("CascadeDetectorAdapter::Detect::Detect");
        CV_Assert(detector);
    }

    void detect(const Mat &Image, vector<Rect> &objects)
    {
        LOGD("CascadeDetectorAdapter::Detect: begin");
        LOGD("CascadeDetectorAdapter::Detect: scaleFactor=%.2f, minNeighbours=%d, minObjSize=(%dx%d), maxObjSize=(%dx%d)", scaleFactor, minNeighbours, minObjSize.width, minObjSize.height, maxObjSize.width, maxObjSize.height);
        Detector->detectMultiScale(Image, objects, scaleFactor, minNeighbours, 0, minObjSize, maxObjSize);
        LOGD("CascadeDetectorAdapter::Detect: end");
    }

    virtual ~CascadeDetectorAdapter()
    {
        LOGD("CascadeDetectorAdapter::Detect::~Detect");
    }

private:
    CascadeDetectorAdapter();
    Ptr<CascadeClassifier> Detector;
};

struct DetectorAggregator
{
    Ptr<CascadeDetectorAdapter> mainDetector;
    Ptr<CascadeDetectorAdapter> trackingDetector;

    Ptr<DetectionBasedTracker> tracker;
    DetectorAggregator(Ptr<CascadeDetectorAdapter>& _mainDetector, Ptr<CascadeDetectorAdapter>& _trackingDetector):
            mainDetector(_mainDetector),
            trackingDetector(_trackingDetector)
    {
        CV_Assert(_mainDetector);
        CV_Assert(_trackingDetector);

        DetectionBasedTracker::Parameters DetectorParams;
        tracker = makePtr<DetectionBasedTracker>(mainDetector, trackingDetector, DetectorParams);
    }
};

JNIEXPORT jlong JNICALL Java_com_rileylundquist_liftsense_JNIDetector_nativeCreateObject
(JNIEnv * jenv, jclass, jstring jFileName, jint faceSize)
{
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeCreateObject enter");
    const char* jnamestr = jenv->GetStringUTFChars(jFileName, NULL);
    string stdFileName(jnamestr);
    jlong result = 0;

    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeCreateObject");

    try
    {
        Ptr<CascadeDetectorAdapter> mainDetector = makePtr<CascadeDetectorAdapter>(
            makePtr<CascadeClassifier>(stdFileName));
        Ptr<CascadeDetectorAdapter> trackingDetector = makePtr<CascadeDetectorAdapter>(
            makePtr<CascadeClassifier>(stdFileName));
        result = (jlong)new DetectorAggregator(mainDetector, trackingDetector);
        if (faceSize > 0)
        {
            mainDetector->setMinObjectSize(Size(faceSize, faceSize));
            //trackingDetector->setMinObjectSize(Size(faceSize, faceSize));
        }
    }
    catch(Exception& e)
    {
        LOGD("nativeCreateObject caught Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
        catch (...)
        {
        LOGD("nativeCreateObject caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeCreateObject()");
        return 0;
    }

    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeCreateObject exit");
    return result;
}

JNIEXPORT void JNICALL Java_com_rileylundquist_liftsense_JNIDetector_nativeDestroyObject
(JNIEnv * jenv, jclass, jlong thiz)
{
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeDestroyObject");

    try
    {
        if(thiz != 0)
        {
            ((DetectorAggregator*)thiz)->tracker->stop();
            delete (DetectorAggregator*)thiz;
        }
    }
    catch(Exception& e)
    {
        LOGD("nativeestroyObject caught Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeDestroyObject caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeDestroyObject()");
    }
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeDestroyObject exit");
}

JNIEXPORT void JNICALL Java_com_rileylundquist_liftsense_JNIDetector_nativeStart
(JNIEnv * jenv, jclass, jlong thiz)
{
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeStart");

    try
    {
        ((DetectorAggregator*)thiz)->tracker->run();
    }
    catch(Exception& e)
    {
        LOGD("nativeStart caught Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeStart caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeStart()");
    }
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeStart exit");
}

JNIEXPORT void JNICALL Java_com_rileylundquist_liftsense_JNIDetector_nativeStop
(JNIEnv * jenv, jclass, jlong thiz)
{
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeStop");

    try
    {
        ((DetectorAggregator*)thiz)->tracker->stop();
    }
    catch(Exception& e)
    {
        LOGD("nativeStop caught Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeStop caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeStop()");
    }
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeStop exit");
}

JNIEXPORT void JNICALL Java_com_rileylundquist_liftsense_JNIDetector_nativeSetFaceSize
(JNIEnv * jenv, jclass, jlong thiz, jint faceSize)
{
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeSetFaceSize -- BEGIN");

    try
    {
        if (faceSize > 0)
        {
            ((DetectorAggregator*)thiz)->mainDetector->setMinObjectSize(Size(faceSize, faceSize));
            //((DetectorAggregator*)thiz)->trackingDetector->setMinObjectSize(Size(faceSize, faceSize));
        }
    }
    catch(Exception& e)
    {
        LOGD("nativeStop caught Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeSetFaceSize caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeSetFaceSize()");
    }
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeSetFaceSize -- END");
}


JNIEXPORT void JNICALL Java_com_rileylundquist_liftsense_JNIDetector_nativeDetect
(JNIEnv * jenv, jclass, jlong thiz, jlong imageGray, jlong faces)
{
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeDetect");

    try
    {
        vector<Rect> RectFaces;
        ((DetectorAggregator*)thiz)->tracker->process(*((Mat*)imageGray));
        ((DetectorAggregator*)thiz)->tracker->getObjects(RectFaces);
        *((Mat*)faces) = Mat(RectFaces, true);
    }
    catch(Exception& e)
    {
        LOGD("nativeCreateObject caught Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeDetect caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code DetectionBasedTracker.nativeDetect()");
    }
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeDetect END");
}

JNIEXPORT void JNICALL Java_com_rileylundquist_liftsense_JNIDetector_colorDetect
        (JNIEnv *jenv, jclass, jlong thiz, jlong imageRgba, jlong oultines)
{
    /**
     * call multipleObjectTracking
     */
    try {

    }
}

/*
 * Class:     com_rileylundquist_liftsense_MainActivity
 * Method:    stringFromJNI
 * Signature: ()Ljava/lang/String;
 */
/*JNIEXPORT jstring JNICALL Java_com_rileylundquist_liftsense_MainActivity_stringFromJNI
  (JNIEnv * env, jobject obj) {
    return env->NewStringUTF("Hello from JNI");
  }*/

/*JNIEXPORT void JNICALL Java_com_rileylundquist_liftsense_MainActivity_nativeDetect(JNIEnv* jenv, jobject, jstring jFileName, jlong addrRgba, jlong addrRetVal) {
    const char* jnamestr = jenv->GetStringUTFChars(jFileName, NULL);
    string stdFileName(jnamestr);

    Mat& mRgba = *(Mat*)addrRgba;
    Mat& retValMat = *(Mat*)addrRetVal;
    Mat gray;
    vector<Rect> faces;

    jint retVal;
    int faceFound=0;

    mRgba.copyTo(retValMat);

    cvtColor(mRgba, gray, CV_RGBA2GRAY);

    CascadeClassifier face_cascade;
    face_cascade.load(stdFileName);
    LOGD("cascade loaded\n");

    face_cascade.detectMultiScale(gray, faces, 2, 1,
                CV_HAAR_FIND_BIGGEST_OBJECT |  CV_HAAR_SCALE_IMAGE,
                Size(30, 30), Size(900, 900));
    LOGD("detectMultiScale\n");

    if (faces.size() > 0)
    {
        int index;
        Rect face;
        for(index=0; index<faces.size(); index++){
            face = faces[index];
            rectangle(retValMat, face, Scalar(255, 0, 0), 3);
        }
        faceFound = 1;
        LOGD("face found\n");
    }
    else
    {
        LOGD("face not found\n");
        faceFound = 0;
    }

    retVal = (jint)faceFound;

    return retVal;
}*/