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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;

// This activity is responsible for displaying the master list of all images
// Implement the MasterListFragment callback, OnImageClickListener
public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener{

    // variables to store the value for the list index of the selected images from fragments.
    // The default value will be 0 and maximum can be 11 in total 12 items per fragments
    private int headImageIndex;
    private int bodyImageIndex;
    private int legImageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Define the behavior for onImageSelected
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

// COMPLETED (2) Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments
        // bodyPartNumber(position/12) will be 0 -headFragment 1 -bodyFragment 2 -legFragment
        int bodyPartFragmentNumber = position / 12;
        // Storing the correct list index on clicked, which means it will always carry values between 0 and 11.
        int listIndex = position - 12*bodyPartFragmentNumber;

        // Setting the current displayed item for associate bodyPartFragent by using a swith statement
        switch (bodyPartFragmentNumber){
            case 0:
                headImageIndex = listIndex;
                break;
            case 1:
                bodyImageIndex = listIndex;
                break;
            case 2:
                legImageIndex = listIndex;
                break;
            default:
                break;
        }

        // COMPLETED (3) Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
        // putting this information in a Bundle and attaching it to an Intent that will launch the host activity (AndroidMeActivity)
        Bundle bundle = new Bundle();
        bundle.putInt("headIndex ", headImageIndex);
        bundle.putInt("bodyIndex ", bodyImageIndex);
        bundle.putInt("legIndex", legImageIndex);

        // Attching the bundle to an Intent to perform Image resources changing.
        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(bundle);

        // COMPLETED (4) Get a reference to the "Next" button and launch the intent when this button is clicked
        // Button Next triggers an activity (AndroidMeActivity)
        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
