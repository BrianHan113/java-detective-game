package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.Chat;
import nz.ac.auckland.se206.TimeManager;

/**
 * Controller class for the Son scene. This class extends the Chat class. This class is responsible
 * for handling the chat view for the son scene, as well as the map hover and clicks.
 */
public class SonController extends Chat {

  @FXML private Button sendButton;
  @FXML private TextArea txtArea;
  @FXML private TextField txtInput;
  @FXML private Label timerLabel;
  @FXML private Rectangle crimeScenePinRect;
  @FXML private Rectangle friendPinRect;
  @FXML private Rectangle wifeRect;
  @FXML private ImageView crimeSceneHover;
  @FXML private ImageView exwifeHover;
  @FXML private ImageView friendHover;

  private TimeManager timeManager = TimeManager.getInstance();

  /**
   * Initializes the chat view for the son scene.
   *
   * <p>This method is called automatically by JavaFX after the FXML file has been loaded. It binds
   * the "Enter" key to the sendButton and sets the role of the chat user. Additionally, it
   * initializes the timer and starts decrementing the time.
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
    setRole("Son", "son.txt");

    timerLabel.setText(timeManager.formatTime());
    timeManager.decrementTime(timerLabel);
  }

  /**
   * Handles the mouse entering a map pin (rectangle) area.
   *
   * <p>Displays the corresponding hover image for the map location that the mouse entered.
   *
   * @param event the mouse event triggered when the user hovers over a map pin
   */
  @FXML
  private void handleRectangleEnter(MouseEvent event) {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();

    // Handle map clicks
    switch (shapeId) {
      case "wifePinRect":
        exwifeHover.setVisible(true);
        break;
      case "friendPinRect":
        friendHover.setVisible(true);
        break;
      case "crimeScenePinRect":
        crimeSceneHover.setVisible(true);
        break;
      default:
        break;
    }
  }

  /**
   * Handles the mouse exiting a map pin (rectangle) area.
   *
   * <p>Hides the corresponding hover image for the map location that the mouse exited.
   *
   * @param event the mouse event triggered when the user stops hovering over a map pin
   */
  @FXML
  private void handleRectangleExit(MouseEvent event) {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();

    // Handle map clicks
    switch (shapeId) {
      case "wifePinRect":
        exwifeHover.setVisible(false);
        break;
      case "friendPinRect":
        friendHover.setVisible(false);
        break;
      case "crimeScenePinRect":
        crimeSceneHover.setVisible(false);
        break;
      default:
        break;
    }
  }
}
