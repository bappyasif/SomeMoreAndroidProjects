

/**
 * Created by BappY on 5/6/2017.
 */

package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

class Emojifier {

    // Crating LOG TAG for log screen
    private static final String LOG_TAG = Emojifier.class.getSimpleName();

    /**
     Method for detecting faces in a bitmap.
     COMPLETED (2): Create a static method in the Emojifier class called detectFaces()
     which detects and logs the number of faces in a given bitmap ********* ******* */

    static void detectFaces(Context context, Bitmap picture) {

        // Create the face detector (using FaceDetector.Builder()), disable tracking and enable classifications
        // while using .Builder() dont forget to use build() at the end.
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        // Build the frame wheere it will be
        Frame frame = new Frame.Builder().setBitmap(picture).build();

        // Detect the faces
        SparseArray<Face> faces = detector.detect(frame);

        // Log the number of faces
        Log.d(LOG_TAG, "detectFaces: number of faces = " + faces.size());

        // If there are no faces detected, show a Toast message
        if(faces.size() == 0){
            Toast.makeText(context, R.string.no_faces_message, Toast.LENGTH_SHORT).show();
        }

        // Release the detector
        detector.release();
    }
}