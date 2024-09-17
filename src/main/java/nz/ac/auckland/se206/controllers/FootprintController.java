package nz.ac.auckland.se206.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import nz.ac.auckland.se206.TimeManager;

public class FootprintController {

  @FXML private Circle exitCircle;
  @FXML private Label timerLabel;
  @FXML private Label evidenceLabel;
  @FXML private Label fingerprintLabel;
  @FXML private Label securityCamLabel;
  @FXML private Label fingerprintHideLabel;
  @FXML private Label securityCamHideLabel;
  @FXML private ImageView rulerImage;

  private int minute;
  private int second;
  private Timeline timeline;
  private TimeManager timeManager = TimeManager.getInstance();

  public void initialize() {
    timerLabel.setText(timeManager.formatTime());
    decrementTime();
  }

  private void decrementTime() {
    timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> updateTimerLabel()));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void updateTimerLabel() {
    minute = timeManager.getMinute();
    second = timeManager.getSecond();
    if (minute == 0 && second == 0) {
      timerLabel.setText("Time's Up!");
    } else {
      timerLabel.setText(String.format("%02d:%02d", minute, second));
    }
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
