package com.example.assignment3.model;

public class Rock {
    private boolean gem;//true if there's a gem inside
    private boolean found;
    private int nearbyGems;
    private boolean scanned;
    //private int nearbyFoundGems;

    public Rock() {
        this.gem = false;
        this.found = false;
        this.nearbyGems = 0;
        this.scanned = false;
    }

    //getters
    public int getNearbyGems() {
        return nearbyGems;
    }

//    public int getNearbyFoundGems() {
//        return nearbyFoundGems;
//    }

    public boolean isGem() {
        return gem;
    }

    public boolean isFound() {
        return found;
    }

    public boolean isScanned() {
        return scanned;
    }

    //mutators
    public void setGem() {
        this.gem = true;
    }

    public void addNearbyGems() {
        this.nearbyGems++;
    }

    public void decNearbyGems(){
        this.nearbyGems--;
    }

    public void nowFound(){
        this.found = true;
    }

    public void setScanned() {
        this.scanned = true;
    }
}
