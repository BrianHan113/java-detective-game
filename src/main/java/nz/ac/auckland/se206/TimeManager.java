package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * TimeManager class is a singleton class that manages the time for the game. It is used to start,
 * stop, reset and decrement the time. It also formats the time in minutes and seconds.
 */
public class TimeManager {

  private static TimeManager instance;
  private static boolean isFirstTimeUp = true;

  /**
   * Returns the instance of the TimeManager class. If the instance is null, a new instance is
   * created. The instance is used to access the methods of the TimeManager class. This is so that
   * only one instance of the TimeManager class is created and used throughout the game, sycning the
   * time between scenes.
   *
   * @return the instance of the TimeManager class
   */
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

  /**
   * Initializes and starts a countdown timer that decrements the {@code time} field every
   * millisecond until it reaches zero, at which point it stops. The timer is implemented using a
   * {@link Timeline} with indefinite cycles.
   */
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

  /**
   * Formats the time in minutes and seconds.
   *
   * @return the formatted time in minutes and seconds
   */
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

  /**
   * Decrements the time by 1 millisecond and updates the timer label with the new time. The timer
   * is implemented using a {@link Timeline} with indefinite cycles.
   *
   * @param timerLabel the label to display the time
   */
  public void decrementTime(Label timerLabel) {
    timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> updateTimerLabel(timerLabel)));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void updateTimerLabel(Label timerLabel) {
    minute = getMinute();
    second = getSecond();
    if (minute == 0 && second == 0) {
      timerLabel.setText("Time's Up!");
    } else {
      timerLabel.setText(String.format("%02d:%02d", minute, second));
    }
  }
}
