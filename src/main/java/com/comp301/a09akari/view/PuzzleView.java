package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;

public class PuzzleView implements FXComponent {
  private Model model;
  private ClassicMvcController controller;
  private Image lightbulb;
  private ImagePattern lightbulbPattern;
  private Image illegalBulb;
  private ImagePattern illegalBulbPattern;

  public PuzzleView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
    loadImage();
  }

  private void loadImage() {
    lightbulb = new Image("light-bulb.png");
    lightbulbPattern = new ImagePattern(lightbulb);
    illegalBulb = new Image("red_light.png");
    illegalBulbPattern = new ImagePattern(illegalBulb);
  }

  // displays clues and game board
  @Override
  public Parent render() {
    StackPane root = new StackPane();
    root.getStyleClass().add("background");
    GridPane board = new GridPane();
    board.setHgap(10);
    board.setVgap(10);

    int rows = model.getActivePuzzle().getHeight();
    int cols = model.getActivePuzzle().getWidth();

    double maxWidth = 500;
    double maxHeight = 500;
    double cellSize = Math.min(maxWidth / cols, maxHeight / rows) - 10; // gaps?

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        switch (model.getActivePuzzle().getCellType(r, c)) {
          case CLUE:
            displayClue(board, r, c, (int) cellSize);
            break;
          case WALL:
            displayWall(board, r, c, (int) cellSize);
            break;
          case CORRIDOR:
            displayCorridor(board, r, c, (int) cellSize);
            break;
        }
      }
    }

    root.getChildren().add(board);
    return root;
  }

  private void displayClue(GridPane board, int r, int c, int size) {
    StackPane pane = new StackPane();
    Rectangle cell = new Rectangle(size, size);
    Text text = new Text("" + model.getActivePuzzle().getClue(r, c));
    text.setFill(Color.WHITE);
    cell.setFill(Color.HOTPINK);
    text.getStyleClass().add("puzzle-number");
    if (model.isClueSatisfied(r, c)) {
      cell.setFill(Color.SPRINGGREEN);
    }

    pane.getChildren().addAll(cell, text);
    board.add(pane, c, r);
  }

  private void displayWall(GridPane board, int r, int c, int size) {
    Rectangle cell = new Rectangle(size, size);
    cell.setFill(Color.ORANGE);
    board.add(cell, c, r);
  }

  private void displayCorridor(GridPane board, int r, int c, int size) {
    StackPane pane = new StackPane();
    Rectangle cell = new Rectangle(size, size);
    cell.setFill(Color.WHITE);
    // if lamp and is illegal: (insert red lamp)
    if (model.isLamp(r, c) && model.isLampIllegal(r, c)) {
      cell.setFill(illegalBulbPattern);
      // if there is a lamp:
    } else if (model.isLamp(r, c)) {
      cell.setFill(lightbulbPattern);
      // cell is lit:
    } else if (model.isLit(r, c)) {
      cell.setFill(Color.YELLOW);
    }

    cell.setOnMouseClicked(
        mouseEvent -> {
          changeLamp(cell, r, c);
        });
    board.add(cell, c, r);
  }

  private void changeLamp(Rectangle cell, int r, int c) {
    if (!model.isLamp(r, c)) {
      model.addLamp(r, c);
    } else {
      model.removeLamp(r, c);
      cell.setFill(Color.WHITE);
    }
  }
}
