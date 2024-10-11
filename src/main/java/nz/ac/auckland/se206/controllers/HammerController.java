package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.AudioPlayerManager;
import nz.ac.auckland.se206.Controller;
import nz.ac.auckland.se206.Evidence;
import nz.ac.auckland.se206.InteractionManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TimeManager;

/**
 * Controller class for the Hammer scene. This class implements the Controller interface. This class
 * is responsible for handling the hammer scene.
 */
public class HammerController implements Controller {

  private static InteractionManager interact = InteractionManager.getInstance();
  private static AudioPlayerManager audioPlayer = AudioPlayerManager.getInstance();

  @FXML private Circle exitCircle;
  @FXML private Label timerLabel;
  @FXML private ImageView fingerprintImage;
  @FXML private ImageView brushImage;
  @FXML private Label evidenceLbl;
  @FXML private Line crossLine;

  private double opacity = 0;
  private boolean isFingerprintDusted = false;
  private TimeManager timeManager = TimeManager.getInstance();

  /**
   * Initializes the Hammer view. Sets the timer and the opacity of the fingerprint image. Starts
   * the timer and decrements the time.
   */
  @FXML
  public void initialize() {
    fingerprintImage.setOpacity(opacity);
    evidenceLbl.setVisible(false);

    timerLabel.setText(timeManager.formatTime());
    timeManager.decrementTime(timerLabel);
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
  private void exitToCrimeScene(MouseEvent event) throws IOException {
    App.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
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
    // Slightly increase opacity of fingeprint to simulate dusting

    this.opacity = this.opacity + 0.004;
    fingerprintImage.setOpacity(opacity);

    // Fully dusted
    if (opacity >= 1) {
      Evidence fingController = (Evidence) SceneManager.getController(AppUi.FINGERPRINT);

      // Fingerprint unlocked in clue view
      isFingerprintDusted = true;
      evidenceLbl.setVisible(true);

      audioPlayer.playAudio("fingerprintFinish.mp3");

      interact.setInteractClue(true);
      fingController.setFingerprintImageVisible();
    }
  }

  @FXML
  private void handleExitBtnEnter() {
    Paint color = Paint.valueOf("#6f7c89");
    exitCircle.setFill(color);
  }

  @FXML
  private void handleExitBtnExit() {
    Paint color = Paint.valueOf("#BBD1E8");
    exitCircle.setFill(color);
  }
}
