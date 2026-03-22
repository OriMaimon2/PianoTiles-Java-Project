# PianoTiles

A Java desktop rhythm game inspired by Piano Tiles.

The player clicks falling tiles in time with music, gains points, and tries to beat their personal best score. The project includes a full menu flow (main menu, song selection, gameplay, and end screen), adjustable audio levels, and visual end-game fireworks.

## Table of Contents

1. Overview
2. Features
3. Gameplay Rules
4. Controls
5. Tech Stack
6. Project Structure
7. How to Run
8. Assets and Resources
9. Scoring and Difficulty Logic
10. Game State Flow
11. Known Limitations
12. Suggested Improvements

## Overview

`PianoTiles` is a Java Swing/AWT game that runs in a fixed `400x800` window at a target of 60 FPS.

Main gameplay loop:
1. Start in the main menu.
2. Open song selection and choose a music track.
3. Click falling tiles before they cross the finish line.
4. If any active tile passes the finish line, the run ends.
5. Final score is shown, best score is persisted to `res/BestScore.txt`.

## Features

- Multi-screen flow:
  - Main menu
  - Settings
  - Rules
  - Song selection
  - Active gameplay
  - End screen
- 4 selectable songs plus menu background music.
- Adjustable music volume and sound-effect volume.
- Two tile types:
  - Black tile (regular points)
  - Red special tile (bonus points)
- Progressive difficulty that increases during a run.
- Personal best score persistence in a text file.
- Firework particle animation and sound on game over.

## Gameplay Rules

- Tiles fall from the top in 4 vertical lanes.
- Click active tiles to score points.
- Missing any active tile (it reaches/passes the finish line) ends the game.
- Special red tiles appear randomly and are worth more points.
- As score increases, tile speed and spawn rate become more challenging.

## Controls

- Mouse only.
- Main menu:
  - `Play` -> opens song selection
  - `Rules` -> opens rules screen
  - `Settings` (gear icon) -> audio settings
  - `Quit` -> closes the game
- Song menu:
  - Click a song to start a run
  - `Back` -> return to main menu
- End menu:
  - `Play Again` -> reset score and return to song selection
  - `Back To Menu` -> reset score and return to main menu
  - `Quit` -> closes the game

## Tech Stack

- Language: Java
- UI/Rendering: AWT + Swing (`Canvas`, `JFrame`, `BufferStrategy`)
- Audio: `javax.sound.sampled` (`Clip`, `AudioInputStream`, `FloatControl`)
- Images: `BufferedImage`, sprite-sheet slicing
- Data persistence: plain text file (`res/BestScore.txt`)

## Project Structure

```text
PianoTiles/
  src/
    Display.java              # Entry point and game loop (main)
    Menu.java                 # Main menu, settings, and rules screen logic
    SongsMenu.java            # Song selection screen
    PianoGame.java            # Core gameplay, tile spawning, scoring, difficulty
    EndMenu.java              # End screen, score display, restart/menu actions
    AudioPlayer.java          # Background music and sound effects manager
    Tile.java                 # Tile interface
    BaseTile.java             # Abstract tile base class
    BlackTile.java            # Regular tile implementation
    SpecialTile.java          # Bonus (red) tile implementation
    FireworkParticle.java     # Single particle for fireworks effect
    FireworksPanel.java       # Standalone firework panel (not wired to main flow)
    BufferedImageLoader.java  # Resource image loading utility
    SpriteSheet.java          # Sprite extraction helper

  res/
    sprite_sheet.png
    menu_music.wav
    Despacito.wav
    Gangam.wav
    Lights.wav
    Levitating.wav
    mouse_click.wav
    fireworks.wav
    BestScore.txt
```

## How to Run

### Option 1: IntelliJ IDEA (recommended)

1. Open the `PianoTiles` folder in IntelliJ IDEA.
2. Ensure a modern JDK is configured (Java 17+ recommended).
3. Mark `src` as Sources Root (usually automatic).
4. Mark `res` as Resources Root so assets are available on classpath.
5. Run `Display.main()`.

### Option 2: Command Line (Windows CMD)

From inside the `PianoTiles` directory:

```cmd
mkdir out\classes
javac -d out\classes src\*.java
java -cp "out\classes;res" Display
```

Notes:
- The classpath includes `res` so `sprite_sheet.png` can be loaded via `getResource("/sprite_sheet.png")`.
- Audio and best score files are also read from the `res` directory.

## Assets and Resources

- `sprite_sheet.png`: UI icons and decorations used in menus.
- Music tracks:
  - `menu_music.wav`
  - `Despacito.wav`
  - `Gangam.wav`
  - `Lights.wav`
  - `Levitating.wav`
- Sound effects:
  - `mouse_click.wav`
  - `fireworks.wav`
- Persistent data:
  - `BestScore.txt` (single integer value for personal best)

## Scoring and Difficulty Logic

- Black tile: `+10` points.
- Special red tile: `+50` points.
- Special tile chance: about `10%` on spawn.
- Difficulty progression:
  - Every `100` points, difficulty increases.
  - Tile speed (`BlackTile.SPEED`) increases.
  - Spawn interval becomes shorter (`90 - difficulty * 7`).
- Loss condition:
  - Any active tile passing the finish line triggers game over.

## Game State Flow

`Display.STATE` drives screen behavior:

1. `Menu`
2. `Songs`
3. `Game`
4. `End`

Transitions:
- `Menu -> Songs` via Play
- `Songs -> Game` via song selection
- `Game -> End` when a tile is missed
- `End -> Songs` via Play Again
- `End -> Menu` via Back To Menu

## Known Limitations

- Input is mouse-only.
- No pause/resume during gameplay.
- Song metadata/duration is not shown.
- All resources are expected in local `res` paths.
- Volume methods assume audio clips are initialized before interaction.
- Some naming inconsistencies exist in asset/class comments (for example, `Gangam` spelling).

## Suggested Improvements

- Add keyboard/touch controls.
- Add pause, countdown, and combo/multiplier mechanics.
- Add lane hit feedback and particle effects on tile click.
- Add settings persistence (store volumes between sessions).
- Improve error handling for missing/corrupted assets.
- Add packaging/build scripts (Maven/Gradle) and executable JAR support.
- Add tests for non-UI logic (scoring, spawn behavior, difficulty thresholds).

