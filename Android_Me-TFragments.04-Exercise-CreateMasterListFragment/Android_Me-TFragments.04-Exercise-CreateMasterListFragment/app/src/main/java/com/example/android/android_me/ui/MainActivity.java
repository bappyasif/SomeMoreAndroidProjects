package com.example.android.android_me.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.android.android_me.R;

/**
 * Created by BappY on 5/4/2017.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // simply setting the layout for this class
        setContentView(R.layout.activity_main);
    }
}
