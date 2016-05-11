package Graphics;

import MainGame.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class GameComponent extends JComponent {

    public Game sim;
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;

    public GameComponent(Game sim) {
        this.sim = sim;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.setColor(new Color(194, 218, 249));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Ball
        g2.setColor(Color.WHITE);
        g2.fillOval(sim.getPosition().x, sim.getPosition().y, sim.getRadius(), sim.getRadius());

    }
}
