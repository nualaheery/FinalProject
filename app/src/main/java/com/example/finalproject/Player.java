package com.example.finalproject;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class Player {

    private static final int MAX_SQUARE_NUMBER = 24;

    private int positionOnBoard;
    private String pinNumber;
    private int debitAmount;
    private int creditAmount;
    private int cashAmount;
    private MonsterItem monster;
    private boolean pinBlocked;

    public Player() {
    }

    public Player(int positionOnBoard, String pinNumber, int debitAmount, int creditAmount, int cashAmount, MonsterItem monster, boolean pinBlocked) {
        this.positionOnBoard = positionOnBoard;
        this.pinNumber = pinNumber;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.cashAmount = cashAmount;
        this.monster = monster;
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

    public int getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(int debitAmount) {
        this.debitAmount = debitAmount;
    }

    public int getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(int creditAmount) {
        this.creditAmount = creditAmount;
    }

    public int getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(int cashAmount) {
        this.cashAmount = cashAmount;
    }

    public MonsterItem getMonster() {
        return monster;
    }

    public boolean isPinBlocked() {
        return pinBlocked;
    }

    public void setPinBlocked(boolean pinBlocked) {
        this.pinBlocked = pinBlocked;
    }

    public void setMonsterItem () {
        this.monster = monster;
    }


    public int movePosition(Context context, int diceValue, ArrayList<Square> squares) {

        if ((this.positionOnBoard + diceValue) > MAX_SQUARE_NUMBER) {
            Toast.makeText(context,"You finished the board",Toast.LENGTH_SHORT).show();
        } else {
            int newPosition = this.positionOnBoard += diceValue ;
           // Toast.makeText(context,"choose a " +squares.get(this.positionOnBoard -1).getSquareColour() +"card",Toast.LENGTH_SHORT).show();

        }

        return this.positionOnBoard;
    }
}
