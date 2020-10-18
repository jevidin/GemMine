package com.example.assignment3.model;

import java.util.Random;

public class GemMine {
    private Rock gemMine[][];
    private int totalRows;
    private int totalCols;

    public GemMine(int rows, int cols, int gems) {
        gemMine = new Rock[rows][cols];
        totalRows = rows;
        totalCols = cols;

        //initiate
        for(int r = 0; r < rows; r++){
            for(int c = 0;c< cols;c++){
                gemMine[r][c]= new Rock();
            }
        }

        //add gems in
        generateGems(gems);
    }

    private void generateGems(int gems){
        for(int i = 0; i < gems; i++){
            addGem();
        }
    }

    private void addGem(){
        //generate random numbers to pick gems (check if alr there first)
        boolean alr = false;
        Random r = new Random();
        int selectedRow = r.nextInt(totalRows);
        int selectedCol = r.nextInt(totalCols);
        while(!alr){
            if(gemMine[selectedRow][selectedCol].isGem()){
                selectedRow = r.nextInt(totalRows);
                selectedCol = r.nextInt(totalCols);
            }
            else{
                gemMine[selectedRow][selectedCol].setGem();
                alr = true;
            }
        }

        //increment nearbyGem for every rock in the same row
        for(int i = 0; i < totalCols; i++){
            gemMine[selectedRow][i].addNearbyGems();
        }

        //increment nearbyGem for every rock in the same col
        for(int i = 0; i < totalRows; i++){
            gemMine[i][selectedCol].addNearbyGems();
        }
    }
}
