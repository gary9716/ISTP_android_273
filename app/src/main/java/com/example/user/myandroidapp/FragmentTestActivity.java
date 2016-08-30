package com.example.user.myandroidapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class FragmentTestActivity extends AppCompatActivity implements View.OnClickListener {

    TestFragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        ((Button)findViewById(R.id.fragment1Button)).setOnClickListener(this);
        ((Button)findViewById(R.id.fragment2Button)).setOnClickListener(this);

        fragments = new TestFragment[2];
        fragments[0] = TestFragment.newInstance("this is fragment 1");
        fragments[0].fragmentName = "f1";
        fragments[1] = TestFragment.newInstance("this is fragment 2");
        fragments[1].fragmentName = "f2";

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.fragment1Button) {
            displayFragment(fragments[0], true);
        }
        else if(viewId == R.id.fragment2Button) {
            displayFragment(fragments[1], true);
        }
    }

    void displayFragment(Fragment fragment, boolean canBeReversed) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragmentContainer, fragment);
        if(canBeReversed) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}
