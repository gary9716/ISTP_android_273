package com.example.user.myandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String[] pokemonNames = {"小火龍","傑尼龜","妙蛙種子"};
    TextView infoText;
    EditText nameEditText;
    RadioGroup optionsGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        //find UIs by their ids
        infoText = (TextView)findViewById(R.id.infoText);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        optionsGroup = (RadioGroup)findViewById(R.id.optionsGroup);
        Button confirmBtn = (Button)findViewById(R.id.confirmButton);
        confirmBtn.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.confirmButton) {
            //confirmButton was clicked
            Log.d("buttonTest","confirm-button was clicked");
            String name = nameEditText.getText().toString();

            int selectedRadioButtonId = optionsGroup.getCheckedRadioButtonId();
            View selectedRadioButtonView = optionsGroup.findViewById(selectedRadioButtonId);
            int selectedIndex = optionsGroup.indexOfChild(selectedRadioButtonView);

            RadioButton selectedRadioButton = (RadioButton)selectedRadioButtonView;
            String radioBtnText = selectedRadioButton.getText().toString();

            String welcomeMessage = String.format(
                    "你好, 訓練家%s 歡迎來到神奇寶貝的世界 你的第一個夥伴是%s",
                    name,
                    radioBtnText
            );

            infoText.setText(welcomeMessage);
        }
    }
}
