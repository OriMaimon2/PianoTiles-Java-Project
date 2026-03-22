import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

//This file is responsible for loading the png image to the screen

public class BufferedImageLoader {

    BufferedImage image;

    // Simple method to grab png image into BufferedImage
    public BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                throw new IOException("Resource not found: " + path);
            }
            image = ImageIO.read(resource);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
