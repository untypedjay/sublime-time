package untypedjay.timer;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Timer {
  private List<TimerListener> timerListeners = new CopyOnWriteArrayList<TimerListener>();
  private String name;
  private Instant startTime;
  private Duration interval;
  private int totalLaps;
  private int completedLaps;
  private boolean isRunning;

  public Timer(String name, Duration interval, int laps) {
    this.name = name;
    this.interval = interval;
    this.totalLaps = laps;
    this.completedLaps = 0;
    this.isRunning = false;
  }

  public void addTimerListener(TimerListener l) {
    timerListeners.add(l);
  }
  public void removeTimerListener(TimerListener l) {
    timerListeners.remove(l);
  }
  private void fireLapExpiryEvent() {
    TimerEvent event = new TimerEvent(this);
    for (TimerListener listener : timerListeners) {
      listener.lapExpired(event);
    }
  }

  private void fireTimerExpiryEvent() {
    TimerEvent event = new TimerEvent(this);
    for (TimerListener listener : timerListeners) {
      listener.timerExpired(event);
    }
  }

  public List<TimerListener> getTimerListeners() {
    return timerListeners;
  }

  public String getName() {
    return name;
  }

  public Instant getStartTime() {
    return startTime;
  }

  public Duration getInterval() {
    return interval;
  }

  public int getTotalLaps() {
    return totalLaps;
  }

  public int getCompletedLaps() {
    return completedLaps;
  }

  public boolean isRunning() {
    return isRunning;
  }
}