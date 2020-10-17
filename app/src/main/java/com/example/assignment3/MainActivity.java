package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPlayButton();
        setupOptionsButton();
        setupHelpButton();
    }

    private void setupOptionsButton() {
        Button btn = findViewById(R.id.btn_options);
        /*Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jackolantern1);
        Resources resource = getResources();
        btn.setBackground(new BitmapDrawable(resource, originalBitmap));*/
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OptionsActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }


    private void setupPlayButton(){
        Button btn = findViewById(R.id.btn_play);
        /*Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jackolantern1);
        Resources resource = getResources();
        btn.setBackground(new BitmapDrawable(resource, originalBitmap));*/
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupHelpButton(){
        Button btn = findViewById(R.id.btn_help);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }
}