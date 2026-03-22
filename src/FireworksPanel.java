import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

//This file is responsible for the firework panel we see on the screen

public class FireworksPanel extends JPanel {
    private List<FireworkParticle> particles;
    private Random rand;

    public FireworksPanel() {
        particles = new ArrayList<>();
        rand = new Random();

        Timer timer = new Timer(16, e -> {
            updateParticles();
            repaint();
        });
        timer.start();
    }

    public void launchFirework() {
        int numParticles = 100;
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;
        for (int i = 0; i < numParticles; i++) {
            double angle = 2 * Math.PI * rand.nextDouble();
            double speed = 2 + 2 * rand.nextDouble();
            double vx = speed * Math.cos(angle);
            double vy = speed * Math.sin(angle);
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            particles.add(new FireworkParticle(centerX, centerY, vx, vy, color));
        }
    }

    private void updateParticles() {
        List<FireworkParticle> aliveParticles = new ArrayList<>();
        for (FireworkParticle particle : particles) {
            particle.update();
            if (particle.isAlive()) {
                aliveParticles.add(particle);
            }
        }
        particles = aliveParticles;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (FireworkParticle particle : particles) {
            particle.render(g2d);
        }
    }
}

