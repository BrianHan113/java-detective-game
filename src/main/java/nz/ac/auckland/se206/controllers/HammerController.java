package nz.ac.auckland.se206.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import nz.ac.auckland.se206.InteractionManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TimeManager;

public class HammerController {

  @FXML private Circle exitCircle;
  @FXML private Label timerLabel;
  @FXML private ImageView fingerprintImage;
  @FXML private ImageView brushImage;
  @FXML private Label evidenceLbl;

  public double opacity = 0;
  private boolean isFingerprintDusted = false;
  private int minute;
  private int second;
  private Timeline timeline;
  private TimeManager timeManager = TimeManager.getInstance();
  private InteractionManager interact = InteractionManager.getInstance();

  @FXML
  public void initialize() {
    fingerprintImage.setOpacity(opacity);
    evidenceLbl.setVisible(false);

    timerLabel.setText(timeManager.formatTime());
    decrementTime();
  }

  @FXML
  private void handleBrushDrag(MouseEvent event) {
    moveBrush(event.getSceneX(), event.getSceneY());

    if (isCollidingWithFingerprint()) {

      if (!isFingerprintDusted) {
        revealFingerprint();
      }
    }
  }

  @FXML
  private void exitToCrimeScene(MouseEvent event) {
    Circle button = (Circle) event.getSource();
    Scene scene = button.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
  }

  private boolean isCollidingWithFingerprint() {
    Bounds fingerprintBounds = fingerprintImage.getBoundsInParent();
    Bounds brushBounds = brushImage.getBoundsInParent();

    // Return true if the brush overlaps the fingerprint
    return fingerprintBounds.intersects(brushBounds);
  }

  private void moveBrush(double x, double y) {
    // Prevent brush from going off screen
    if (x < 50 || x > 850 || y < 50 || y > 500) {
      return;
    }

    brushImage.setLayoutX(x - brushImage.getFitWidth() / 2);
    brushImage.setLayoutY(y - brushImage.getFitHeight() / 2);
  }

  private void revealFingerprint() {
    this.opacity = this.opacity + 0.001;
    fingerprintImage.setOpacity(opacity);
    if (opacity >= 1) {
      isFingerprintDusted = true;
      evidenceLbl.setVisible(true);
      interact.setInteractFingerprintDusted(true);

      // To the guy implementing fingerprint clue scene,
      // this is where fingerprint is fully dusted
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
