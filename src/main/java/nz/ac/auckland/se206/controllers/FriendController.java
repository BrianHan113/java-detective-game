package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.Chat;
import nz.ac.auckland.se206.TimeManager;

public class FriendController extends Chat {

  @FXML private Button sendButton;
  @FXML private TextArea txtArea;
  @FXML private TextField txtInput;
  @FXML private Label timerLabel;
  @FXML private Rectangle crimeScenePinRect;
  @FXML private Rectangle sonPinRect;
  @FXML private Rectangle wifePinRect;
  @FXML private ImageView crimeSceneHover;
  @FXML private ImageView sonHover;
  @FXML private ImageView exwifeHover;

  private TimeManager timeManager = TimeManager.getInstance();

  /**
   * Initializes the chat view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() {
    // Bind <Enter> key to sendButton
    txtInput.setOnKeyPressed(
        event -> {
          if (event.getCode() == KeyCode.ENTER) {
            sendButton.fire();
          }
        });
    setRole("Friend", "friend.txt");

    timerLabel.setText(timeManager.formatTime());
    timeManager.decrementTime(timerLabel);

    // Debugging: Check if txtInput is focusable
    System.out.println("txtInput is focusable: " + txtInput.isFocusTraversable());

    // Request focus for txtInput with a slight delay
    Platform.runLater(() -> {
      try {
        Thread.sleep(100); // Add a slight delay
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      txtInput.requestFocus();
      // Debugging: Check if txtInput has focus
      System.out.println("txtInput has focus: " + txtInput.isFocused());
    });
  }

  @FXML
  private void handleRectangleEnter(MouseEvent event) {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();

    // Handle map clicks
    switch (shapeId) {
      case "wifePinRect":
        exwifeHover.setVisible(true);
        break;
      case "sonPinRect":
        sonHover.setVisible(true);
        break;
      case "crimeScenePinRect":
        crimeSceneHover.setVisible(true);
        break;
      default:
        break;
    }
  }

  @FXML
  private void handleRectangleExit(MouseEvent event) {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();

    // Handle map clicks
    switch (shapeId) {
      case "wifePinRect":
        exwifeHover.setVisible(false);
        break;
      case "sonPinRect":
        sonHover.setVisible(false);
        break;
      case "crimeScenePinRect":
        crimeSceneHover.setVisible(false);
        break;
      default:
        break;
    }
  }
}
