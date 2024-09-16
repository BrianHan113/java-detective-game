package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TimeManager {

  private static TimeManager instance;

  public static TimeManager getInstance() {
    if (instance == null) {
      instance = new TimeManager();
    }
    return instance;
  }

  private int minute;
  private int millisecond;
  private int second;
  private int time;
  private Timeline timeline;

  private TimeManager() {
    time = 5 * 60 * 1000;
  }

  public void startTimer() {
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.millis(1),
                e -> {
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
    millisecond = time % 1000;
    return String.format("%02d:%02d:%03d", minute, second, millisecond);
  }

  public int getMinute() {
    minute = time / 60000;
    return minute;
  }

  public int getSecond() {
    second = (time % 60000) / 1000;
    return second;
  }

  public int getMillisecond() {
    millisecond = time % 1000;
    return millisecond;
  }

  public void stop() {
    timeline.stop();
  }

  public void resetTimer(int min, int sec, int milli) {
    time = min * 60000 + sec * 1000 + milli;
  }
}
