package untypedjay.timer;

import java.util.EventListener;

public interface TimerListener extends EventListener {
  void lapExpired(TimerEvent e);
  void timerExpired(TimerEvent e);
}
