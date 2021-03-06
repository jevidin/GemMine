package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.assignment3.model.Options;

import java.util.ArrayList;

public class OptionsActivity extends AppCompatActivity {

    public static Options optionsInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setupGameSizeRadioButtons();
        setupNumberOfGemsRadioButtons();
        optionsInstance = Options.getInstance(this);
    }

    private void setupNumberOfGemsRadioButtons() {
        RadioGroup group = findViewById(R.id.radio_group_gems);
        int[] number_of_gems_array = getResources().getIntArray(R.array.number_of_gems);
        for(int i = 0; i < number_of_gems_array.length; i++){
            final int index = i;
            final int selected_gem_amount = number_of_gems_array[i];
            RadioButton btn = new RadioButton(this);
            btn.setText("" + selected_gem_amount);
            btn.setTextColor(Color.parseColor("#BD4104"));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveGemAmount(selected_gem_amount);
                    //optionsInstance.setScoresIndex(index);
                }
            });
            group.addView(btn);
            if(selected_gem_amount == getGemAmountSharedPref(this)){
                btn.setChecked(true);
            }
        }
    }
    private static final String GEMS_PREF_NAME = "amount of gems";
    static public int getGemAmountSharedPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int defaultAmount = context.getResources().getInteger(R.integer.default_gems);
        return prefs.getInt(GEMS_PREF_NAME, defaultAmount);
    }

    private void saveGemAmount(int gemAmount) {
        optionsInstance.setGems(gemAmount);
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
            btn.setTextColor(Color.parseColor("#BD4104"));
            btn.setText(selected_game_size);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveGameSize(selected_game_size);
                }
            });
            group.addView(btn);

            String gameSize = getRowCountSharedPref(this) + " x " + getColCountSharedPref(this);
            if(selected_game_size.equals(gameSize)){
                btn.setChecked(true);
            }
        }
    }


    private static final String ROW_PREF_NAME = "Row Amount";
    private static final String COL_PREF_NAME = "Column Amount";
    private static final String PREFS_NAME = "AppPrefs";
    private void saveGameSize(String selected_game_size) {
        int row_count;
        int col_count;
        switch(selected_game_size){
            case "5 x 10":
                row_count = 5;
                col_count = 10;
                break;
            case "6 x 15":
                row_count = 6;
                col_count = 15;
                break;
            default:
                row_count = 4;
                col_count = 6;
                break;
        }
        optionsInstance.setRows(row_count);
        optionsInstance.setCols(col_count);
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ROW_PREF_NAME, row_count);
        editor.putInt(COL_PREF_NAME, col_count);
        editor.apply();
    }

    static public int getRowCountSharedPref(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int defaultRows = context.getResources().getInteger(R.integer.default_rows);

        return prefs.getInt(ROW_PREF_NAME, defaultRows);
    }
    static public int getColCountSharedPref(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int defaultCols = context.getResources().getInteger(R.integer.default_cols);

        return prefs.getInt(COL_PREF_NAME, defaultCols);
    }


    public static Intent makeIntent(Context context){
        return new Intent(context, OptionsActivity.class);
    }
}