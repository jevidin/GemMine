package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.model.GemMine;
import com.example.assignment3.model.Options;

public class GameActivity extends AppCompatActivity {

    private static int row_amount;
    private static int col_amount;
    private Button[][] buttons;
    private GemMine gemMine;
    private int scannedTotal = 0;
    private Options optionsInstance;
    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        optionsInstance = Options.getInstance(this);
        row_amount = optionsInstance.getRows();
        col_amount = optionsInstance.getCols();
        int gem_amount = optionsInstance.getGems();
        highScore = optionsInstance.getScore();
        buttons = new Button[row_amount][col_amount];
        gemMine = new GemMine(row_amount, col_amount, gem_amount);
        displayText();

        populateButtons();

    }

    private void displayText() {
        TextView foundTV = findViewById(R.id.txtFound);
        String found = "Found "+ gemMine.getGemsFound() + " of " + gemMine.getTotalGems() +
                " gems.";
        foundTV.setText(found);
        //highScore = optionsInstance.getScore();
        TextView scannedTV = findViewById(R.id.txtScanned);
        String scanned = "Scans: "+ scannedTotal + " Best Score: " + highScore;
        scannedTV.setText(scanned);
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.table_for_buttons);
        for(int row = 0; row < row_amount; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);
            for(int col = 0; col < col_amount; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                final Button btn = new Button(this);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                        1.0f);
                btn.setLayoutParams(layoutParams);
                btn.setPadding(0, 0, 0, 0);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                });
                // code to add the background after button sizes are determined is partially taken from: https://stackoverflow.com/questions/3591784/views-getwidth-and-getheight-returns-0
                btn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        btn.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int newWidth = btn.getHeight();
                        int newHeight = btn.getHeight();
                        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rock2shrinked);
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                        Resources resource = getResources();
                        btn.setBackground(new BitmapDrawable(resource, scaledBitmap));
                    }
                });
                tableRow.addView(btn);
                buttons[row][col] = btn;
            }
        }

    }


    private void gridButtonClicked(int col, int row) {
        Button btn = buttons[row][col];

        //lock button sizes:
        lockButtonSizes();
        //do stuff
        scanMine(row,col,btn);
        updateTxtUI();
    }



    private void scanMine(int row, int col, Button btn) {
        //check if it's a mine
        boolean check = gemMine.isGem(row, col);

        if(gemMine.isScanned(row, col)){
            return;
        }
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //if rock is not a gem, or already found, show how many nearby
        if(!check || gemMine.isFound(row, col)){

            v.vibrate(100);
            int nearby = gemMine.nearby(row, col);
            //display it
            btn.setText("" + nearby);
            gemMine.setScanned(row,col);
            scannedTotal++;

        }

        //if rock is a gem, increment gemsFound, update data
        else{
            //code for playing sound effect taken from https://www.youtube.com/watch?v=9oj4f8721LM
            MediaPlayer media = MediaPlayer.create(this, R.raw.minecraft_exp_sound);
            media.start();

            //code for vibrating with a pattern is from https://www.youtube.com/watch?v=yDIbV-1cov4
            long[] pattern = {0, 150, 50, 150};
            v.vibrate(pattern, -1);

            gemMine.gemFound(row,col);
            int newWidth = btn.getWidth();
            int newHeight = btn.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gem1);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            btn.setBackground(new BitmapDrawable(resource, scaledBitmap));

            //if gemsFound = total gems, end game
            if(gemMine.getTotalGems() == gemMine.getGemsFound()){
                optionsInstance.setScore(scannedTotal, this);
                highScore = optionsInstance.getScore();
                // code for fullscreen dialog partially taken from https://www.youtube.com/watch?v=ImV6y2K76qE
                View view = getLayoutInflater().inflate(R.layout.congratulations_layout, null);
                final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                dialog.setContentView(view);

                String scoreText = getString(R.string.scans_used);
                scoreText += scannedTotal;
                TextView scoreTV = view.findViewById(R.id.txt_score);
                scoreTV.setText(scoreText);

                String highscoreText = getString(R.string.highscore);
                highscoreText += highScore;
                TextView highscoreTV = view.findViewById(R.id.txt_highscore);
                highscoreTV.setText(highscoreText);

                dialog.show();
                Button btn2 = view.findViewById(R.id.btn_back);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        dialog.dismiss();

                    }
                });

            }

            //update ui to decrease
            updateMineUI();
        }
    }
    private void updateMineUI() {
        Button btn;
        for(int row = 0; row < row_amount;row++){
            for(int col = 0; col < col_amount;col++){
                if(gemMine.isScanned(row,col)){
                    int nearby = gemMine.nearby(row, col);
                    btn = buttons[row][col];
                    btn.setText(""+ nearby);

                }
            }
        }

    }

    private void updateTxtUI() {
        //update gems found
        TextView tv = findViewById(R.id.txtFound);
        String found = "Found "+ gemMine.getGemsFound() + " of " + gemMine.getTotalGems() +
                " gems.";
        tv.setText(found);

        //update # scanned
        tv = findViewById(R.id.txtScanned);
        String scanned = "Scans: "+ scannedTotal + " Best Score: " + highScore;
        tv.setText(scanned);
    }

    private void lockButtonSizes() {
        for (int row = 0; row < row_amount; row++){
            for(int col = 0; col < col_amount; col++){
                Button btn = buttons[row][col];
                btn.setTextSize(20);
                btn.setTextColor(Color.parseColor("#a33a07"));

                int width = btn.getWidth();
                btn.setMinWidth(width);
                btn.setMaxWidth(width);

                int height = btn.getHeight();
                btn.setMinHeight(height);
                btn.setMaxHeight(height);
            }
        }
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, GameActivity.class);
    }
}