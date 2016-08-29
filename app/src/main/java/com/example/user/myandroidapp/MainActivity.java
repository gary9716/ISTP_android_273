package com.example.user.myandroidapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends CustomizedActivity implements View.OnClickListener, EditText.OnEditorActionListener {

    public static final String selectedPokemonIndexKey = "selectedPokemonIndexKey";

    static final String[] pokemonNames = {"小火龍","傑尼龜","妙蛙種子"};
    TextView infoText;
    EditText nameEditText;
    RadioGroup optionsGroup;
    Button confirmBtn;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        handler = new Handler(this.getMainLooper());

        activityName = this.getClass().getSimpleName();

        //find UIs by their ids
        infoText = (TextView)findViewById(R.id.infoText);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        nameEditText.setOnEditorActionListener(this);
        nameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        optionsGroup = (RadioGroup)findViewById(R.id.optionsGroup);
        confirmBtn = (Button)findViewById(R.id.confirmButton);
        confirmBtn.setOnClickListener(MainActivity.this);

    }

    Runnable jumpToNewActivityTask = new Runnable() {
        @Override
        public void run() {
            jumpToNewActivity();
        }
    };

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.confirmButton) {
            //confirmButton was clicked
            Log.d("buttonTest","confirm-button was clicked");
            String name = nameEditText.getText().toString();

            int selectedRadioButtonId = optionsGroup.getCheckedRadioButtonId();
            View selectedRadioButtonView = optionsGroup.findViewById(selectedRadioButtonId);
//            int selectedIndex = optionsGroup.indexOfChild(selectedRadioButtonView);

            RadioButton selectedRadioButton = (RadioButton)selectedRadioButtonView;
            String radioBtnText = selectedRadioButton.getText().toString();

            String welcomeMessage = String.format(
                    "你好, 訓練家%s 歡迎來到神奇寶貝的世界 你的第一個夥伴是%s",
                    name,
                    radioBtnText
            );

            infoText.setText(welcomeMessage);

            handler.postDelayed(jumpToNewActivityTask, 3 * 1000); //delay 3 seconds
        }
    }

    private int getSelectedPokemonIndex() {
        int selectedRadioButtonId = optionsGroup.getCheckedRadioButtonId();
        View selectedRadioButtonView = optionsGroup.findViewById(selectedRadioButtonId);
        return optionsGroup.indexOfChild(selectedRadioButtonView);
    }

    private void jumpToNewActivity() {

        Intent intent = new Intent();

        intent.setClass(MainActivity.this, PokemonListActivity.class);
        intent.putExtra(selectedPokemonIndexKey, getSelectedPokemonIndex());

        startActivity(intent);
        finish();

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            //dismiss virtual keyboard
            InputMethodManager inm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            //simulate button clicked
            confirmBtn.performClick();
            return true;
        }
        return false;
    }
}
