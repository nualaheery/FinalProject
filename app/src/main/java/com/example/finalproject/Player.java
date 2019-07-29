package com.example.finalproject;

public class Player {

    private static final int MAX_SQUARE_NUMBER = 24;

    private int positionOnBoard;
    private String pinNumber;
    private double debitAmount;
    private double creditAmount;
    private double cashAmount;
    private boolean pinBlocked;


    public Player() {

    }

    public Player(int positionOnBoard, String pinNumber, int debitAmount, int creditAmount, int cashAmount, boolean pinBlocked) {
        this.positionOnBoard = positionOnBoard;
        this.pinNumber = pinNumber;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.cashAmount = cashAmount;
        this.pinBlocked = pinBlocked;


    }

    public int getPositionOnBoard() {
        return positionOnBoard;
    }

    public void setPositionOnBoard(int positionOnBoard) {
        this.positionOnBoard = positionOnBoard;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public boolean isPinBlocked() {
        return pinBlocked;
    }

    public void setPinBlocked(boolean pinBlocked) {
        this.pinBlocked = pinBlocked;
    }



}
