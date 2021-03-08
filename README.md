# sublime-time
A simple command line tool to create, start, stop and delete timers.

## Manual Build Process
1. `mvn install`
1. `mvn package`
1. `java --module-path timer/target/timer-1.0.0.jar;timer-client/target/timer-client-1.0.0.jar -m timerclient/untypedjay.timer.client.CliClient`

## Commands
* `ls`: list all timers
* `mk NAME INTERVAL [LAPS]`: create a new timer (default: 10 laps)
* `rm ID`: remove the timer with the corresponding id
* `start ID`: start the timer with the corresponding id
* `stop ID`: stop the timer with the corresponding id
* `exit`: quit the application
* `help [COMMAND]`: get help