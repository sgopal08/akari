package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
    Pane frame = new VBox();
    frame.setMinWidth(500);
    frame.setMinHeight(500);
    frame.getChildren().add(puzzleView.render());
    frame.getChildren().add(messageView.render());
    frame.getChildren().add(controlView.render());
    return frame;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }
}
