package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.Evidence;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class CctvController extends Evidence {

  @FXML private MediaView mediaView;
  @FXML private Button playButton;
  @FXML private Slider progressSlider;

  private Media footage;
  private MediaPlayer videoPlayer;
  private boolean isPlaying;

  /**
   * Initializes the Security Cam view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  @Override
  public void initialize() {
    timerLabel.setText(timeManager.formatTime());
    decrementTime();

    try {
      footage = new Media(App.class.getResource("/videos/SecurityCam4.mp4").toURI().toString());
      videoPlayer = new MediaPlayer(footage);
      mediaView.setMediaPlayer(videoPlayer);
    } catch (URISyntaxException e) {
      // Handle the exception as needed
      System.out.println("Problem with the media file");
      e.printStackTrace();
    }

    // Bind video player progress with progress slider
    videoPlayer
        .currentTimeProperty()
        .addListener(
            new InvalidationListener() {
              public void invalidated(Observable ov) {
                updateValues();
              }
            });

    // Bind progress slider with video property to allow for jumping
    progressSlider
        .valueProperty()
        .addListener(
            new InvalidationListener() {
              public void invalidated(Observable ov) {
                if (progressSlider.isPressed()) {
                  videoPlayer.seek(
                      videoPlayer
                          .getMedia()
                          .getDuration()
                          .multiply(progressSlider.getValue() / 100));
                }
              }
            });
  }

  // Method to update progress slider to follow video
  protected void updateValues() {
    Platform.runLater(
        new Runnable() {
          public void run() {
            progressSlider.setValue(
                videoPlayer.getCurrentTime().toMillis()
                    / videoPlayer.getTotalDuration().toMillis()
                    * 100);
          }
        });
  }

  @FXML
  private void handlePlayClick(ActionEvent event) {
    if (isPlaying) {
      videoPlayer.pause();
      playButton.setText("PLAY");
      isPlaying = false;
    } else {
      videoPlayer.play();
      playButton.setText("PAUSE");
      isPlaying = true;
    }
  }

  @FXML
  private void exitToCrimeScene(MouseEvent event) throws IOException {
    videoPlayer.pause();
    playButton.setText("PLAY");
    isPlaying = false;
    App.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
  }

  private void decrementTime() {
    timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> updateTimerLabel()));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void updateTimerLabel() {
    minute = timeManager.getMinute();
    second = timeManager.getSecond();
    if (minute == 0 && second == 0) {
      timerLabel.setText("Time's Up!");
    } else {
      timerLabel.setText(String.format("%02d:%02d", minute, second));
    }
  }
}
