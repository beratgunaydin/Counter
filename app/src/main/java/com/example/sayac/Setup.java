package com.example.sayac;

import android.content.Context;
import android.content.SharedPreferences;

public class Setup {
    int upperLimit;
    int lowerLimit;
    int currentValue;
    boolean upperVib;
    boolean upperSound;
    boolean lowerVib;
    boolean lowerSound;

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Names name = new Names();

    static Setup setup = null;

    private Setup(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("setup", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static Setup getInstance(Context context) {
        if (setup == null) {
            setup = new Setup(context);
        }
        return setup;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public void setSettingsValues(int upperLimit, int lowerLimit, boolean upperVib, boolean upperSound, boolean lowerVib, boolean lowerSound) {
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.upperVib = upperVib;
        this.upperSound = upperSound;
        this.lowerVib = lowerVib;
        this.lowerSound = lowerSound;
    }

    public void saveValues() {
        editor.putInt(name.upper_limit, upperLimit);
        editor.putInt(name.lower_limit, lowerLimit);
        editor.putInt(name.current_value, currentValue);
        editor.putBoolean(name.upper_vib, upperVib);
        editor.putBoolean(name.upper_sound, upperSound);
        editor.putBoolean(name.lower_vib, lowerVib);
        editor.putBoolean(name.lower_sound, lowerSound);
        editor.commit();
    }

    public void loadValues() {
        upperLimit = sharedPreferences.getInt(name.upper_limit, 20);
        lowerLimit = sharedPreferences.getInt(name.lower_limit, 0);
        currentValue = sharedPreferences.getInt(name.current_value, 0);
        upperVib = sharedPreferences.getBoolean(name.upper_vib, false);
        upperSound = sharedPreferences.getBoolean(name.upper_sound, false);
        lowerVib = sharedPreferences.getBoolean(name.lower_vib, false);
        lowerSound = sharedPreferences.getBoolean(name.lower_sound, false);
    }
}
