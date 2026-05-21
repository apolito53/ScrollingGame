# Scrolling Game

Small Swing side-scroller prototype.

## Run from an IDE

Open this directory as a Maven project and run `ScrollingPanel.Runner`.

The existing NetBeans Ant project files are still present, but the Maven file is the easiest import target for current IDEs.

## Run from the command line

```bash
mvn compile exec:java
```

or build a runnable jar:

```bash
mvn package
java -jar target/scrolling-game-1.0.0-SNAPSHOT.jar
```

## Controls

- `A` or left arrow: move left
- `D` or right arrow: move right
- space or up arrow: jump
- click: add a platform
- `R`: remove platforms
- `V`: stop vertical movement
- `T`: force fall
