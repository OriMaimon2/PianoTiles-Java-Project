import java.awt.*;

//File that handle specific tile - a special tile that pops up during the game

public class SpecialTile extends BaseTile{

    private final int POINTS = 50;
    private int SPEED = 2 * BlackTile.SPEED;

    public SpecialTile(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        activate();
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
            g.setColor(Color.RED);
            g.fillRect(getXPos(), getYPos(), getW(), getH());
            g.setColor(new Color(255, 90, 90));
            g.fillRect(getXPos(), getYPos(), getW(), 10);
            g.fillRect(getXPos(), getYPos()+getH(), getW(), -10);
        }
        else {
            g.setColor(new Color(231, 136, 136));
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
