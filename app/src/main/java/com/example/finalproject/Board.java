package com.example.finalproject;

import java.util.ArrayList;

public class Board {

   private ArrayList<Square> boardSquares;

    public Board() {
    }

    public Board(ArrayList<Square> boardSquares) {
        this.boardSquares = boardSquares;
    }

    public ArrayList<Square> getBoardSquares() {
        return boardSquares;
    }

    public void setBoardSquares(ArrayList<Square> boardSquares) {
        this.boardSquares = boardSquares;
    }
}
