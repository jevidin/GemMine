package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 6;
    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
    String game_dimension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        populateButtons();


    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.table_for_buttons);
        for(int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);
            for(int col = 0; col < NUM_COLS; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button btn = new Button(this);
                btn.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                btn.setText("" + col + "," + row);
                btn.setPadding(0, 0, 0, 0);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
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

        btn.setText("" + col);
    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLS; col++){
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