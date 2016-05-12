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

    /**
     * Connstructs a GameComponent with a game object and a few images
     * This class handles most of the graphics
     * @param game Contains all the information about the state of the game
     */
    public GameComponent(Game game) {
        this.game = game;
        backGround = null;
        ground = null;

        //Loading images into the game
        try {
            backGround = ImageIO.read(new File("data/Game-Background.png"));
        } catch (IOException e) {
            System.out.println("Background image could not be found!");
        }
        try {
            ground = ImageIO.read(new File("data/Ground.png"));
        } catch (IOException e) {
            System.out.println("Ground image could not be found!");
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.drawImage(backGround, 0, 0, WIDTH, HEIGHT, this);

        //Ground
        g2.drawImage(ground, 0, 670, WIDTH, unitConversion(game.getGroundHeight()), this);

        // Ball
        g2.setColor(game.getBall().getColor());
        g2.fillOval(unitConversion(game.getBall().getxPos()), yConversion(game.getBall().getyPos()),
                unitConversion(game.getBall().getDiameter()), unitConversion(game.getBall().getDiameter()));

    }

    /**
     * Converts millimeters into pixels
     * @param millimeters Measurements used in game calculations
     * @return An integer representing pixels
     */
    public int unitConversion(int millimeters) {
        return millimeters/10;
    }

    /**
     * Converts y-coordinates from game into the graphical coordinate system
     * @param millimeters Measurements used in game calculations
     * @return An integer representing pixels
     */
    public int yConversion(int millimeters) {
        return HEIGHT - unitConversion(millimeters);
    }
}