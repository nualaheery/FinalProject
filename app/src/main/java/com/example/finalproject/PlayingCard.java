package com.example.finalproject;

public class PlayingCard {

    private String mCaption;
    private int mAmount;
    private String mColour;

    public PlayingCard(String caption, int amount) {
        mCaption = caption;
        mAmount = amount;
    }

    public PlayingCard(String caption, int amount, String colour) {
        mCaption = caption;
        mAmount = amount;
        mColour = colour;
    }



    public String getmCaption() { return mCaption;}

    public int getmAmount() { return mAmount;}

    public String getmColour() {return mColour;}


}

