package com.example.sayac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    EditText upValue;
    EditText lowValue;
    Button upPlus, upMinus;
    Button lowPlus, lowMinus;
    CheckBox upVib, upSound;
    CheckBox lowVib, lowSound;

    Setup setupClass;

    int upLimit;
    int lowLimit;
    boolean up_vib;
    boolean up_sound;
    boolean low_vib;
    boolean low_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        upValue = (EditText) findViewById(R.id.up_value);
        lowValue = (EditText) findViewById(R.id.low_value);
        upPlus = (Button) findViewById(R.id.button_up_plus);
        upMinus = (Button) findViewById(R.id.button_up_minus);
        lowPlus = (Button) findViewById(R.id.button_low_plus);
        lowMinus = (Button) findViewById(R.id.button_low_minus);
        upVib = (CheckBox) findViewById(R.id.checkBox_up_vib);
        upSound = (CheckBox) findViewById(R.id.checkBox_up_sound);
        lowVib = (CheckBox) findViewById(R.id.checkBox_low_vib);
        lowSound = (CheckBox) findViewById(R.id.checkBox_low_sound);

        upValue.setText(String.valueOf(upLimit));
        lowValue.setText(String.valueOf(lowLimit));

        setupClass = Setup.getInstance(getApplicationContext());

        upPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLimit++;
                upValue.setText(String.valueOf(upLimit));
            }
        });

        upMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLimit--;
                upValue.setText(String.valueOf(upLimit));
            }
        });

        lowPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowLimit++;
                lowValue.setText(String.valueOf(lowLimit));
            }
        });

        lowMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowLimit--;
                lowValue.setText(String.valueOf(lowLimit));
            }
        });

        upValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                upLimit = Integer.valueOf(upValue.getText().toString());
            }
        });

        lowValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                lowLimit = Integer.valueOf(lowValue.getText().toString());
            }
        });

        upVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                up_vib = b;
            }
        });

        upSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                up_sound = b;
            }
        });

        lowVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                low_vib = b;
            }
        });

        lowSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                low_sound = b;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSettingsPreferences();
        upValue.setText(String.valueOf(upLimit));
        lowValue.setText(String.valueOf(lowLimit));
        upVib.setChecked(up_vib);
        upSound.setChecked(up_sound);
        lowVib.setChecked(low_vib);
        lowSound.setChecked(low_sound);
    }

    private void getSettingsPreferences() {
        upLimit = setupClass.upperLimit;
        lowLimit = setupClass.lowerLimit;
        up_vib = setupClass.upperVib;
        up_sound = setupClass.upperSound;
        low_vib = setupClass.lowerVib;
        low_sound = setupClass.lowerSound;
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupClass.setSettingsValues(upLimit, lowLimit, up_vib, up_sound, low_vib, low_sound);
        setupClass.saveValues();
    }
}