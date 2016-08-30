package com.example.user.myandroidapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 2016/8/30.
 */


public class CustomizedFragment extends Fragment {

    public final static String debug_tag = "fragmentTest";
    public String fragmentName = "";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(debug_tag, fragmentName + " is onAttach(activity)");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(debug_tag, fragmentName + " is onAttach(context)");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(debug_tag, fragmentName + " is onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(debug_tag, fragmentName + " is onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(debug_tag, fragmentName + " is onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(debug_tag, fragmentName + " is onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(debug_tag, fragmentName + " is onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(debug_tag, fragmentName + " is onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(debug_tag, fragmentName + " is onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(debug_tag, fragmentName + " is onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(debug_tag, fragmentName + " is onDetach");
    }
}
