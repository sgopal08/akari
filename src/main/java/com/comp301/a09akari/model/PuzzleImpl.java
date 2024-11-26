package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
    int [][] board;

    public PuzzleImpl(int[][] board) {
        if(board == null){
            throw new IllegalArgumentException();
        }

        this.board = board;
    }

    @Override
    public int getWidth(){
        return board[0].length;
    }

    @Override
    public int getHeight(){
        return board.length;
    }

    @Override
    public CellType getCellType(int r, int c){
        if(r > board[0].length || c > board.length){
            throw new IndexOutOfBoundsException("Value is outside the bounds of board.");
        }
        if(r <= 4 || c <= 4){
            return CellType.CLUE;
        } else if(r == 5 || c == 5){
            return CellType.WALL;
        } else {
            return CellType.CORRIDOR;
        }
    }

    @Override
    public int getClue(int r, int c){
        if(r < board[0].length || c > board.length){
            throw new IndexOutOfBoundsException("Value is outside the bounds of board.");
        }

        if(getCellType(r,c) != CellType.CLUE){
            throw new IllegalArgumentException("Cell is not of type Clue.");
        }

       return board[r][c];
    }

}
