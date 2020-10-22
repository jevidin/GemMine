package com.example.assignment3.model;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.assignment3.OptionsActivity;
import com.example.assignment3.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Options {
    private static final String PREFS_NAME = "AppPrefs";
    //private static final String SCORES_PREFS_NAME = "highscores";
    public static Options instance;
    private int row_count;
    private int col_count;
    private int gem_amount;
    private int[][] scores;
    private int sizeIndex;
    private int gemsIndex;

    public static Options getInstance(Context context){
        if(instance == null){
            instance = new Options(context);
        }
        return instance;
    }
    private Options(Context context){
        row_count = OptionsActivity.getRowCountSharedPref(context);
        col_count = OptionsActivity.getColCountSharedPref(context);
        gem_amount = OptionsActivity.getGemAmountSharedPref(context);
        scores = new int[3][4];
        for(int i = 0; i < 3; i++){
            for(int k = 0; k < 4; k++){
                scores[i][k] = getScoreFromSharedPrefs(context, i, k);
            }
        }

        sizeIndex = row_count -4;
        gemsIndex = gem_amount/5 -1;
    }
    public void setGems(int gems){
        gem_amount = gems;
        gemsIndex = gem_amount/5 -1;
    }
    public int getGems(){
        return gem_amount;
    }
    public void setCols(int cols){
        col_count = cols;
    }
    public int getCols(){
        return col_count;
    }
    public void setRows(int rows){
        row_count = rows;
        sizeIndex = row_count -4;
    }
    public int getRows(){
        return row_count;
    }

    public int getScore(){
        return scores[sizeIndex][gemsIndex];
    }

    public void setScore(int score, Context context){//also saves score
        if(scores[sizeIndex][gemsIndex] == 0 || score < scores[sizeIndex][gemsIndex]){
            scores[sizeIndex][gemsIndex] = score;
        }
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(""+sizeIndex+gemsIndex, scores[sizeIndex][gemsIndex]);
        editor.apply();

    }

    public int getScoreFromSharedPrefs(Context context, int row, int col){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String prefName = "" + row + col;
        return prefs.getInt(prefName, 0);
    }





//    private void storeLensToSharedPref() {
//        SharedPreferences prefs = getSharedPreferences(SHAREDPREF_LENSES, MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        Gson gson = new Gson();
//        List<Lens> storedLens = new ArrayList<>();
//
//        if(lensManager.getNumLenses() == 0){
//            storedLens.add(new Lens("Canon", 1.8, 50));
//            storedLens.add(new Lens("Tamron", 2.8, 90));
//            storedLens.add(new Lens("Sigma", 2.8, 200));
//            storedLens.add(new Lens("Nikon", 4, 200));
//        }
//
//        //turn manager into an arraylist
//        for(int i = 0; i < lensManager.getNumLenses();i++){
//            storedLens.add(lensManager.get(i));
//        }
//
//        String json = gson.toJson(storedLens);
//        editor.putString(SHAREDPREF_LENS_MANAGER, json);
//        editor.apply();
//    }

}
