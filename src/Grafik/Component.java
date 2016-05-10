package SwingTest;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Victor on 2016-05-01.
 */
public class Component extends JComponent{

    public Game sim;
    public static final int HEIGHT = 640;
    public static final int WIDTH = 960;

    public Component(Game sim) {
        this.sim = sim;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        g2.setColor(Color.WHITE);
        g2.fillOval(sim.getPosition().x, sim.getPosition().y, sim.getRadius(), sim.getRadius());

    }
}
