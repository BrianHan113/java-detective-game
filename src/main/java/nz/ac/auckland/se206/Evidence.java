package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import nz.ac.auckland.se206.SceneManager.AppUi;

public abstract class Evidence implements Controller {
  @FXML protected Label evidenceLabel;
  @FXML protected Circle exitCircle;
  @FXML protected Label timerLabel;
  @FXML protected Label fingerprintLabel;
  @FXML protected Label shoeprintLabel;
  @FXML protected Label securityCamLabel;
  @FXML protected Label fingerprintHideLabel;
  @FXML protected Label shoeprintHideLabel;
  @FXML protected Label securityCamHideLabel;
  @FXML protected Line crossLine;

  protected int minute;
  protected int second;
  protected Timeline timeline;
  protected TimeManager timeManager = TimeManager.getInstance();

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

  public Label getFingerprintLabel() {
    return fingerprintLabel;
  }

  public Label getFingerprintHideLabel() {
    return fingerprintHideLabel;
  }

  public Label getShoeprintLabel() {
    return shoeprintLabel;
  }

  public Label getSecurityCamLabel() {
    return securityCamLabel;
  }

  public Label getShoeprintHideLabel() {
    return shoeprintHideLabel;
  }

  public Label getSecurityCamHideLabel() {
    return securityCamHideLabel;
  }
}
