import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

//file for the song menu - drawing it and saving the choice

public class SongsMenu extends MouseAdapter {

    private Boolean[] mouseHover = new Boolean[5];
    /*
    0 - Back
    1 - Despacito
    2- Gangam Style
    3 - Blinding lights
    4 - Levitating
    */

    public SongsMenu() {
        Arrays.fill(mouseHover, false);
    }

    public void render(Graphics g) {
        int q=192;
        int j=64;

        g.setFont(Display.largeFont);
        g.setColor(Color.black);
        g.drawString("Songs", Display.WIDTH / 2 - 130, 100);
        g.drawString("Menu", Display.WIDTH / 2 - 110, 180);

        g.setFont(Display.smallFont);
        if (mouseHover[1]) g.setColor(Display.orange);
        else g.setColor(Color.black);
        g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) - 60, q, j);
        g.drawString("Despacito", Display.WIDTH / 2 - 70, Display.HEIGHT / 2 - j + 10 - 60);

        if (mouseHover[2]) g.setColor(Display.orange);
        else g.setColor(Color.black);
        g.setFont(Display.tinyFont);
        g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 20, q, j);
        g.drawString("Gangam Style", Display.WIDTH / 2 - 80, Display.HEIGHT / 2 - j + 10 + 20);

        if (mouseHover[3]) g.setColor(Display.orange);
        else g.setColor(Color.black);
        g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 100, q, j);
        g.drawString("Blinding Lights", Display.WIDTH / 2 - 88, Display.HEIGHT / 2 - j + 10 + 100);

        if (mouseHover[4]) g.setColor(Display.orange);
        else g.setColor(Color.black);
        g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 180, q, j);
        g.drawString("Levitating", Display.WIDTH / 2 - 58, Display.HEIGHT / 2 - j + 10 + 180);

        if (mouseHover[0]) g.setColor(Display.orange);
        else g.setColor(Color.black);
        g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 330, q, j);
        g.drawString("Back", Display.WIDTH / 2 - 30, Display.HEIGHT / 2 - j + 10 + 330);

    }

    // Helper method to tell if the mouse is in a certain area on screen
    private boolean mouseOver(int mx, int my, int x, int y, int w, int h){
        if(mx > x && mx < (x + w)){
            return my > y && my < y + h;
        } return false;
    }

    // Method to handle mouse press
    public void mousePressed(MouseEvent e) {
        if (Display.gameState != Display.STATE.Songs) {
            return;
        }
        AudioPlayer.playMouseSound();
        if (mouseHover[1]){
            AudioPlayer.stopMusic();
            AudioPlayer.playMusic(1);
            Display.gameState = Display.STATE.Game;
        }
        else if (mouseHover[2]){
            AudioPlayer.stopMusic();
            AudioPlayer.playMusic(2);
            Display.gameState = Display.STATE.Game;
        }
        else if (mouseHover[3]){
            AudioPlayer.stopMusic();
            AudioPlayer.playMusic(3);
            Display.gameState = Display.STATE.Game;
        }
        else if (mouseHover[4]){
            AudioPlayer.stopMusic();
            AudioPlayer.playMusic(4);
            Display.gameState = Display.STATE.Game;
        }
        else if (mouseHover[0]){
            Display.gameState = Display.STATE.Menu;
        }

        Arrays.fill(mouseHover, false);
    }

    // Method to handle mouse movement
    public void mouseMoved(MouseEvent e) {
        if (Display.gameState != Display.STATE.Songs) {
            return;
        }

        int mx = e.getX();
        int my = e.getY();
        int q=192;
        int j=64;

        mouseHover[0] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 330, q, j);
        mouseHover[1] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) - 60, q, j);
        mouseHover[2] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 20, q, j);
        mouseHover[3] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 100, q, j);
        mouseHover[4] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2) + 180, q, j);
    }
}
