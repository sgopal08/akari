package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private PuzzleLibrary puzzles;
  private int index;
  private int[][] lamps; // 1 is lit lamp; 0 is not lit
  List<ModelObserver> activeObservers;
  private Puzzle activePuzzle;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    }
    this.puzzles = puzzles;
    this.index = 0;
    this.activePuzzle =
        library.getPuzzle(index); // set active puzzle to first one in puzzle library
    this.activeObservers = new ArrayList<>();
    this.lamps =
        new int[activePuzzle.getHeight()][activePuzzle.getWidth()]; // num of rows & num of columns
      this.resetPuzzle();
  }

  @Override
  public void addLamp(int r, int c) {
    checkBounds(r, c, true);
    if (!isLamp(r, c)) {
      lamps[r][c] = 1;
      notifyObservers();
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    checkBounds(r, c, true);
    if (isLamp(r, c)) {
      lamps[r][c] = 0;
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    checkBounds(r, c, true);
    if (isLamp(r, c)) {
      return true;
    }
    // row left-side
    for (int i = c - 1; i >= 0; i--) {
      if (puzzles.getPuzzle(index).getCellType(r, i) != CellType.CORRIDOR) break;
      if (isLamp(r, i)) return true;
    }
    // row right-side
    for (int i = c + 1; i < puzzles.getPuzzle(index).getWidth(); i++) {
      if (puzzles.getPuzzle(index).getCellType(r, i) != CellType.CORRIDOR) break;
      if (isLamp(r, i)) return true;
    }
    // column up
    for (int i = r - 1; i >= 0; i--) {
      if (puzzles.getPuzzle(index).getCellType(i, c) != CellType.CORRIDOR) break;
      if (isLamp(i, c)) return true;
    }
    // column down
    for (int i = r + 1; i < puzzles.getPuzzle(index).getHeight(); i++) {
      if (puzzles.getPuzzle(index).getCellType(i, c) != CellType.CORRIDOR) break;
      if (isLamp(i, c)) return true;
    }

    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    checkBounds(r, c, true);
    return lamps[r][c] == 1;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    checkBounds(r, c, false);
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException("There is no lamp at this cell");
    }
    int width = activePuzzle.getWidth();
    int height = activePuzzle.getHeight();
    CellType currentCell = activePuzzle.getCellType(r, c);

    // horizontal left-side
    for (int i = c - 1; i >= 0; i--) {
      if (currentCell != CellType.CORRIDOR) break;
      if (isLamp(r, c)) {
        return true;
      }
    }
    // vertical right-side
    for (int i = c + 1; i < width; i++) {
      if (currentCell != CellType.CORRIDOR) break;
      if (isLamp(r, c)) {
        return true;
      }
    }

    // up
    for (int i = r - 1; i >= 0; i--) {
      if (currentCell != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r, c)) {
        return true;
      }
    }

    // down
    for (int i = r + 1; i < height; i++) {
      if (currentCell != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r, c)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzles.getPuzzle(index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.index;
  }

  @Override
  public int getPuzzleLibrarySize(){
      return puzzles.size();
  }

    @Override
    public void resetPuzzle() {
      lamps = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
      notifyObservers();
    }

    @Override
  public void setActivePuzzleIndex(int setIndex) {
    if (index < 0 || index >= puzzles.size()) {
      throw new IndexOutOfBoundsException("Active puzzle not in puzzle library");
    }
    this.index = setIndex;
    activePuzzle = puzzles.getPuzzle(index);
    resetPuzzle();
  }

  @Override
  public boolean isSolved(){
      //every clue satisfied: if clue is satisfied()
      // every corridor is illuminated: FALSE if corridor is not lit, has illegal lamp, or has a lamp
      for(int r = 0; r < getActivePuzzle().getWidth(); r++){
          for(int c = 0; c < getActivePuzzle().getHeight(); c++){
              CellType currentCell = getActivePuzzle().getCellType(r,c);
              switch (currentCell){
                  case CORRIDOR:
                      if(!isLit(r,c) || isLampIllegal(r,c) || isLamp(r,c)) return false;
                      break;
                  case CLUE:
                      if(!isClueSatisfied(r,c)) return false;
                      break;
              }
          }
      }
      return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c){
      checkBounds(r,c,false);
      if(puzzles.getPuzzle(index).getCellType(r,c) != CellType.CLUE){
          throw new IllegalArgumentException("Cell is of not type Clue");
      }

      int lampsNeeded = puzzles.getPuzzle(index).getClue(r,c);
      int lampCount = 0;

      //up
      if(r > 0 && lamps[r-1][c] == 1){
          lampCount++;
      }

      //down
      if(r < getActivePuzzle().getHeight() - 1 && lamps[r+1][c] == 1){
          lampCount++;
      }

      //left
      if(c > 0 && lamps[r][c-1] == 1){
          lampCount++;
      }

      //right
      if(c < getActivePuzzle().getWidth() - 1 && lamps[r][c+1] == 1){
          lampCount++;
      }

      return lampCount == lampsNeeded;
  }

  @Override
  public void addObserver(ModelObserver observer){
      activeObservers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer){
      activeObservers.remove(observer);
  }


  public void checkBounds(int r, int c, boolean corridor) {
    if (r > puzzles.getPuzzle(index).getHeight()
        || c > puzzles.getPuzzle(index).getWidth()
        || r < 0
        || c < 0) { // maybe make >=
      throw new IndexOutOfBoundsException("Value is of puzzle bounds");
    }

    if (corridor) {
      if (puzzles.getPuzzle(index).getCellType(r, c) != CellType.CORRIDOR) {
        throw new IllegalArgumentException("Cell must be type Corridor");
      }
    }
  }

  public void notifyObservers() {
    for (ModelObserver a : activeObservers) {
      a.update(this);
    }
  }
}
