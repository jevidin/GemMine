package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    int rows = 1;
    String game_dimension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        populateButtons();


    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.table_for_buttons);


    }



    public static Intent makeIntent(Context context){
        return new Intent(context, GameActivity.class);
    }
}