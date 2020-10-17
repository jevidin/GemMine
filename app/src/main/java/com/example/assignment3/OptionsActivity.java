package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setupGameSizeRadioButtons();
        setupNumberOfGemsRadioButtons();
    }

    private void setupNumberOfGemsRadioButtons() {
        RadioGroup group = findViewById(R.id.radio_group_gems);
        int[] number_of_gems_array = getResources().getIntArray(R.array.number_of_gems);
        for(int i = 0; i < number_of_gems_array.length; i++){
            final int selected_gem_amount = number_of_gems_array[i];
            RadioButton btn = new RadioButton(this);
            btn.setText("" + selected_gem_amount);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveGemAmount(selected_gem_amount);
                }
            });
            group.addView(btn);
            if(selected_gem_amount == getGemAmount(this)){
                btn.setChecked(true);
            }
        }
    }
    private static final String GEMS_PREF_NAME = "amount of gems";
    static private int getGemAmount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int defaultAmount = context.getResources().getInteger(R.integer.default_gems);
        return prefs.getInt(GEMS_PREF_NAME, defaultAmount);
    }

    private void saveGemAmount(int gemAmount) {
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(GEMS_PREF_NAME, gemAmount);
        editor.apply();
    }

    private void setupGameSizeRadioButtons() {
        RadioGroup group = findViewById(R.id.radio_group_game_size);
        String[] game_sizes_array = getResources().getStringArray(R.array.game_size);
        for (int i = 0; i < game_sizes_array.length; i++){
            final String selected_game_size = game_sizes_array[i];
            RadioButton btn = new RadioButton(this);
            btn.setText(selected_game_size);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(OptionsActivity.this, "you clicked: " + selected_game_size, Toast.LENGTH_SHORT).show();
                    saveGameSize(selected_game_size);
                }
            });
            group.addView(btn);
            if(selected_game_size.equals(getGameSize(this))){
                btn.setChecked(true);
            }
        }
    }

    private static final String PANEL_PREF_NAME = "Game Size";
    private static final String PREFS_NAME = "AppPrefs";
    private void saveGameSize(String selected_game_size) {
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PANEL_PREF_NAME, selected_game_size);
        editor.apply();
    }

    static public String getGameSize(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String defaultSize = context.getResources().getString(R.string.default_game_size);
        return prefs.getString(PANEL_PREF_NAME, defaultSize);
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, OptionsActivity.class);
    }
}