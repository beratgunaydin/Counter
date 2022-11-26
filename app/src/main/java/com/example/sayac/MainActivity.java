package com.example.sayac;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView counter_value;
    Button button_minus, button_plus, button_settings;

    int lowerLimit;
    int upperLimit;
    int currentValue;

    boolean upperVib;
    boolean upperSound;
    boolean lowerVib;
    boolean lowerSound;

    Setup setupClass;
    Context context;

    Vibrator vibrator = null;
    MediaPlayer player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter_value = (TextView) findViewById(R.id.textView_current_value);
        button_minus = (Button) findViewById(R.id.button_minus);
        button_plus = (Button) findViewById(R.id.button_plus);
        button_settings = (Button) findViewById(R.id.button_settings);

        context = getApplicationContext();
        setupClass = Setup.getInstance(context);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        player = MediaPlayer.create(context, R.raw.whistle);

        setupClass.loadValues();

        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValues(-1);
            }
        });

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValues(1);
            }
        });

        button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferences();
        counter_value.setText(String.valueOf(currentValue));
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupClass.setCurrentValue(currentValue);
        setupClass.saveValues();
    }

    private void getPreferences() {
        upperLimit = setupClass.upperLimit;
        lowerLimit = setupClass.lowerLimit;
        upperVib = setupClass.upperVib;
        lowerVib = setupClass.lowerVib;
        upperSound = setupClass.upperSound;
        lowerSound = setupClass.lowerSound;
        currentValue = setupClass.currentValue;
    }

    private void updateValues(int step) {
        if (step < 0) {
            if (currentValue + step < lowerLimit) {
                currentValue = lowerLimit;

                if(lowerSound) {
                    player.start();
                }

                if(lowerVib) {
                    if(vibrator.hasVibrator()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                            Toast.makeText(this, "Vibration", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            else {
                currentValue += step;
            }
        }
        else {
            if (currentValue + step > upperLimit) {
                currentValue = upperLimit;

                if (upperSound) {
                    player.start();
                }

                if(upperVib) {
                    if(vibrator.hasVibrator()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                            Toast.makeText(this, "Vibration", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            else {
                currentValue += step;
            }
        }
        counter_value.setText(String.valueOf(currentValue));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    updateValues(5);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    updateValues(-5);
                }
        }

        return super.dispatchKeyEvent(event);
    }
}