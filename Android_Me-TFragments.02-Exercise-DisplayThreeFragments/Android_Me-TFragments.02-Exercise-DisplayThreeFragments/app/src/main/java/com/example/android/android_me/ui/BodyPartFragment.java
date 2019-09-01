/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;

import java.util.List;

public class BodyPartFragment extends Fragment {

    // Tag for showcat logging.
    private static final String TAG = "BodyPartFragment";

    // COMPLETED (1) Create a setter method and class variable to set and store of a list of image resources

    // COMPLETED (2) Create another setter method and variable to track and set the index of the list item to display
        // ex. index = 0 is the first image id in the given list , index 1 is the second, and so on

    // variables to store a list of Image resources which are type integer.
    private List<Integer> mListOfImageResourceIds;
    // variable for indexing to the image that this fragment displays
    private int mImageResourceIndex;

    /** Now we will head out  to implement the setter and getter methods to display resources accordingly.*/

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public BodyPartFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        // Get a reference to the ImageView in the fragment layout
        ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        // Set the image to the first in our list of head images
//        imageView.setImageResource(AndroidImageAssets.getHeads().get(0));   // we dont this hardcoded parameter instead we will use dynamic approcah to deal with this phenomena.

        // COMPLETED (3) If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found

        // checking whetherr it has resources in the firstplace else will return the provided one.
        if (mListOfImageResourceIds != null) {
            // setting the image resources to the list Item at the stored index.
            imageView.setImageResource(mListOfImageResourceIds.get(mImageResourceIndex));
        } else {
            // Log a statement of its Unavailablity, hence null
            Log.v(TAG, "This fragment has a NULL list of images!" +mListOfImageResourceIds);
        }

        // Return the rootView
        return rootView;
    }

    // Setter methods for keeping track of the Image resources this fragment can display and which image
    // is in the fragment currently being displayed.
    public void setmImageResourceId(List<Integer> imageResourceIds) {
        mListOfImageResourceIds = imageResourceIds;
    }

    public void setmImageResourceIndex(int index) {
        mImageResourceIndex = index;
    }

}
