package com.example.finalproject;

public class Square {

    private int squareNumber;
    private String squareColour;
    private int withdrawalAmount;

    public Square() {
    }

    public Square(int squareNumber, String squareColour) {
        this.squareNumber = squareNumber;
        this.squareColour = squareColour;
    }

    public Square(int squareNumber, String squareColour, int withdrawalAmount) {
        this.squareNumber = squareNumber;
        this.squareColour = squareColour;
        this.withdrawalAmount = withdrawalAmount;
    }

    public int getWithdrawalAmount() { return withdrawalAmount;}

    public void setWithdrawalAmount(int withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
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
