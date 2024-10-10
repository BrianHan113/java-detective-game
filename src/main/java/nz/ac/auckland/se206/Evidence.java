package nz.ac.auckland.se206;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
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
  @FXML protected ImageView fingerprintTab;
  @FXML protected ImageView shoeprintTab;
  @FXML protected ImageView securityCamTab;
  @FXML protected ImageView evidenceTab;

  @FXML protected ImageView image;
  @FXML protected Label imageHideLabel;

  protected int minute;
  protected int second;
  protected Timeline timeline;
  protected TimeManager timeManager = TimeManager.getInstance();

  @FXML
  private void moveToOtherEvidence(MouseEvent event) throws IOException {

    // Find fxid of clicked object
    Label label = (Label) event.getSource();
    String labelId = label.getId();

    // Go to the corresponding scene
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
  private void handleEvidenceEnter(MouseEvent event) {
    Label label = (Label) event.getSource();
    String labelId = label.getId();
    Image clueHover = null;
    Image evidenceHover = null;

    // Setup the hover on the evidence tabs
    try {
      clueHover =
          new Image(App.class.getResource("/images/other_page_hover.png").toURI().toString());
      evidenceHover =
          new Image(
              App.class.getResource("/images/other_page_colour_hover.png").toURI().toString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    // Label identifying logic
    switch (labelId) {
      case "evidenceLabel":
        evidenceTab.setImage(evidenceHover);
        break;
      case "fingerprintLabel":
        fingerprintTab.setImage(clueHover);
        break;
      case "shoeprintLabel":
        shoeprintTab.setImage(clueHover);
        break;
      case "securityCamLabel":
        securityCamTab.setImage(clueHover);
        break;
      default:
        break;
    }
  }

  @FXML
  private void handleEvidenceExit(MouseEvent event) {
    Label label = (Label) event.getSource();
    String labelId = label.getId();
    Image clueHover = null;
    Image evidenceHover = null;

    // Setup the hover on the evidence tabs
    try {
      clueHover = new Image(App.class.getResource("/images/other_page.png").toURI().toString());
      evidenceHover =
          new Image(App.class.getResource("/images/other_page_colour.png").toURI().toString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    // Label identifying logic
    switch (labelId) {
      case "evidenceLabel":
        evidenceTab.setImage(evidenceHover);
        break;
      case "fingerprintLabel":
        fingerprintTab.setImage(clueHover);
        break;
      case "shoeprintLabel":
        shoeprintTab.setImage(clueHover);
        break;
      case "securityCamLabel":
        securityCamTab.setImage(clueHover);
        break;
      default:
        break;
    }
  }

  @FXML
  private void exitToCrimeScene(MouseEvent event) throws IOException {
    App.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
  }

  public void setSecurityCamLabelVisible() {
    securityCamLabel.setVisible(true);
    securityCamHideLabel.setVisible(false);
  }

  public void setFingerprintLabelVisible() {
    fingerprintLabel.setVisible(true);
    fingerprintHideLabel.setVisible(false);
  }

  public void setShoeprintLabelVisible() {
    shoeprintLabel.setVisible(true);
    shoeprintHideLabel.setVisible(false);
  }

  public void setFingerprintImageVisible() {
    image.setVisible(true);
    imageHideLabel.setVisible(false);
  }

  @FXML
  private void handleExitBtnEnter() {
    Paint color = Paint.valueOf("#6f7c89");
    exitCircle.setFill(color);
  }

  @FXML
  private void handleExitBtnExit() {
    Paint color = Paint.valueOf("#bbd1e8");
    exitCircle.setFill(color);
  }
}
