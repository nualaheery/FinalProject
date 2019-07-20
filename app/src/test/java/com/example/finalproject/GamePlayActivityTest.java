package com.example.finalproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GamePlayActivityTest {

    private double amountOwed, totalCashAmount, totalDebitAmount, amountToTransfer, amountToAdd;
    private Player player;
    private double delta = 1;

    @Before
    public void setUp() {

        amountOwed = 10;
        totalCashAmount = 100;
        totalDebitAmount = 100;
        amountToTransfer = 20;
        amountToAdd = 5;
        player = new Player();
    }

    @Test
    public void payWithCashTest() {
        double expected = totalCashAmount - amountOwed;
        player.setCashAmount(totalCashAmount - amountOwed);


        assertEquals(expected,player.getCashAmount(), delta);
    }

    @Test
    public void addMoneyToAccountTest() {
        double expected = totalCashAmount + amountToAdd;
        player.setCashAmount(totalCashAmount + amountToAdd);

        assertEquals(expected, player.getCashAmount(),delta);


    }

    @Test
    public void withdrawCashTest() {
        player.setDebitAmount(totalDebitAmount - amountToTransfer);
        player.setCashAmount(totalCashAmount + amountToTransfer);


        double expectedDebitRemaining = (totalDebitAmount - amountToTransfer);
        assertEquals(expectedDebitRemaining,player.getDebitAmount(),delta);

        double expectedCashRemaining = (totalCashAmount + amountToTransfer);
        assertEquals(expectedCashRemaining, player.getCashAmount(),delta);
    }


}