package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.Chat;

public class SonController extends Chat {

  @FXML private Button sendButton;
  @FXML private TextArea txtArea;
  @FXML private TextField txtInput;
  @FXML private Label timerLabel;
  @FXML private Rectangle crimeSceneRect;
  @FXML private Rectangle friendRect;
  @FXML private Rectangle exwifeRect;

  /**
   * Initializes the chat view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    System.out.println("Initialized Son Chat: "+this);
    // Bind <Enter> key to sendButton
    txtInput.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        sendButton.fire();
      }
    });
    setRole("Son", "chat.txt");
  }
}
