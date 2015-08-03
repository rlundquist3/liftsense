package com.rileylundquist.liftsense;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;

/**
 * Created by rlundquist3 on 8/2/15.
 */
public class JNIDetector {

    public JNIDetector(String cascadeName, int minFaceSize) {
        mNativeObj = nativeCreateObject(cascadeName, minFaceSize);
    }

    public void detect(Mat imageGray, MatOfRect faces) {
        nativeDetect(mNativeObj, imageGray.getNativeObjAddr(), faces.getNativeObjAddr());
    }

    public void setMinFaceSize(int size) {
        nativeSetFaceSize(mNativeObj, size);
    }

    private long mNativeObj = 0;
    private static native long nativeCreateObject(String cascadeName, int minFaceSize);
    private static native void nativeDetect(long thiz, long inputImage, long faces);
    private static native void nativeSetFaceSize(long thiz, int size);
}
