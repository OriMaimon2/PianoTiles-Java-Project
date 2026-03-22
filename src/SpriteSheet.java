import java.awt.image.BufferedImage;

//taking care of the SpriteSheet

public class SpriteSheet {

    private BufferedImage sprite;

    public SpriteSheet(BufferedImage ss) {
        this.sprite = ss;
    }

    // SpriteSheet is divided into 32x32 images
    public BufferedImage grabImage(int col, int row, int width, int height){
        return sprite.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
    }
}
