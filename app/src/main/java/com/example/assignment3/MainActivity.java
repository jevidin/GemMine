package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayWelcome();
        setContentView(R.layout.activity_main);
        //testAnim();
        setupPlayButton();
        setupOptionsButton();
        setupHelpButton();
    }

    private void testAnim() {
        ImageView test = (ImageView)findViewById(R.id.imageView);
        Animation bounceTitle = AnimationUtils.loadAnimation(MainActivity.this, R.anim.blink_anim);
        test.startAnimation(bounceTitle);
    }

    private void displayWelcome() {
        // code for fullscreen dialog partially taken from https://www.youtube.com/watch?v=ImV6y2K76qE
        View view = getLayoutInflater().inflate(R.layout.welcome_layout, null);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view);
        ImageView title = view.findViewById(R.id.imgTitle);

        ConstraintLayout cLayout;
        cLayout = view.findViewById(R.id.cLayout);

        dialog.show();
        //xml files from animation is taken from https://www.youtube.com/watch?v=fqU4zc_XeX0&ab_channel=InsideAndroid
        //with minor changes
        Animation bounce = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
        title.startAnimation(bounce);

        ImageView minecart = view.findViewById(R.id.imgMinecart);
        Animation slide = AnimationUtils.loadAnimation(MainActivity.this, R.anim.righttoleft);
        minecart.startAnimation(slide);

        TextView skip1 = view.findViewById(R.id.txtSkip);
        ImageView skip2 = view.findViewById(R.id.imgSkip);
        Animation fade = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
        skip1.startAnimation(fade);
        skip2.startAnimation(fade);

        TextView author1 = view.findViewById(R.id.txtAuthor);
        ImageView author2 = view.findViewById(R.id.imgAuthor);
        author1.startAnimation(bounce);
        author2.startAnimation(bounce);

        cLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        /*
        Button btn2 = view.findViewById(R.id.btn_back);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();

            }
        });*/
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