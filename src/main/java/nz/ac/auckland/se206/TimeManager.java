package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TimeManager {

  private static TimeManager instance;
  private static boolean isFirstTimeUp = true;

  public static TimeManager getInstance() {
    if (instance == null) {
      instance = new TimeManager();
    }
    return instance;
  }

  private int minute;
  private int second;
  private int time;
  private Timeline timeline;

  private TimeManager() {
    time = 5 * 60 * 1000;
  }

  public void startTimer() {
    // Start the timer using timeline
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.millis(1),
                e -> {
                  // Decrement timer while not reached 0
                  if (time > 0) {
                    time--;
                  } else {
                    stop();
                  }
                }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  public String formatTime() {
    minute = time / 60000;
    second = (time % 60000) / 1000;
    return String.format("%02d:%02d", minute, second);
  }

  public int getMinute() {
    minute = time / 60000;
    return minute;
  }

  public int getSecond() {
    second = (time % 60000) / 1000;
    return second;
  }

  public void stop() {
    timeline.stop();
  }

  public void resetTimer(int min) {
    time = min * 60000;
  }

  public boolean isFirstTimeUp() {
    return isFirstTimeUp;
  }

  public void setFirstTimeUp(boolean firstTimeUp) {
    isFirstTimeUp = firstTimeUp;
  }

  public void decrementTime(Label timerLabel) {
    timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> updateTimerLabel(timerLabel)));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void updateTimerLabel(Label timerLabel) {
    minute = getMinute();
    second = getSecond();
    if (minute == 0 && second == 0) {
      System.out.println("Time's Up from the time manager!");
      timerLabel.setText("Time's Up!");
    } else {
      timerLabel.setText(String.format("%02d:%02d", minute, second));
    }
  }
}
