package nz.ac.auckland.se206.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import nz.ac.auckland.se206.InteractionManager;
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
  private InteractionManager interact = InteractionManager.getInstance();

  @FXML
  public void initialize() {
    timerLabel.setText(timeManager.formatTime());
    decrementTime();

    if (interact.getInteractHammer()) {
      fingerprintHideLabel.setVisible(false);
      fingerprintLabel.setVisible(true);
    }
    if (interact.getInteractFootprint()) {
      shoeprintHideLabel.setVisible(false);
      shoeprintLabel.setVisible(true);
    }
    if (interact.getInteractSecurityCamera()) {
      securityCamHideLabel.setVisible(false);
      securityCamLabel.setVisible(true);
    }
  }

  @FXML
  private void exitToCrimeScene(MouseEvent event) {
    Circle circle = (Circle) event.getSource();
    Scene scene = circle.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
  }

  @FXML
  private void moveToOtherEvidence(MouseEvent event) {
    Label label = (Label) event.getSource();
    String labelId = label.getId();
    Scene scene = label.getScene();

    switch (labelId) {
      case "evidenceLabel":
        scene.setRoot(SceneManager.getUiRoot(AppUi.EVIDENCE));
        break;
      case "fingerprintLabel":
        scene.setRoot(SceneManager.getUiRoot(AppUi.FINGERPRINT));
        break;
      case "shoeprintLabel":
        scene.setRoot(SceneManager.getUiRoot(AppUi.FOOTPRINT));
        break;
      case "securityCamLabel":
        scene.setRoot(SceneManager.getUiRoot(AppUi.CCTV));
        break;
      default:
        break;
    }
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
