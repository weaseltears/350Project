# cowboymahjong

This is the readme for cowboymahjong, try to keep it up to date with any information future-you will wish past-you
remembered to write down

## Project set up
This is a gradle project using JMonkey Engine and other java libraries

# Modules : 

Game module `:game` : holds `build.gradle` dependencies for the game code & should hold your code.

Desktop module `:desktop` : holds `build.gradle` for desktop dependencies & uses the `:game` module, this module can hold the desktop gui.


# Running Game : 

### Desktop : 

```gradle
./gradlew run
```


# Building Game :

### Desktop :

```bash
    $./gradlew :desktop:copyJars
```



### Distribute with a JRE

Distributing with a JRE means you'll need to provide an operating specific bundle for each OS you are
targeting (which is a disadvantage) but your end use will not have to have a JRE locally installed
(which is an advantage).

Either:

In your IDE execute the gradle task distZip (which you'll find under gradle > distributions > buildAllDistributions)

Or:

In the command line open at the root of this project enter the following command: gradlew buildAllDistributions

Then you will find a series of zip in the build/distributions folder. These zip(s) will contain your game, all the libraries to run it and an
OS specific JRE. (The same files will also be available unzipped in a folder, which may be useful if distributing via steampipe or similar).


References :



=> Gradle DSL : https://docs.gradle.org/current/dsl/index.html

=> Gradle for java : https://docs.gradle.org/current/userguide/multi_project_builds.html

=> Gradle/Groovy Udacity course by google : https://github.com/udacity/ud867/blob/master/1.11-Exercise-ConfigureFileSystemTasks/solution.gradle


=> See JMonkeyEngine Desktop Example : https://github.com/Scrappers-glitch/basic-gradle-template

=> See JMonkeyEngine RPI armhf Desktop Example : https://github.com/Scrappers-glitch/JmeCarPhysicsTestRPI



SPRINT 4

Added observer pattern Hover Listener (includes design pattern added from last sprint). Lift/drop functionality stays the same, TileHoverListener is implemented so simpleUpdate reports results to the hovering subject instead of actually changing tile's positions.



////////////////////////////////////////////////////////////////////////////////////
SPRINT 2:


Requirements: -Java 21

Run from root folder cowboymahjong: ./gradlew run

on Mac, if GLFW throws a thread error, run from Eclipse with VM Argument -XstartOnFirstThread

Project: Cowboy Mahjong -A simple Western-themed 3D Mahjong Game in Java made using jMonkeyEngine

////////////////////////////////////////////////////////////////////////////////////

Sprint 1:

Checklist: – Are you in a Group?

No

– If so, who else is in your group?

N/A

– Do you have your GitHub account set up?

Yes

– Do you have a public repository for your Project?

Yes

– What is the link to your GitHub repository?

https://github.com/weaseltears/350Project

– If you are in a group, does everyone have write access to the github repo?

N/A

– Do you have a “Hello World” program that compiles and runs?

Yes

– Where is the entry point to your project? (src/main/Main.java for example)

350project/TestProject/src/Main.java
