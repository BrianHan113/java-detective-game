package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

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

  @FXML
  private void handleNavigationClick(MouseEvent event) {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();
    Scene sceneOfShape = shape.getScene();

    switch (shapeId) {
      case "wifePinRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.EX_WIFE));
        break;
      case "friendPinRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.FRIEND));
        break;
      case "sonPinRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.SON));
        break;

      default:
        break;
    }
  }
}
