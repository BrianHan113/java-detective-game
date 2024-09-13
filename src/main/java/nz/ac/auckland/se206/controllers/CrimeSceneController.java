package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class CrimeSceneController {

  @FXML private Button viewEvidenceBtn;
  @FXML private Rectangle shoeprintRect;
  @FXML private Rectangle securityCameraRect;
  @FXML private Rectangle hammerRect;
  @FXML private Label timerLbl;
  @FXML private Rectangle sonPinRect;
  @FXML private Rectangle friendPinRect;
  @FXML private Rectangle wifePinRect;
  @FXML private Button guessBtn;

  @FXML
  private void showEvidence() {
    System.out.println("Show Evidence Button Clicked");
  }

  @FXML
  private void guessBtnClicked() {
    System.out.println("Guess Button Clicked");
  }
}
