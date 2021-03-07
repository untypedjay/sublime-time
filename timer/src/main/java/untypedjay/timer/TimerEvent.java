package untypedjay.timer;

import java.time.Duration;
import java.time.Instant;
import java.util.EventObject;

public class TimerEvent extends EventObject {
  protected String timerName;
  protected Duration elapsedTime;
  protected int totalLaps;
  protected int completedLaps;

  public TimerEvent(Timer timer) {
    super(timer);
    this.timerName = timer.getName();
    this.elapsedTime = Duration.between(timer.getStartTime(), Instant.now());
    this.totalLaps = timer.getTotalLaps();
    this.completedLaps = timer.getCompletedLaps();
  }

  public String getTimerName() {
    return timerName;
  }

  public Duration getElapsedTime() {
    return elapsedTime;
  }

  public int getTotalLaps() {
    return totalLaps;
  }

  public int getCompletedLaps() {
    return completedLaps;
  }
}
