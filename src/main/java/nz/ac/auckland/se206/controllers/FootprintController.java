package nz.ac.auckland.se206.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
  private int millisecond;
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
}
