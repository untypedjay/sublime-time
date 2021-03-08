package untypedjay.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Timer implements Runnable {
  private static Logger logger = LoggerFactory.getLogger(Timer.class);
  private List<TimerListener> timerListeners = new CopyOnWriteArrayList<>();
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

  public void start() {
    if (!isRunning) {
      new Thread(this).start();
      logger.debug(name + ": started");
    }
  }

  public void stop() {
    isRunning = false;
    logger.debug(name + ": stopped");
  }

  public void addTimerListener(TimerListener l) {
    timerListeners.add(l);
    logger.debug(name + ": listener added");
  }

  public void removeTimerListener(TimerListener l) {
    timerListeners.remove(l);
    logger.debug(name + ": listener removed");
  }

  private void fireLapExpiryEvent() {
    TimerEvent event = new TimerEvent(this);
    for (TimerListener listener : timerListeners) {
      listener.lapExpired(event);
    }
    logger.debug(name + ": lap expiry event fired");
  }

  private void fireTimerExpiryEvent() {
    TimerEvent event = new TimerEvent(this);
    for (TimerListener listener : timerListeners) {
      listener.timerExpired(event);
    }
    logger.debug(name + ": timer expiry event fired");
  }

  public List<TimerListener> getTimerListeners() {
    return timerListeners;
  }

  public void setTimerListeners(List<TimerListener> timerListeners) {
    this.timerListeners = timerListeners;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Instant getStartTime() {
    return startTime;
  }

  public Duration getInterval() {
    return interval;
  }

  public void setInterval(Duration interval) {
    this.interval = interval;
  }

  public int getTotalLaps() {
    return totalLaps;
  }

  public void setTotalLaps(int totalLaps) {
    this.totalLaps = totalLaps;
  }

  public int getCompletedLaps() {
    return completedLaps;
  }

  public boolean isRunning() {
    return isRunning;
  }

  @Override
  public void run() {
    isRunning = true;
    completedLaps = 0;
    startTime = Instant.now();
    for (int i = 0; i < totalLaps; i++) {
        try {
          for (int j = 0; j < interval.toSeconds(); j++) {
            Thread.sleep(1000);
            if (!isRunning) return;
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        completedLaps++;
        fireLapExpiryEvent();
    }
    fireTimerExpiryEvent();
    isRunning = false;
  }
}
