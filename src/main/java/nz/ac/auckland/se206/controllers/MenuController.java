package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {
  @FXML
  private Button playButton;

  @FXML
  private Button quitButton;

  @FXML
  public void initialize() throws IOException {
    // Initialise
  }

  @FXML
  private void playButtonClicked(ActionEvent event) {
    // Change to correct room to start game.
  }

  @FXML
  private void quitButtonClicked(ActionEvent event) {
    // Quit game
  }

}
