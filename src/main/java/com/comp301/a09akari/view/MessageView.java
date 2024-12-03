package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class MessageView implements FXComponent {
  private Model model;
  private ClassicMvcController controller;

  public MessageView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    StackPane popUp = new StackPane();
    if (model.isSolved()) {
      int num = model.getActivePuzzleIndex() + 1;
      Rectangle box = new Rectangle();
      box.setFill(Color.PINK);
      box.getStyleClass().add("box");
      Label instructions = new Label("You completed puzzle " + num + "!");
      instructions.getStyleClass().add("instructions");
      instructions.setAlignment(Pos.TOP_CENTER);
      popUp.getChildren().add(instructions);
    }
    return popUp;
  }
}
