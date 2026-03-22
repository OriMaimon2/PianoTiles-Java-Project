
//This file is responsible for the tiles that the game is made with (their point rates)

public abstract class BaseTile implements Tile {
    private int xPos;
    private int yPos;
    private int w;
    private int h;
    private boolean isActive;

    public BaseTile(int x, int y, int w, int h) {
        this.xPos = x;
        this.yPos = y;
        this.w = w;
        this.h = h;
        this.isActive = false;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void incYPos(int yInc) {
        this.yPos += yInc;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivateTile() {
        this.isActive = false;
    }

    // Abstract method to be implemented by subclasses
    public abstract void onTilePressed();
}