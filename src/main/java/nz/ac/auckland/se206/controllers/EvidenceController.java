package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import nz.ac.auckland.se206.Evidence;

/** Controller class for the Evidence scene. This class extends the Evidence class. */
public class EvidenceController extends Evidence {

  @FXML
  public void initialize() {
    timerLabel.setText(timeManager.formatTime());
    timeManager.decrementTime(timerLabel);
  }
}
