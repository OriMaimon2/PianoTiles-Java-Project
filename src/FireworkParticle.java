import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

//This file is responsible for the firework particle we see on the screen


public class FireworkParticle {
    private double x, y;
    private double vx, vy;
    private float alpha;
    private Color color;

    public FireworkParticle(double x, double y, double vx, double vy, Color color) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.alpha = 1.0f;
        this.color = color;
    }

    public boolean isAlive() {
        return alpha > 0;
    }

    public void update() {
        x += vx;
        y += vy;
        alpha -= 0.02f;
    }

    public void render(Graphics2D g) {
        if (alpha <= 0) return;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(color);
        g.fillOval((int) x, (int) y, 5, 5);
    }
}
