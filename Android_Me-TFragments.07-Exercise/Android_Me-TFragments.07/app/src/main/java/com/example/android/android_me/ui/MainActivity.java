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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity is responsible for displaying the master list of all images
// Implement the MasterListFragment callback, OnImageClickListener
public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    // Variables to store the values for the list index of the selected images
    // The default value will be index = 0
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    // COMPLETED (3) Create a boolean variable to track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // COMPLETED (4) If you are making a two-pane display, add new BodyPartFragments to create an initial Android-Me image
        // Also, for the two-pane display, get rid of the "Next" button in the master list fragment
        // figuring out whether the view will be in a single pane mode or double mode display
        if (findViewById(R.id.android_me_linear_layout) != null) {
            // then we will ensure its viewing on TwoPane UI, so we will set it to True.
            mTwoPane = true;

            // Getting rid of the Next Button from the screen on two pane mode (1 of 2 Ui changes)
            // for that wee need to get the refernce of that Next Button then setVisibility to GONE
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            // lets add somee more space to the ImageGridView for the two pane mode on tablet, these are
            // just small UI changes. 2 of 2.
            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            // setting the number of collumns
            gridView.setNumColumns(2);


            // Creating a new body part fragments for this view on screen
            if (savedInstanceState == null) {
                // In two pane mode we need to add additional BodyPartFragments to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();

                // creating a new headFragment for the two pane mode
                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImageIds(AndroidImageAssets.getHeads());
                // Adding to its container using Trannsaction on this fragment
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                // Creating a new bodyFragment for this view on the screen
                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                // Adding to its container using Transaction on this
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                // Creating a new legFragment for this view
                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());
                // Adding this to the leg container by Transaction
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        } else {
            // setting to false if its not on Two Pane UI
            mTwoPane = false;
        }

    }

    // Define the behavior for onImageSelected
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        // COMPLETED (5) Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        // The two-pane case will not need a Bundle or Intent since a new activity will not be started;
        // This is all happening in this MainActivity and one fragment will be replaced at a time


        // Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments

        // bodyPartNumber will be = 0 for the head fragment, 1 for the body, and 2 for the leg fragment
        // Dividing by 12 gives us these integer values because each list of images resources has a size of 12
        int bodyPartNumber = position / 12;

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0-11
        int listIndex = position - 12 * bodyPartNumber;

        // now we need to seperate the case of Two Pane mode we will acheive this via if-else
        // (which we already implemented for the Smaller/ Single pane display
        if (mTwoPane) {
            // crating a two pane compatible BodyPartFragment object to attach
            // those fragments differently than a single pane mode
            BodyPartFragment doublePaneModeFragment = new BodyPartFragment();
            // Creating another BodyPartFragment for the two pane body part fragment
            switch (bodyPartNumber) {
                case 0:
                    // Similiarly ass single pane we need to attend to the two pane mode compatability on the screen

                    // A head Image (IDs & Index) to be set on the double pane mode
                    doublePaneModeFragment.setImageIds(AndroidImageAssets.getHeads());
                    doublePaneModeFragment.setListIndex(listIndex);
                    // Replace the old Head Fragment from the single-pane mode with the new double-pane mode
                    // using Transaction on getSupportFragmentManaget()
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, doublePaneModeFragment)
                            .commit();
                    break;

                case 1:
                    // A body Image (IDs & Index) to be set on double-pane mode
                    // Create and display the bodyFragment BodyPartFragments
                    doublePaneModeFragment.setImageIds(AndroidImageAssets.getBodies());
                    doublePaneModeFragment.setListIndex(listIndex);
                    // Replacing the old value with New value for the fragments on the screen.
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, doublePaneModeFragment)
                            .commit();
                    break;

                case 2:
                    // A leg Image resource (IDs & index) need to be set for consistent performance on double-pane mode
                    // Create and display legFragment for the Two-Pane mode  BodyPartFragments
                    doublePaneModeFragment.setImageIds(AndroidImageAssets.getLegs());
                    doublePaneModeFragment.setListIndex(listIndex);
                    // Replacing the old leg image resource view on the app with the scope variants double-pane mode
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, doublePaneModeFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        } else {
            // Set the currently displayed item for the single pane body part fragment
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }

            // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);

            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(b);

            // The "Next" button launches a new AndroidMeActivity
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });

        }
    }

}
