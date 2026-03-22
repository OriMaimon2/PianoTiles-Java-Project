import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

//This file is responsible for the menu in the end of each game

public class EndMenu extends MouseAdapter {

    private File file = new File("res/BestScore.txt");

    public static int fireCounter = 0;
    private List<FireworkParticle> particles = new ArrayList<>();
    private Random rand = new Random();

    private Boolean[] mouseHover = new Boolean[3];
    /*
    0 - Play again
    1 - Back to menu
    2 - Quit
    */

    public EndMenu() {
        Arrays.fill(mouseHover, false);
    }

    public void update(){
        if(fireCounter <= 150)
            launchFirework();
            fireCounter++;
        List<FireworkParticle> aliveParticles = new ArrayList<>();
        for (FireworkParticle particle : particles) {
            particle.update();
            if (particle.isAlive()) {
                aliveParticles.add(particle);
            }
        }
        particles = aliveParticles;
    }

    public void render(Graphics g) {
        int q=192;
        int j=64;

        Graphics2D g2d = (Graphics2D) g;
        for (FireworkParticle particle : particles) {
            particle.render(g2d);
        }

        g.setFont(Display.mediumFont);
        g.setColor(Color.BLUE);
        g.drawString("Final Score:", Display.WIDTH / 2 - 145, 60);
        g.drawString(""+PianoGame.TOTAL_POINTS, Display.WIDTH / 2 - 10 - 10 * String.valueOf(PianoGame.TOTAL_POINTS).length(), 113);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Read the first line from the file
            String firstLine = reader.readLine();
            int bestScore = Integer.parseInt(firstLine.trim());

            g.setColor(new Color(92, 127, 255));
            g.setFont(Display.smallFont);
            g.drawString("Personal best: ", Display.WIDTH / 2 - 140, 150);
            g.drawString(" "+bestScore, Display.WIDTH / 2 + 70, 151);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        g.setFont(Display.tinyFont);
        if (mouseHover[0]) g.setColor(Display.orange);
        else g.setColor(Color.black);
        g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) - 100, q, j);
        g.drawString("Play Again", Display.WIDTH / 2 - 64, Display.HEIGHT / 2 - j + 10 - 100);

        if (mouseHover[1]) g.setColor(Display.orange);
        else g.setColor(Color.black);
        g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) - 20, q, j);
        g.drawString("Back To Menu", Display.WIDTH / 2 - 83, Display.HEIGHT / 2 - j + 10 - 20);

        if (mouseHover[2]) g.setColor(Display.orange);
        else g.setColor(Color.black);
        g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 60, q, j);
        g.drawString("Quit", Display.WIDTH / 2 - 28, Display.HEIGHT / 2 - j + 10 + 60);
    }

    // Helper method to tell if the mouse is in a certain area on screen
    private boolean mouseOver(int mx, int my, int x, int y, int w, int h){
        if(mx > x && mx < (x + w)){
            return my > y && my < y + h;
        } return false;
    }

    // Method to handle mouse press
    public void mousePressed(MouseEvent e) {
        if (Display.gameState != Display.STATE.End) {
            return;
        }
        AudioPlayer.playMouseSound();
        if (mouseHover[0]){
            PianoGame.resetFlag = true;
            PianoGame.TOTAL_POINTS = 0;
            Display.gameState = Display.STATE.Songs;
        }
        else if (mouseHover[1]){
            PianoGame.resetFlag = true;
            PianoGame.TOTAL_POINTS = 0;
            Display.gameState = Display.STATE.Menu;
        }
        else if (mouseHover[2]){
            System.exit(0);
        }

        Arrays.fill(mouseHover, false);
    }

    // Method to handle mouse movement
    public void mouseMoved(MouseEvent e) {
        if (Display.gameState != Display.STATE.End) {
            return;
        }

        int mx = e.getX();
        int my = e.getY();
        int q=192;
        int j=64;

        mouseHover[0] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) - 100, q, j);
        mouseHover[1] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) - 20, q, j);
        mouseHover[2] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 60, q, j);
    }
    public void launchFirework() {
        int numParticles = 100;
        double centerX = Display.WIDTH / 2.0;
        double centerY = Display.HEIGHT / 2.0;
        for (int i = 0; i < numParticles; i++) {
            double angle = 2 * Math.PI * rand.nextDouble();
            double speed = 2 + 2 * rand.nextDouble();
            double vx = speed * Math.cos(angle);
            double vy = speed * Math.sin(angle);
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            particles.add(new FireworkParticle(centerX, centerY, vx, vy, color));
        }
    }

}
