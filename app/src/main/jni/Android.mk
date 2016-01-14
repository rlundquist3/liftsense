LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

#opencv
OPENCVROOT:= /Users/rlundquist3/Development/Android/OpenCV-android-sdk
OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
include ${OPENCVROOT}/sdk/native/jni/OpenCV.mk

LOCAL_C_INCLUDES := /Users/rlundquist3/Development/Android/OpenCV-android-sdk/sdk/native/jni/include

LOCAL_SRC_FILES := main.cpp MultiObjectTrackingBasedOnColor/MultipleObjectTracking.h MultiObjectTrackingBasedOnColor/MultipleObjectTracking.cpp MultiObjectTrackingBasedOnColor/Object.h MultiObjectTrackingBasedOnColor/Object.cpp
LOCAL_LDLIBS += -llog
LOCAL_MODULE := liftSenseCV

include $(BUILD_SHARED_LIBRARY)