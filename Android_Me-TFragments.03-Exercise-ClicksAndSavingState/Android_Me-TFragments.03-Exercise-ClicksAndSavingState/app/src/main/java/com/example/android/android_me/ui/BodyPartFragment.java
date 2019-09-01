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

import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {

    // COMPLETED (3) Create final Strings to store state information about the list of images and list index

    // Final strings to store state information about the list of images and list Index.
    public static final String IMAGE_ID_LIST = "image_resource_ids";
    public static final String LIST_OF_IMAGE_INDEX = "list_image_index";

    // Tag for logging
    private static final String TAG = "BodyPartFragment";

    // Variables to store a list of image resources and the index of the image that this fragment displays
    private List<Integer> mImageIds;
    private int mListIndex;

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

        // loading the saved state (the lsit of images and list index) if there is any.
        // TO NOTE: we are doing it to avoid the app view inconsistency.
        if (savedInstanceState != null){
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_OF_IMAGE_INDEX);
        }

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        // Get a reference to the ImageView in the fragment layout
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view); // if it wasnt final then calling it ono the setImageResource() would show an error for not being final

        // If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        if(mImageIds != null){
            // Set the image resource to the list item at the stored index
            imageView.setImageResource(mImageIds.get(mListIndex));

            // COMPLETED (1) Set a click listener on the image view and on a click increment the list index and set the image resource
            // COMPLETED (2) If you reach the end of a list of images, set the list index back to 0 (the first item in the list)

            // Setting a click listener on the image view
            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // Increment the position of the Image resource Id to the next resources through out the list
                    if (mListIndex < mImageIds.size()-1) { // on fail execution we will reset this value to -1
                        mListIndex++;
                    } else {
                        // The end of the list has been reached.. so returning to the begining of the list
                        mListIndex = 0;      // thus we can keep on displaying the fragments at will
                    }
                    // Setting the new image resource to the new list item to be stored
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });

        } else {
            Log.v(TAG, "This fragment has a null list of image id's");
        }

        // Return the rootView
        return rootView;
    }

    // Setter methods for keeping track of the list images this fragment can display and which image
    // in the list is currently being displayed

    public void setImageIds(List<Integer> imageIds) {
        mImageIds = imageIds;
    }

    public void setListIndex(int index) {
        mListIndex = index;
    }

    // COMPLETED (4) Override onSaveInstanceState and save the current state of this fragment

    // in order to keep our app current state intact when we rotate the screen or any interruption for that matter. to avoid badStateHolder for the app
    // we will acheive it through by overriding onSaveeInstanceState and saving the current state.
    // here we want to save the image list and the current list index as (key, value) pair.

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        // now saving the states of those variants(image ids and list index) using put.....()
        // also casting image id acordingly (ArrayList<E>)
        currentState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
        currentState.putInt(LIST_OF_IMAGE_INDEX, mListIndex);
    }
}
