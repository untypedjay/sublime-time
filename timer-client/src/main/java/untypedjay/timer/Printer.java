package untypedjay.timer;

import java.time.Duration;
import java.util.List;

public class Printer {
  public static void printInvalidCommandError(String[] commandArray) {
    System.out.println("'" + String.join(" ", commandArray) + "' is not a valid command");
    System.out.println("See 'help'");
  }

  public static void printTimers(List<Timer> timers) {
    System.out.println("ID    NAME    INTERVAL    LAPS    RUNNING");
    for (int i = 0; i < timers.size(); i++) {
      System.out.println(i + 1 + "    " + timers.get(i).getName() + "    " + formatDuration(timers.get(i).getInterval()) + "   " + timers.get(i).getTotalLaps() + "    " + timers.get(i).isRunning());
    }
  }

  public static void printHelpPage(String command) {
    switch (command) {
      case "ls":
        System.out.println("Usage:  ls");
        System.out.println("List all timers");
        break;
      case "mk":
        System.out.println("Usage:  mk NAME INTERVAL [LAPS]");
        System.out.println("Create a new timer");
        System.out.println("INTERVAL (hh:mm:ss): the duration of a single lap");
        System.out.println("LAPS (integer): how often the timer should be executed");
        break;
      case "rm":
        System.out.println("Usage:  rm ID");
        System.out.println("Remove the timer with the corresponding id");
        break;
      case "start":
        System.out.println("Usage:  start ID");
        System.out.println("Start the timer with the corresponding id");
        break;
      case "stop":
        System.out.println("Usage:  stop ID");
        System.out.println("Stop the timer with the corresponding id");
        break;
      case "exit":
        System.out.println("Usage:  exit");
        System.out.println("Quit the application");
        break;
      default:
        System.out.println("ls          list timers");
        System.out.println("mk          new timer");
        System.out.println("rm          remove timer");
        System.out.println("start       start timer");
        System.out.println("stop        stop timer");
        System.out.println("exit        quit application");
    }
  }

  private static String formatDuration(Duration duration) {
    long seconds = duration.getSeconds();
    long absSeconds = Math.abs(seconds);
    String positive = String.format(
      "%d:%02d:%02d",
      absSeconds / 3600,
      (absSeconds % 3600) / 60,
      absSeconds % 60);
    return seconds < 0 ? "-" + positive : positive;
  }
}
