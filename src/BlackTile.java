import java.awt.*;

//This file is responsible for specific tile - the black one!


public class BlackTile extends BaseTile{

    private final int POINTS = 10;
    public static int SPEED = 1;
    private boolean stationary;

    public BlackTile(int x, int y, int width, int height, boolean stationary)
    {
        super(x, y, width, height);
        activate();
        this.stationary = stationary;
    }

    @Override
    public void update() {
        incYPos(SPEED);
        if (getYPos() > 650)       // Tile reached the bottom
            deactivateTile();
    }

    @Override
    public void render(Graphics g) {
        if (isActive())
        {
            g.setColor(Color.BLACK);
            g.fillRect(getXPos(), getYPos(), getW(), getH());
            if (!stationary){
                g.setColor(new Color(60, 61, 62));
                g.fillRect(getXPos(), getYPos(), getW(), 8);
                g.fillRect(getXPos(), getYPos() + getH(), getW(), -8);
            }
        }
        else {
            g.setColor(new Color(175, 175, 175));
            g.fillRect(getXPos(), getYPos(), getW(), getH());
        }
    }

    @Override
    public void onTilePressed() {
        PianoGame.TOTAL_POINTS += POINTS;
    }

    public int getPoints() {return this.POINTS; }


    @Override
    public boolean passedWithoutPress() {
        if (getYPos() + getH() > 650 && isActive())
            return true;
        return false;
    }
}
