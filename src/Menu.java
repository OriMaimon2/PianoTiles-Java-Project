import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

//This file is responsible for the menu that shows up on the screen
//That means drawing the menu and taking care of button presses

public class Menu extends MouseAdapter {

    // States for different features in the menu screen
    private enum MENU_STATE {
        Menu,
        Settings,
        Rules;
    }

    private MENU_STATE menu_state = MENU_STATE.Menu;    // Initialize screen to menu
    private BufferedImage[] images;
    private Boolean[] mouseHover = new Boolean[21];      // Each element corresponds to a button on screen, indicating if the mouse is hovering over it
    /*
    0 - Settings button
    1 - Play button
    2 - Rules button
    3 - Quit button
    4 - Back button
    5 - Music volume button 1
    6 - Music volume button 2
    7 - Music volume button 3
    8 - Music volume button 4
    9 - Sound volume button 1
    10 - Sound volume button 2
    11 - Sound volume button 3
    12 - Sound volume button 4
     */
    
    public Menu() {
        //start song
        AudioPlayer.playMusic(0);

        SpriteSheet spriteSheet = new SpriteSheet(Display.sprite_sheet);

        // Load all images that are relevant to the menu
        images = new BufferedImage[13];
        for (int i = 0; i < 5; i++) {
            images[i] = spriteSheet.grabImage(i + 1, 1, 32, 32);
            images[i + 5] = spriteSheet.grabImage(i + 1, 2, 32, 32);
        }
        images[10] = spriteSheet.grabImage(6, 1, 64, 64);
        images[11] = spriteSheet.grabImage(1, 3, 64, 64);
        images[12] = spriteSheet.grabImage(4, 3, 160, 96);
        /*
        0 - Non hovered mute image
        1 - Non hovered low image
        2 - Non hovered medium image
        3 - Non hovered high image
        4 - Non hovered settings image
        5 - Hovered mute image
        6 - Hovered low image
        7 - Hovered medium image
        8 - Hovered high image
        9 - Hovered settings image
        10 - 12 are for decoration
        */

        Arrays.fill(mouseHover, false);
    }

    public void update() {

    }

    public void render(Graphics g) {
        int q=192;
        int j=64;
        
        switch (menu_state) {
            case Menu -> {
                if (mouseHover[0]) g.drawImage(images[9], 10, 10, null); // 
                else g.drawImage(images[4], 10, 10, null);
                g.setFont(Display.largeFont);
                g.setColor(Color.black);
                g.drawString("Piano", Display.WIDTH / 2 - 110, 100);
                g.drawString("Tiles", Display.WIDTH / 2 - 100, 180);
                g.drawImage(images[10], Display.WIDTH / 2 - 180, 70, null);
                g.drawImage(images[11], Display.WIDTH / 2 + 100, 150, null);
                g.drawImage(images[12], Display.WIDTH / 2 - 100, 200, null);
                g.setFont(Display.smallFont);
                if (mouseHover[1]) g.setColor(Display.orange);
                else g.setColor(Color.black);
                g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2), q, j);
                g.drawString("Play", Display.WIDTH / 2 - 30, Display.HEIGHT / 2 - j + 10);
                if (mouseHover[2]) g.setColor(Display.orange);
                else g.setColor(Color.black);
                g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2, q, j);
                g.drawString("Rules", Display.WIDTH / 2 - 40, Display.HEIGHT / 2 + (j / 2) + 10);
                if (mouseHover[3]) g.setColor(Display.orange);
                else g.setColor(Color.black);
                g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 + (j * 3 / 2), q, j);
                g.drawString("Quit", Display.WIDTH / 2 - 30, Display.HEIGHT / 2 + j * 2 + 10);
            }
            case Settings -> {
                g.setFont(Display.mediumFont);
                g.setColor(Color.BLACK);
                g.drawString("Music Volume ", 20, 100);
                g.drawString("Sound Volume ", 10, 300);

                if (mouseHover[5]) g.drawImage(images[5], 40, 140, null);
                else g.drawImage(images[0], 40, 140, null);
                if (mouseHover[6]) g.drawImage(images[6], 120, 140, null);
                else g.drawImage(images[1], 120, 140, null);
                if (mouseHover[7]) g.drawImage(images[7], 200, 140, null);
                else g.drawImage(images[2], 200, 140, null);
                if (mouseHover[8]) g.drawImage(images[8], 280, 140, null);
                else g.drawImage(images[3], 280, 140, null);
                if (mouseHover[9]) g.drawImage(images[5], 40, 340, null);
                else g.drawImage(images[0], 40, 340, null);
                if (mouseHover[10]) g.drawImage(images[6], 120, 340, null);
                else g.drawImage(images[1], 120, 340, null);
                if (mouseHover[11]) g.drawImage(images[7], 200, 340, null);
                else g.drawImage(images[2], 200, 340, null);
                if (mouseHover[12]) g.drawImage(images[8], 280, 340, null);
                else g.drawImage(images[3], 280, 340, null);

                if (mouseHover[4]) g.setColor(Display.orange);
                else g.setColor(Color.black);
                g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT - j * 2 - 70, q, j);
                g.drawString("Back", Display.WIDTH / 2 - 60, Display.HEIGHT - j * 3 / 2 + 18 - 70);
            }
            case Rules -> {
                g.setFont(Display.largeFont);
                g.setColor(Color.black);
                g.drawString("Rules", Display.WIDTH / 2 - 110, 100);

                //The rules:
                g.setFont(Display.tinyFont2);
                g.setColor(Color.black);
                g.drawString("In the song selection screen you", Display.WIDTH / 2 - 140, 150);
                g.drawString("can choose a song.", Display.WIDTH / 2 - 140, 170);
                g.drawString("After the selection, the game will start.", Display.WIDTH / 2 - 140, 190);

                g.drawString("Every few moments new tiles will", Display.WIDTH / 2 - 140, 230);
                g.drawString("appear and your goal is to click", Display.WIDTH / 2 - 140, 250);
                g.drawString("on as many of them as possible", Display.WIDTH / 2 - 140, 270);
                g.drawString("and earn a score.", Display.WIDTH / 2 - 140, 290);

                g.drawString("From time to time red tiles", Display.WIDTH / 2 - 140, 330);
                g.drawString("will appear that have a higher score.", Display.WIDTH / 2 - 140, 350);

                g.drawString("Good luck and have fun playing!", Display.WIDTH / 2 - 140, 390);


                if (mouseHover[4]) g.setColor(Display.orange);
                else g.setColor(Color.black);
                g.drawRect(Display.WIDTH / 2 - q / 2, Display.HEIGHT - j * 2 - 70, q, j);
                g.setFont(Display.mediumFont);
                g.drawString("Back", Display.WIDTH / 2 - 60, Display.HEIGHT - j * 3 / 2 + 18 - 70);
            }
        }
    }

    // Helper method to tell if the mouse is in a certain area on screen
    private boolean mouseOver(int mx, int my, int x, int y, int w, int h){
        if(mx > x && mx < (x + w)){
            return my > y && my < y + h;
        } return false;
    }

    // Method to handle mouse press
    public void mousePressed(MouseEvent e) {
        if (Display.gameState != Display.STATE.Menu) {
            return;
        }

        AudioPlayer.playMouseSound();
        switch (menu_state) {
            case Menu -> {
                if (mouseHover[0]) menu_state = MENU_STATE.Settings;
                else if (mouseHover[1]) Display.gameState = Display.STATE.Songs;
                else if (mouseHover[2]) menu_state = MENU_STATE.Rules;
                else if (mouseHover[3]){
                    System.exit(0);
                }
            }
            case Settings -> {
                if (mouseHover[5]) AudioPlayer.setMusicVolume(0);
                else if (mouseHover[6]) AudioPlayer.setMusicVolume(0.2);
                else if (mouseHover[7]) AudioPlayer.setMusicVolume(0.6);
                else if (mouseHover[8]) AudioPlayer.setMusicVolume(1);
                else if (mouseHover[9]) AudioPlayer.setSoundVolume(0);
                else if (mouseHover[10]) AudioPlayer.setSoundVolume(0.333);
                else if (mouseHover[11]) AudioPlayer.setSoundVolume(0.666);
                else if (mouseHover[12]) AudioPlayer.setSoundVolume(1);
                else if (mouseHover[4]) menu_state = MENU_STATE.Menu;
            }
            case Rules -> {
                if (mouseHover[4]) menu_state = MENU_STATE.Menu;
            }
        }
        Arrays.fill(mouseHover, false);
    }

    // Method to handle mouse movement
    public void mouseMoved(MouseEvent e) {
        if (Display.gameState != Display.STATE.Menu) {
            return;
        }

        int mx = e.getX();
        int my = e.getY();
        int q=192;
        int j=64;

        switch (menu_state) {
            case Menu -> {
                mouseHover[0] = mouseOver(mx, my, 10, 10, 32, 32);
                mouseHover[1] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 - (j * 3 / 2), q, j);
                mouseHover[2] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2, q, j);
                mouseHover[3] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT / 2 + (j * 3 / 2), q, j);
            }
            case Settings -> {
                mouseHover[5] = mouseOver(mx, my, 40, 140, 32, 32);
                mouseHover[6] = mouseOver(mx, my, 120, 140, 32, 32);
                mouseHover[7] = mouseOver(mx, my, 200, 140, 32, 32);
                mouseHover[8] = mouseOver(mx, my, 280, 140, 32, 32);
                mouseHover[9] = mouseOver(mx, my, 40, 340, 32, 32);
                mouseHover[10] = mouseOver(mx, my, 120, 340, 32, 32);
                mouseHover[11] = mouseOver(mx, my, 200, 340, 32, 32);
                mouseHover[12] = mouseOver(mx, my, 280, 340, 32, 32);
                mouseHover[4] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT - j * 2 - 70, q, j);
            }
            case Rules -> mouseHover[4] = mouseOver(mx, my, Display.WIDTH / 2 - q / 2, Display.HEIGHT - (j * 2) - 70, q, j);
        }
    }
}
