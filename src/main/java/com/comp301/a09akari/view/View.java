package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class View implements FXComponent, ModelObserver {
  private FXComponent puzzleView;
  private FXComponent messageView;
  private FXComponent controlView;

  private Scene scene;

  public View(ClassicMvcController controller, Model model) {
    model.addObserver(this);
    puzzleView = new PuzzleView(controller, model);
    messageView = new MessageView(model, controller);
    controlView = new ControlView(model, controller);
    scene = new Scene(this.render());
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public Parent render() {
    VBox frame = new VBox();
    frame.getStyleClass().add("background");
    frame.setPrefSize(500, 600);
    frame.setSpacing(20);
    frame.setAlignment(Pos.CENTER);
    frame.getChildren().addAll(puzzleView.render(), messageView.render(), controlView.render());
    return frame;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }
}
