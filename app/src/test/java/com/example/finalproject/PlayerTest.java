package com.example.finalproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    //test data
    private int debitAmount;
    private String pinNumber;
    private Player player;

    @Before
    public void setUp() {

    debitAmount = 100;
    pinNumber = "1234";
    player = new Player();
    }

    @Test
    public void setPinNumber() {
        player.setPinNumber(pinNumber);

        assertEquals(pinNumber,player.getPinNumber());

    }

    @Test
    public void setDebitAmount() {
        player.setDebitAmount(debitAmount);

        assertEquals(debitAmount,player.getDebitAmount());
    }
}