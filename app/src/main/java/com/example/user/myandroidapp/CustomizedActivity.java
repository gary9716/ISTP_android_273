package com.example.user.myandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CustomizedActivity extends AppCompatActivity {

    public static final String debug_tag = "testAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(debug_tag, "it's onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(debug_tag, "it's onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(debug_tag, "it's onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(debug_tag, "it's onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(debug_tag, "it's onStop");
    }
}
