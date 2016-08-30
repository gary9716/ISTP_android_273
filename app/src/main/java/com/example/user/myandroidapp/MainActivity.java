package com.example.user.myandroidapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

public class MainActivity extends CustomizedActivity implements View.OnClickListener, EditText.OnEditorActionListener {

    public static final String selectedPokemonIndexKey = "selectedPokemonIndexKey";
    public static final String trainerNameKey = "trainerName";
    public static final String selectedOptionIndexKey = "selectedOptionIndex";

    static final String[] pokemonNames = {"小火龍","傑尼龜","妙蛙種子"};
    TextView infoText;
    EditText nameEditText;
    RadioGroup optionsGroup;
    Button confirmBtn;
    Handler handler;
    ProgressBar progressBar;
    SharedPreferences preferences;

    int selectedOptionIndex = 0;
    String nameOfTheTrainer = null;

    boolean isFirstTimeUsingThisPage = false;

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

        progressBar = (ProgressBar)findViewById(R.id.loadingBar);
        progressBar.setIndeterminateDrawable(new CircularProgressDrawable
                .Builder(this)
                .colors(getResources().getIntArray(R.array.gplus_colors))
                .sweepSpeed(1f)
                .strokeWidth(10f)
                .build()
        );

        preferences = getSharedPreferences(Application.class.getSimpleName(), MODE_PRIVATE);

        nameOfTheTrainer = preferences.getString(trainerNameKey, null);
        selectedOptionIndex = preferences.getInt(selectedOptionIndexKey, 0);

        //set the visibility of UIs
        if(nameOfTheTrainer == null) { //first time
            nameEditText.setVisibility(View.VISIBLE);
            optionsGroup.setVisibility(View.VISIBLE);
            confirmBtn.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.INVISIBLE);
            isFirstTimeUsingThisPage = true;
        }
        else {
            nameEditText.setVisibility(View.INVISIBLE);
            optionsGroup.setVisibility(View.INVISIBLE);
            confirmBtn.setVisibility(View.INVISIBLE);

            progressBar.setVisibility(View.VISIBLE);
            isFirstTimeUsingThisPage = false;

            //automatically jump to new activity
            confirmBtn.performClick();
        }

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

            if(isFirstTimeUsingThisPage) {
                nameOfTheTrainer = nameEditText.getText().toString();

                int selectedRadioButtonId = optionsGroup.getCheckedRadioButtonId();
                View selectedRadioButtonView = optionsGroup.findViewById(selectedRadioButtonId);
                selectedOptionIndex = optionsGroup.indexOfChild(selectedRadioButtonView);

//                RadioButton selectedRadioButton = (RadioButton) selectedRadioButtonView;
//                String radioBtnText = selectedRadioButton.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(trainerNameKey, nameOfTheTrainer);
                editor.putInt(selectedOptionIndexKey, selectedOptionIndex);
                editor.commit();

            }

            String welcomeMessage = String.format(
                    "你好, 訓練家%s 歡迎來到神奇寶貝的世界 你的第一個夥伴是%s",
                    nameOfTheTrainer,
                    pokemonNames[selectedOptionIndex]
            );

            infoText.setText(welcomeMessage);

            handler.postDelayed(jumpToNewActivityTask, 5 * 1000); //delay 3 seconds
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
        intent.putExtra(selectedPokemonIndexKey, selectedOptionIndex);

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
