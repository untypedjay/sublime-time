package untypedjay.timer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.Vector;

import static untypedjay.timer.Printer.*;

public class CliClient {
  private static List<Timer> timers = new Vector<>();

  public static void main(String[] args) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    String[] commands = promptFor(in, "").split(" ");

    while (!commands[0].equals("exit")) {
      switch (commands[0]) {
        case "ls":
          printTimers(timers);
          break;

        case "mk":
          if (commands.length < 3) {
            printInvalidCommandError(commands);
          } else {
            createTimer(commands);
          }
          break;

        case "rm":
          if (commands.length != 2) {
            printInvalidCommandError(commands);
          } else {
            try {
              int timerId = Integer.parseInt(commands[1]);
              removeTimer(timerId - 1);
            } catch (NumberFormatException e) {
              printInvalidCommandError(commands);
            }
          }
          break;

        case "start":
          if (commands.length != 2) {
            printInvalidCommandError(commands);
          } else {
            try {
              int timerId = Integer.parseInt(commands[1]);
              new Thread(timers.get(timerId - 1)).start();
            } catch (NumberFormatException e) {
              printInvalidCommandError(commands);
            }
          }
          break;

        case "stop":
          if (commands.length != 2) {
            printInvalidCommandError(commands);
          } else {
            try {
              int timerId = Integer.parseInt(commands[1]);
              stopTimer(timerId - 1);
            } catch (NumberFormatException e) {
              printInvalidCommandError(commands);
            }
          }
          break;

        case "help":
          if (commands.length >= 2) {
            printHelpPage(commands[1]);
          } else {
            printHelpPage("");
          }

          break;

        default:
          printInvalidCommandError(commands);
          break;
      }

      commands = promptFor(in, "").split(" ");
    }
  }

  private static void stopTimer(int id) {
    //TODO
  }

  private static void createTimer(String[] commandArray) {
    String[] timeStringArray = commandArray[2].split(":");
    int[] timeNumberArray = new int[timeStringArray.length];

    try {
      for (int i = 0; i < timeStringArray.length; i++) {
        timeNumberArray[i] = Integer.parseInt(timeStringArray[i]);
      }
    } catch (NumberFormatException e) {
      printInvalidCommandError(commandArray);
      return;
    }

    Duration duration = getDuration(timeNumberArray);
    if (commandArray.length == 3 && duration != null) {
      addTimer(new Timer(commandArray[1], duration, 10));
    } else if (duration != null) {
      addTimer(new Timer(commandArray[1], duration, Integer.parseInt(commandArray[3])));
    } else {
      printInvalidCommandError(commandArray);
    }
  }

  private static void addTimer(Timer timer) {
    timer.addTimerListener(new TimerListener() {
      @Override
      public void lapExpired(TimerEvent e) {
        System.out.println(e.getTimerName() + ": " + formatDuration(e.getElapsedTime()) + " (" + e.getCompletedLaps() + "/" + e.getTotalLaps() + " laps)");
      }

      @Override
      public void timerExpired(TimerEvent e) {
        System.out.println(e.getTimerName() + ": finished " + e.getTotalLaps() + " laps in " + formatDuration(e.getElapsedTime()));
      }
    });

    timers.add(timer);
  }

  private static void removeTimer(int id) {
    if (id < 0 || id >= timers.size()) {
      System.out.println("ERROR: timer id does not exist");
    } else {
      timers.remove(id);
    }
  }

  private static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    }
  }

  public static Duration getDuration(int[] input) {
    if (input.length != 3) {
      return null;
    }
    return Duration.parse("PT" + input[0] + "H" + input[1] + "M" + input[2] + "S");
  }
}
