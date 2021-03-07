package untypedjay.timer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

public class CliClient {
  public static void main(String[] args) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    String userCmd = promptFor(in, "");

    while (!userCmd.equals("exit")) {
      switch (userCmd) {
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
          printHelpPage();
          break;

        default:
          System.out.println("ERROR: invalid command");
          break;
      }

      userCmd = promptFor(in, "");
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
    System.out.println("NAME    INTERVAL    LAPS    STATUS");
    for (var timer: getAllTimers()) {
      System.out.println(timer);
    }
  }

  private static List<String> getAllTimers() {
    var result = new Vector<String>();
    result.add("DummyTimer1   00:01:22    10    running");
    result.add("DummyTimer1   00:00:55     3    stopped");
    return result;
  }

  private static void printHelpPage() {
    System.out.println("ls          list timers");
    System.out.println("mk          create new timer");
    System.out.println("rm          remove timer");
    System.out.println("start       start timer");
    System.out.println("stop        stop timer");
    System.out.println("exit        quit application");
  }
}
