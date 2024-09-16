package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class HammerController {

  @FXML private Circle exitCircle;
  @FXML private Label timerLabel;
  @FXML private ImageView fingerprintImage;
  @FXML private ImageView brushImage;
  public double opacity = 0;
  private boolean isFingerprintDusted = false;

  @FXML
  public void initialize() {
    fingerprintImage.setOpacity(opacity);
  }

  @FXML
  private void handleBrushDrag(MouseEvent event) {
    moveBrush(event.getSceneX(), event.getSceneY());

    if (isCollidingWithFingerprint()) {
      System.out.println("Coliding");
    }
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

  // @FXML
  // private void handleMouseDragged(MouseEvent event) {
  //   if (!isFingerprintDusted) {
  //     this.opacity = this.opacity + 0.001;
  //     fingerprintImage.setOpacity(opacity);
  //     System.out.println("weiofj");
  //     if (opacity >= 1) {
  //       isFingerprintDusted = true;
  //     }
  //   }
  // }
}
