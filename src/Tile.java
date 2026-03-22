import java.awt.*;

public interface Tile {
    void update();
    void render(Graphics g);
    void deactivateTile();
    boolean passedWithoutPress();
}
