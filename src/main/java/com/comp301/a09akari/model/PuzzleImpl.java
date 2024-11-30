package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
    int [][] board;

    public PuzzleImpl(int[][] board) {
        this.board = board;
    }

    @Override
    public int getWidth() {
        if (board != null && board.length > 0) {
            return board[0].length;
        } else {
            return 0;
        }
    }

    @Override
    public int getHeight() {
        if (board != null) {
            return board.length;
        } else {
            return 0;
        }
    }

    @Override
    public CellType getCellType(int r, int c){
        if(r > board.length || c > board[0].length){
            throw new IndexOutOfBoundsException("Value is outside the bounds of board.");
        }

        int value = board[r][c];
        if(value <= 4){
            return CellType.CLUE;
        } else if(value == 5){
            return CellType.WALL;
        } else {
            return CellType.CORRIDOR;
        }
    }

    @Override
    public int getClue(int r, int c){
        if(r > board.length || c > board[0].length){
            throw new IndexOutOfBoundsException("Value is outside the bounds of board.");
        }

        if(getCellType(r,c) != CellType.CLUE){
            throw new IllegalArgumentException("Cell is not of type Clue.");
        }

       return board[r][c];
    }

}
