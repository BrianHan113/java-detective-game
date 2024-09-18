package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.SceneManager.AppUi;

public abstract class Evidence implements Controller {
  @FXML
  private void moveToOtherEvidence(MouseEvent event) throws IOException {
    Label label = (Label) event.getSource();
    String labelId = label.getId();

    switch (labelId) {
      case "evidenceLabel":
        App.setRoot(SceneManager.getUiRoot(AppUi.EVIDENCE));
        break;
      case "fingerprintLabel":
        App.setRoot(SceneManager.getUiRoot(AppUi.FINGERPRINT));
        break;
      case "shoeprintLabel":
        App.setRoot(SceneManager.getUiRoot(AppUi.FOOTPRINT));
        break;
      case "securityCamLabel":
        App.setRoot(SceneManager.getUiRoot(AppUi.CCTV));
        break;
      default:
        break;
    }
  }

  @FXML
  private void exitToCrimeScene(MouseEvent event) throws IOException {
    App.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
  }
}
