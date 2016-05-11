package Graphics;

import MainGame.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class GameComponent extends JComponent {

    public Game game;


    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;


    public GameComponent(Game game) {
        this.game = game;
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

        //Ground
        g2.setColor(new Color(1, 186, 0));
        g2.fillRect(0, yConversion(game.getGroundHeight()), unitConversion(game.getGroundWidth()), unitConversion(game.getGroundHeight()));

        // Ball
        g2.setColor(game.getBall().getColor());
        g2.fillOval(unitConversion(game.getBall().getxPos()), yConversion(game.getBall().getyPos()),
                unitConversion(game.getBall().getDiameter()), unitConversion(game.getBall().getDiameter()));

    }
    
    public int unitConversion(int millimeters) {
        return millimeters/10;
    }

    public int yConversion(int millimeters) {
        return HEIGHT - unitConversion(millimeters);
    }
}
