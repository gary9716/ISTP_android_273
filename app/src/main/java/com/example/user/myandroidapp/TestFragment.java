package com.example.user.myandroidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by user on 2016/8/30.
 */
public class TestFragment extends CustomizedFragment {

    final static String messageKey = "message";
    String mMessage;
    View fragmentView;

    public static TestFragment newInstance(String message) {

        Bundle args = new Bundle();
        args.putString(messageKey, message);

        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mMessage = args.getString(messageKey);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_test, null);
            TextView textView = (TextView)fragmentView.findViewById(R.id.textView);
            textView.setText(mMessage);
        }

        return fragmentView;
    }

}
