package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import nz.ac.auckland.se206.Evidence;

/** Controller class for the Fingerprint scene. This class extends the Evidence class. */
public class FingerprintController extends Evidence {

  @FXML
  public void initialize() {
    timerLabel.setText(timeManager.formatTime());
    timeManager.decrementTime(timerLabel);
  }
}
