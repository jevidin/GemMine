package com.example.assignment3.model;


import android.content.Context;

import com.example.assignment3.OptionsActivity;

public class Options {
    public static Options instance;
    private int row_count;
    private int col_count;
    private int gem_amount;

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
    }
    public void setGems(int gems){
        gem_amount = gems;
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
    }
    public int getRows(){
        return row_count;
    }
}
