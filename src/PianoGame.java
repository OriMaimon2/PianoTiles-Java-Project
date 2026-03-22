import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

//file of the game itself - after you choose a song

public class PianoGame extends MouseAdapter {
    private ArrayList<BaseTile> tiles;
    private final Tile line, vertLine1, vertLine2, vertLine3;
    private BaseTile failTile;

    public static int TOTAL_POINTS = 0;
    private int prevX = -1;                  // X position of previously created tile
    private int counter = 0;                // Counter for tile creation
    private int difficulty = 0;             // Game difficulty
    private boolean difficultyFlag = false; // Flag to increase difficulty

    public static boolean resetFlag = false;// Flag to reset game

    public PianoGame() {
        this.tiles = new ArrayList<>();
        this.line = new BlackTile(0, 650, 400, 15, true);
        this.vertLine1 = new BlackTile(99, 0, 2, 800, true);
        this.vertLine2 = new BlackTile(199, 0, 2, 800, true);
        this.vertLine3 = new BlackTile(299, 0, 2, 800, true);
    }

    // Method to handle mouse press
    public void mousePressed(MouseEvent e) {
        if (Display.gameState != Display.STATE.Game)
        {
            return;
        }
        AudioPlayer.playMouseSound();
        int x = e.getX(), y = e.getY();
        for (BaseTile tile : tiles) {
            if (tile.isActive() && tile.getXPos() <= x && x <= tile.getXPos() + tile.getW()
                    && tile.getYPos() - 5 <= y && y <= tile.getYPos() + tile.getH() + 5) {
                tile.deactivateTile();
                tile.onTilePressed();
                if (TOTAL_POINTS % 100 == 0 && difficulty <= 10) {
                    difficultyFlag = true;
                }
            }
        }
    }

    // Method to randomly create a new tile
    void createAndAddTile() {
        Random rand = new Random();
        boolean isSpecial = rand.nextInt(10) == 0; // 10% chance for special tile
        int starting_X = rand.nextInt(4);
        // Decrease chance of two concurrent tiles being in the same lane
        if (starting_X == prevX) {
            if (rand.nextInt(10) <= 8) starting_X = rand.nextInt(4);
        }
        prevX = starting_X;
        int height = 80 + rand.nextInt(221); // Random height between 80 and 300

        BaseTile tile = isSpecial
                ? new SpecialTile(starting_X * 100, -height, 100, height)
                : new BlackTile(starting_X * 100, -height, 100, height, false);

        tiles.add(tile);
    }

    void update() {
        // Move tiles down the screen
        for (BaseTile tile : tiles) {
            tile.update();
            // Check if a tile has reached the bottom
            if (tile.passedWithoutPress()) {
                try {
                    File file = new File("res/BestScore.txt");

                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        // Read the first line from the file
                        String firstLine = reader.readLine();
                        int currentBestScore = Integer.parseInt(firstLine.trim());

                        // Check if TOTAL_POINTS is greater than the current best score
                        if (TOTAL_POINTS > currentBestScore) {
                            // Replace the content of the file with TOTAL_POINTS
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                writer.write(String.valueOf(TOTAL_POINTS));
                            }
                        }
                    } catch (IOException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                    failTile = tile;
                    Display.gameState = Display.STATE.End;
                    AudioPlayer.stopMusic();
                    AudioPlayer.playFireworkSound();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        // Remove tiles that are out of bounds
        tiles.removeIf(tile -> tile.getYPos() > Display.HEIGHT);

        // Create new tiles
        counter++;
        if (counter % (90 - difficulty * 7) == 0){
            counter = 0;
            createAndAddTile();
        }

        // Increase difficulty
        if (difficultyFlag){
            difficulty ++;
            BlackTile.SPEED ++;
            difficultyFlag = false;
        }
    }

    public void render(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Display.WIDTH, Display.HEIGHT);

        // Draw all tiles
        for (BaseTile tile : tiles) {
            tile.render(g);
        }

        // Draw the current total points
        g.setColor(Color.blue);
        g.setFont(new Font("Ariel", Font.BOLD, 30));
        g.drawString("SCORE: " + TOTAL_POINTS, 120, 35);

        // Draw the finish line
        line.render(g);
        vertLine1.render(g);
        vertLine2.render(g);
        vertLine3.render(g);
    }

    public Tile[] getFailAndLineTiles(){
        Tile[] t = new Tile[2];
        t[0] = failTile;
        t[1] = line;
        return t;
    }

    // Method to prepare the for next game
    public void resetGame(){
        tiles.clear();
        counter = 0;
        difficulty = 0;
        difficultyFlag = false;
        failTile = null;
        prevX = -1;
        EndMenu.fireCounter = 0;
    }
}
 