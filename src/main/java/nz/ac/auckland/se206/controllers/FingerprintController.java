package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import nz.ac.auckland.se206.Evidence;

public class FingerprintController extends Evidence {

  @FXML
  public void initialize() {
    timerLabel.setText(timeManager.formatTime());
    timeManager.decrementTime(timerLabel);
  }
}
