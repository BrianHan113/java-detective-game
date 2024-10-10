package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.Controller;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TimeManager;

/** Controller class for the Context scene. This class implements the Controller interface. */
public class ContextController implements Controller {

  @FXML private Button continueButton;

  private TimeManager timeManager = TimeManager.getInstance();

  @FXML
  public void initialize() {
    continueButton.setDisable(true);
  }

  @FXML
  private void onContinuePressed(ActionEvent event) throws IOException {
    // Change to correct room to start game.
    App.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
    timeManager.startTimer();
  }

  public void enableContinueButton() {
    continueButton.setDisable(false);
  }
}
