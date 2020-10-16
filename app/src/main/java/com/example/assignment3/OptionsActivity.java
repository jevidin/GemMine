package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
                }
            });
            group.addView(btn);
        }
    }
    public static Intent makeIntent(Context context){
        return new Intent(context, OptionsActivity.class);
    }
}