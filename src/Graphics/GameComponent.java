package Graphics;

import MainGame.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class GameComponent extends JComponent {

    public Game game;

    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;
    private BufferedImage backGround;
    private BufferedImage ground;

    public GameComponent(Game game) {
        this.game = game;
        backGround = null;
        ground = null;

        try {
            backGround = ImageIO.read(new File("data/Game-Background.png"));
        } catch (IOException e) {}
        try {
            ground = ImageIO.read(new File("data/Ground.png"));
        } catch (IOException e) {}
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.drawImage(backGround, 0, 0, 1280, 720, this);

        //Ground
        g2.drawImage(ground, 0, 670, 1280, 50, this);

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