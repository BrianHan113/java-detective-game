package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import nz.ac.auckland.se206.App;

public class VideoplayerController {

    @FXML
    private BorderPane mediaPane;
    @FXML
    private MediaView mediaView;
    @FXML
    private Button playButton;

    private Media footage;
    private MediaPlayer videoPlayer;

    @FXML
    void initialize() {
      try {
        footage = new Media(App.class.getResource("/videos/vid.mp4").toURI().toString());
        videoPlayer = new MediaPlayer(footage);
        mediaView.setMediaPlayer(videoPlayer);

        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
      } catch (URISyntaxException e) {
        e.printStackTrace();
        // Handle the error appropriately
      }
    }

    @FXML
    void onPlay(ActionEvent event) {
      videoPlayer.play();
    }

}
