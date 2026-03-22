import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

//with this file we run the game

public class Display extends Canvas implements Runnable {

    public static final int WIDTH = 400, HEIGHT = 800;   // Screen dimensions

    // Define fonts and colors
    public static final Font tinyFont = new Font("arial", Font.BOLD, 25);
    public static final Font tinyFont2 = new Font("arial", Font.BOLD, 15);
    public static final Font smallFont = new Font("arial", Font.BOLD, 30);
    public static final Font mediumFont = new Font("arial", Font.BOLD, 50);
    public static final Font largeFont = new Font("arial", Font.BOLD, 80);
    public static final Color orange = new Color(255, 100, 0);


    public static JFrame frame;
    private String title = "Piano Tiles";           // Screen title

    private Thread thread;
    private boolean running = true;

    // States for different aspects of the game
    public enum STATE {
        Menu,
        Songs,
        Game,
        End;
    }

    public static STATE gameState = STATE.Menu;     // Initialize display to menu screen
    public static BufferedImage sprite_sheet;       // Game sprite sheet

    private Menu menu;
    private SongsMenu songsMenu;
    private PianoGame pianoGame;
    private EndMenu endMenu;

    public Display() {
        // Initialize screen and set attributes
        frame = new JFrame(title);
        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        frame.setPreferredSize(dimension);
        frame.setMinimumSize(dimension);
        frame.setMaximumSize(dimension);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);

        // Initialize different game features
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            sprite_sheet = loader.loadImage("/sprite_sheet.png");
            System.out.println("Images loaded");
        }catch(Exception e){
            e.printStackTrace();
        }

        menu = new Menu();
        songsMenu = new SongsMenu();
        pianoGame = new PianoGame();
        endMenu = new EndMenu();

        this.addMouseListener(menu);
        this.addMouseMotionListener(menu);
        this.addMouseListener(songsMenu);
        this.addMouseMotionListener(songsMenu);
        this.addMouseListener(pianoGame);
        this.addMouseMotionListener(pianoGame);
        this.addMouseListener(endMenu);
        this.addMouseMotionListener(endMenu);
    }


    private void init() {
        // Initialize game objects

    }

    synchronized void start() {
        // Initialize game thread
        thread = new Thread(this, "Thread");
        thread.start();
        running = true;
    }

    synchronized void stop() {
        // Handle thread termination
        try {
            thread.join();
            running = false;
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Game loop
        this.requestFocus();
        long lastTime = System.nanoTime();
        final double fps = 60.0;
        double ns = Math.pow(10, 9) / fps;
        double delta = 0.0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        init();

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                update();
                render();
                delta--;
                frames++;
            }

            // Display fps
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.frame.setTitle(title + "      |      " + frames + " fps");
                frames = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) throws IOException {
        // Display screen
        Display display = new Display();
        display.start();
    }

    private void update() {
        switch (gameState) {
            case Game -> pianoGame.update();
            case End -> endMenu.update();
        }

        if (PianoGame.resetFlag){
            pianoGame.resetGame();
            PianoGame.resetFlag = false;
        }
    }

    private void render() {
        // Create graphics object
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();


        // Draw to screen
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        switch (gameState){
            case Menu -> menu.render(g);
            case Songs -> songsMenu.render(g);
            case Game -> pianoGame.render(g);
            case End -> {
                Tile[] t = pianoGame.getFailAndLineTiles();
                if (t[0] != null) t[0].render(g);
                t[1].render(g);
                endMenu.render(g);
            }
        }

        g.dispose();
        bs.show();
    }
}