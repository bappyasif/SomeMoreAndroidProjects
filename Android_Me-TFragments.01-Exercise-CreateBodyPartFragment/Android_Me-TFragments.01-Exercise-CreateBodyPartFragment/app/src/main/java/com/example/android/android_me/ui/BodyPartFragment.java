package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by BappY on 5/4/2017.
 */

public class BodyPartFragment extends Fragment {
    // Mandatory implementation on initial creation of a Class.java
    // is to create an constructor for instantiating a Task, in this case, the fragment
    public BodyPartFragment(){
        // empty constructor
    }

    // after that we need to implement an onCreate() to define it's functionality on the app on the screen
    // In this case we will inflate the fragment layout and sets the image resources using an onCreateView()
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflating the Android_ME fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        // also getting the reference to the ImageView in the fragment layout
        ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        // now setting the image resource to display
        imageView.setImageResource(AndroidImageAssets.getHeads().get(0));

        // return the root view
        return rootView;
    }
}
