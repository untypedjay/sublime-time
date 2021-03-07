package untypedjay.timer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.Vector;

public class CliClient implements TimerListener {
  private static List<Timer> timers = new Vector<>();

  public static void main(String[] args) {
    Duration duration = Duration.parse("PT20.345S");
    timers.add(new Timer("DummyTimer1", duration, 10));
    timers.add(new Timer("Cool Timer", duration, 23));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    String userInput = promptFor(in, "");
    String[] commands = userInput.split(" ");

    while (!commands[0].equals("exit")) {
      switch (commands[0]) {
        case "ls":
          printTimers();
          break;

        case "mk":
          System.out.println("ERROR: not yet implemented");
          break;

        case "rm":
          System.out.println("ERROR: not yet implemented");
          break;

        case "start":
          System.out.println("ERROR: not yet implemented");
          break;

        case "stop":
          System.out.println("ERROR: not yet implemented");
          break;

        case "help":
          if (commands.length >= 2) {
            printHelpPage(commands[1]);
          } else {
            printHelpPage("");
          }

          break;

        default:
          System.out.println("ERROR: invalid command");
          break;
      }

      commands = promptFor(in, "").split(" ");
    }
  }

  static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    }
  }

  private static void printTimers() {
    System.out.println("ID    NAME    INTERVAL    LAPS    RUNNING");
    for (int i = 0; i < timers.size(); i++) {
      System.out.println(i + 1 + "    " + timers.get(i).getName() + "    " + formatDuration(timers.get(i).getInterval()) + "   " + timers.get(i).getTotalLaps() + "    " + timers.get(i).isRunning());
    }
  }

  private static void printHelpPage(String command) {
    switch (command) {
      case "ls":
        System.out.println("Usage:  ls");
        System.out.println("List all timers");
        break;
      case "mk":
        System.out.println("Usage:  mk NAME INTERVAL [LAPS]");
        System.out.println("Create a new timer");
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

  public static String formatDuration(Duration duration) {
    long seconds = duration.getSeconds();
    long absSeconds = Math.abs(seconds);
    String positive = String.format(
      "%d:%02d:%02d",
      absSeconds / 3600,
      (absSeconds % 3600) / 60,
      absSeconds % 60);
    return seconds < 0 ? "-" + positive : positive;
  }

  @Override
  public void lapExpired(TimerEvent e) {
    System.out.println(e.getTimerName() + ": " + e.getElapsedTime().getSeconds() + " (" + e.getCompletedLaps() + "/" + e.getTotalLaps() + " laps)");
  }

  @Override
  public void timerExpired(TimerEvent e) {
    System.out.println(e.getTimerName() + ": finished " + e.getTotalLaps() + " laps in " + e.getElapsedTime().getSeconds() + "s");
  }
}
