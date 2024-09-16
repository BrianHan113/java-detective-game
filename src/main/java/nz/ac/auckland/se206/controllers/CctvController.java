package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;

public class CctvController {

  @FXML private Label evidenceLabel;
  @FXML private Circle exitCircle;
  @FXML private Label fingerprintHideLabel;
  @FXML private Label fingerprintLabel;
  @FXML private MediaView mediaView;
  @FXML private Button pauseButton;
  @FXML private Button playButton;
  @FXML private Slider progressSlider;
  @FXML private Label shoeprintHideLabel;
  @FXML private Label shoeprintLabel;
  @FXML private Label timerLabel;

  private Media footage;
  private MediaPlayer videoPlayer;

  /**
   * Initializes the Security Cam view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    try {
      footage = new Media(App.class.getResource("/videos/SecurityCam3.mp4").toURI().toString());
      videoPlayer = new MediaPlayer(footage);
      mediaView.setMediaPlayer(videoPlayer);
    } catch (URISyntaxException e) {
      // Handle the exception as needed
      e.printStackTrace();
    }
  }

  @FXML
  void handlePauseClick(ActionEvent event) {
    videoPlayer.stop();
  }

  @FXML
  void handlePlayClick(ActionEvent event) {
    videoPlayer.play();
  }

  @FXML
  void onMediaError(ActionEvent event) {
    System.out.println("Broken Media");
  }

}

