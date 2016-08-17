package com.rileylundquist.liftsense;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.io.File;


public class CameraActivity extends Activity implements CvCameraViewListener2 {

    private static final String TAG = "Camera Fragment";

    private Mat mRgba;
    private Mat mGray;
    private JNIDetector mNativeDetector;

    private PortraitCameraView mOpenCvCameraView;

    private boolean imageCaptured = false;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");

                    // Load native library after OpenCV initialization
                    System.loadLibrary("liftSenseCV");
                    Log.i(TAG, "post load library");

                    //mNativeDetector = new JNIDetector();
//                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mOpenCvCameraView = (PortraitCameraView) findViewById(R.id.camera_view);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.enableView();

        final ImageButton captureButton = (ImageButton) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageCaptured = true;
                    }
                }
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mGray = new Mat();
        mRgba = new Mat();
    }

    public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();

        if (imageCaptured) {
            /**
             * Pass Mat to native environment
             * Return outlines from native
             * Draw returned outlines on frame
             */
            //Mat result = new Mat();
            //mNativeDetector.colorDetect(mRgba, result);

            //mRgba = result;

            EditText h1e = (EditText) findViewById(R.id.editTextH);
            EditText h2e = (EditText) findViewById(R.id.editTextH2);
            EditText s1e = (EditText) findViewById(R.id.editTextS);
            EditText s2e = (EditText) findViewById(R.id.editTextS2);
            EditText v1e = (EditText) findViewById(R.id.editTextV);
            EditText v2e = (EditText) findViewById(R.id.editTextV2);
            int h1 = Integer.parseInt(h1e.getText().toString());
            int h2 = Integer.parseInt(h2e.getText().toString());
            int s1 = Integer.parseInt(s1e.getText().toString());
            int s2 = Integer.parseInt(s2e.getText().toString());
            int v1 = Integer.parseInt(v1e.getText().toString());
            int v2 = Integer.parseInt(v2e.getText().toString());

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            String barOption = sharedPref.getString("pref_bar_weight", "0");
            String unitOption = sharedPref.getString("pref_units", "0");

            float barWeight = 0;
            if (barOption.equals("0")) {
                if (unitOption.equals("lbs"))
                    barWeight = 45.0f;
                else
                    barWeight = 20.0f;
            } else {
                if (unitOption.equals("lbs"))
                    barWeight = 35.0f;
                else
                    barWeight = 16.0f;
            }

            float weight = nativeColorDetect2(mRgba.getNativeObjAddr(), h1, h2, s1, s2, v1, v2) + barWeight;

            Log.d(TAG, Float.toString(weight));

//      float weight = nativeColorDetect(mRgba.getNativeObjAddr()) + barWeight;

            if (imageCaptured) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", weight);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        }

        return mRgba;
    }

    //@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private native float nativeColorDetect(long inputImage);
    private native float nativeColorDetect2(long inputImage, int h1, int h2, int s1, int s2, int v1, int v2);
}
