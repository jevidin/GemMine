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

public class GameActivity extends AppCompatActivity {

    private static int row_amount;
    private static int col_amount;
    Button[][] buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        row_amount = OptionsActivity.getRowCount(this);
        col_amount = OptionsActivity.getColCount(this);
        buttons = new Button[row_amount][col_amount];

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
                btn.setText("" + col + "," + row);
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
        int newWidth = btn.getWidth();
        int newHeight = btn.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gem1);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        btn.setBackground(new BitmapDrawable(resource, scaledBitmap));

        btn.setText("" + col);
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