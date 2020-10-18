package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.model.GemMine;

public class GameActivity extends AppCompatActivity {

    private static int row_amount;
    private static int col_amount;
    private Button[][] buttons;
    private GemMine gemMine;
    private int scannedTotal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        row_amount = OptionsActivity.getRowCount(this);
        col_amount = OptionsActivity.getColCount(this);
        int gem_amount = OptionsActivity.getGemAmount(this);
        buttons = new Button[row_amount][col_amount];
        gemMine = new GemMine(row_amount, col_amount, gem_amount);
        //put in another method later
        TextView foundTV = (TextView) findViewById(R.id.txtFound);
        String found = "Found "+ gemMine.getGemsFound() + " of " + gemMine.getTotalGems() +
                " gems.";
        foundTV.setText(found);

        TextView scannedTV = (TextView) findViewById(R.id.txtScanned);
        String scanned = "# Scans used: "+ scannedTotal;
        scannedTV.setText(scanned);

        populateButtons();

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
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);
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
                        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.virus_green);
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
        int newWidth = btn.getWidth();
        int newHeight = btn.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jackolantern1);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        btn.setBackground(new BitmapDrawable(resource, scaledBitmap));

        //do stuff
        scanMine(row,col,btn);
        updateTxtUI();
    }



    private void scanMine(int row, int col, Button btn) {
        //check if it's a mine
        boolean check = gemMine.isGem(row, col);

        //if rock is not a gem, or alr found, show how many nearby
        if(!check || gemMine.isFound(row, col)){
            int nearby = gemMine.nearby(row, col);
            //display it
            btn.setText("" + nearby);
            gemMine.setScanned(row,col);
            scannedTotal++;

        }

        //if rock is a gem, increment gemsFound, update data
        else{
            gemMine.gemFound(row,col);

            //if gemsFound = total gems, end game
            if(gemMine.getTotalGems() == gemMine.getGemsFound()){
                Toast.makeText(this, "End game", Toast.LENGTH_SHORT).show();
            }
            //display found
            btn.setText("Mine");
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
        TextView tv = (TextView) findViewById(R.id.txtFound);
        String found = "Found "+ gemMine.getGemsFound() + " of " + gemMine.getTotalGems() +
                " gems.";
        tv.setText(found);

        //update # scanned
        tv = (TextView) findViewById(R.id.txtScanned);
        String scanned = "# Scans used: "+ scannedTotal;
        tv.setText(scanned);
    }

    private void lockButtonSizes() {
        for (int row = 0; row < row_amount; row++){
            for(int col = 0; col < col_amount; col++){
                Button btn = buttons[row][col];
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