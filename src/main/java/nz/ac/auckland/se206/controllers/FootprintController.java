package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.Evidence;

public class FootprintController extends Evidence {

  @FXML private ImageView rulerImage;

  @FXML
  public void initialize() {
    timerLabel.setText(timeManager.formatTime());
    timeManager.decrementTime(timerLabel);
  }

  @FXML
  private void handleRulerDrag(MouseEvent event) {
    moveRuler(event.getSceneX(), event.getSceneY());
  }

  private void moveRuler(double x, double y) {
    // Prevent ruler from going off screen
    if (x < 50 || x > 850 || y < 150 || y > 500) {
      return;
    }

    rulerImage.setLayoutX(x - rulerImage.getFitWidth() / 2);
    rulerImage.setLayoutY(y - rulerImage.getFitHeight() - 70);
  }
}
