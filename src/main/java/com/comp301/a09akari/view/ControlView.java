package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ControlView implements FXComponent{
    private Model model;
    private ClassicMvcController controller;

    public ControlView(Model model, ClassicMvcController controller){
        this.model = model;
        this.controller = controller;
    }

    @Override
    public Parent render() {

        HBox pane = new HBox(5);

        pane.getChildren().clear();
        pane.getChildren().add(renderButtons());
        Label puzzleNum =
                new Label(("Puzzle:  " + model.getActivePuzzleIndex() + 1) + "/" + model.getPuzzleLibrarySize());
        puzzleNum.getStyleClass().add("puzzleNum");
        pane.getChildren().add(puzzleNum);
        return pane;
    }

    private HBox renderButtons() {
        HBox layout = new HBox(5);
        // previous button
        Button prev = new Button("Previous");
        prev.getStyleClass().add("buttonStyle");
        prev.setOnAction(
                (ActionEvent event) -> {
                    controller.clickPrevPuzzle();
                });
        layout.getChildren().add(prev);

        // next
        Button next = new Button("Next");
        next.getStyleClass().add("buttonStyle");
        next.setOnAction(
                (ActionEvent event) -> {
                    controller.clickNextPuzzle();
                });
        layout.getChildren().add(next);

        // random
        Button rand = new Button("Random");
        rand.getStyleClass().add("buttonStyle");

        rand.setOnAction(
                (ActionEvent event) -> {
                    int before = model.getActivePuzzleIndex();
                    controller.clickRandPuzzle();
                    int after = model.getActivePuzzleIndex();

                    while (before == after) {
                        before = model.getActivePuzzleIndex();
                        controller.clickRandPuzzle();
                        after = model.getActivePuzzleIndex();
                    }
                });
        layout.getChildren().add(rand);

        // reset
        Button resetButton = new Button("Reset");
        resetButton.getStyleClass().add("buttonStyle");
        resetButton.setOnAction(
                (ActionEvent event) -> {
                    controller.clickResetPuzzle();
                });
        layout.getChildren().add(resetButton);
        return layout;
    }
}
