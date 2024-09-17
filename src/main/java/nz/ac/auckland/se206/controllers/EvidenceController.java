package nz.ac.auckland.se206.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TimeManager;

public class EvidenceController {

  @FXML private Circle exitCircle;
  @FXML private Label timerLabel;
  @FXML private Label fingerprintLabel;
  @FXML private Label shoeprintLabel;
  @FXML private Label securityCamLabel;
  @FXML private Label fingerprintHideLabel;
  @FXML private Label shoeprintHideLabel;
  @FXML private Label securityCamHideLabel;

  private int minute;
  private int second;
  private Timeline timeline;
  private TimeManager timeManager = TimeManager.getInstance();

  @FXML
  public void initialize() {
    timerLabel.setText(timeManager.formatTime());
    decrementTime();
  }

  @FXML
  private void exitToCrimeScene(MouseEvent event) {
    Circle button = (Circle) event.getSource();
    Scene scene = button.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
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
