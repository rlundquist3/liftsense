package com.rileylundquist.liftsense;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;

public class JNIDetector
{
    public JNIDetector() {
        mNativeObj = nativeCreateObject(mNativeObj);
    }

//    public void start() {
//        nativeStart(mNativeObj);
//    }
//
//    public void stop() {
//        nativeStop(mNativeObj);
//    }
//
//    public void detect(Mat imageGray, MatOfRect faces) {
//        nativeDetect(mNativeObj, imageGray.getNativeObjAddr(), faces.getNativeObjAddr());
//    }

    public void colorDetect(Mat imageRgba, Mat result) {
        nativeColorDetect(mNativeObj, imageRgba.getNativeObjAddr(), result.getNativeObjAddr());
    }

    public void release() {
        nativeDestroyObject(mNativeObj);
        mNativeObj = 0;
    }

    private long mNativeObj = 0;

    private static native long nativeCreateObject(long thiz);
    private static native void nativeDestroyObject(long thiz);
//    private static native void nativeStart(long thiz);
//    private static native void nativeStop(long thiz);
//    private static native void nativeDetect(long thiz, long inputImage, long faces);
    private static native void nativeColorDetect(long thiz, long inputImage, long result);
}
