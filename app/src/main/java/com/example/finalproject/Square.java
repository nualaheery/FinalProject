package com.example.finalproject;

public class Square {

    private int squareNumber;
    private String squareColour;

    public Square() {
    }

    public Square(int squareNumber, String squareColour) {
        this.squareNumber = squareNumber;
        this.squareColour = squareColour;
    }

    public int getSquareNumber() {
        return squareNumber;
    }

    public void setSquareNumber(int squareNumber) {
        this.squareNumber = squareNumber;
    }

    public String getSquareColour() {
        return squareColour;
    }

    public void setSquareColour(String squareColour) {
        this.squareColour = squareColour;
    }
}
