package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.Chat;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class ExwifeController extends Chat {

  @FXML private Button sendButton;
  @FXML private TextArea txtArea;
  @FXML private TextField txtInput;
  @FXML private Label timerLabel;
  @FXML private Rectangle crimeSceneRect;
  @FXML private Rectangle sonRect;
  @FXML private Rectangle friendRect;

  /**
   * Initializes the chat view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    System.out.println("Initialized Ex-wife Chat: " + this);
    // Bind <Enter> key to sendButton
    txtInput.setOnKeyPressed(
        event -> {
          if (event.getCode() == KeyCode.ENTER) {
            sendButton.fire();
          }
        });
    setRole("Ex-Wife", "chat.txt");
  }

  @FXML
  private void handleNavigationClick(MouseEvent event) {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();
    Scene sceneOfShape = shape.getScene();

    switch (shapeId) {
      case "friendPinRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.FRIEND));
        break;
      case "sonPinRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.SON));
        break;
      case "crimeScenePinRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
        break;

      default:
        break;
    }
  }
}
