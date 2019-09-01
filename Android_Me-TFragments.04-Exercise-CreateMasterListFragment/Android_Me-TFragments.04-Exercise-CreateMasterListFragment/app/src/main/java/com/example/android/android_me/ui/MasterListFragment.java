package com.example.android.android_me.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by BappY on 5/4/2017.
 */

public class MasterListFragment extends Fragment {

    // COMPLETED (2) Create a new class called MasterListFragment which will display the GridView list of ALL AndroidMe images
    // In the fragment class, you'll need to implement an empty constructor, and onCreateView
    public MasterListFragment(){
        // empty constructor
    }

    // Now overriding the onCreateView()where it Inflates the fragment layout file and
    // sets the correct resource for the image to display
    // Inflates the GridView of all AndroidMe images

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Inflate the gridView for the fragment layout
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // getting a refernce to the GridView in the fragment_master_list layout file.
        GridView gridView = (GridView) rootView.findViewById(R.id.images_grid_view);

        // COMPLETED (3) In the MasterListFragment class, create a new MasterListAdapter and set it on the GridView
        // The MasterListAdapter code is provided; it creates the ImageViews that are contained in the GridView
        // The adapter takes as parameters (Context context, List<Integer> imageIds)
        // it takes in context  and an arrayList of all images to display at once on the screen.
        MasterListAdapter masterListAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            masterListAdapter = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());
        }

        // setting the adapter with gridview
        gridView.setAdapter(masterListAdapter);

        // returning the rootView
        return rootView;
    }

}
