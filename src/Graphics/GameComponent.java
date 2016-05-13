package Graphics;

import MainGame.Game;
import Physics.GameVector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class GameComponent extends JComponent {

    public Game game;
    public ScrollingBackground scrollingBackground;
    public ScrollingGround scrollingGround;
    // Window bounds
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;
    // Images
    BufferedImage background, ground, heaven, heavenHigher;
    //boolean so you can only give speed to the ball once
    public boolean SPACECLICK = false;
    public long keyPressedMillis;
    public long keyPressed;
    double angularVelocity;

    /**
     * Connstructs a GameComponent with a game object and a few images
     * This class handles most of the graphics
     * @param game Contains all the information about the state of the game
     */
    public GameComponent(Game game) {
        this.game = game;
        setInput();

        try{
            background = ImageIO.read(new File("data/layer-1-double.png"));
        } catch(IOException e){}
        try {
            ground = ImageIO.read(new File("data/layer-2-double.png"));
        } catch (IOException e) {}
        try {
            heaven = ImageIO.read(new File("data/Heaven.png"));
        } catch (IOException e) {}
        try {
            heavenHigher = ImageIO.read(new File("data/HeavenHigher.png"));
        } catch (IOException e) {}

        scrollingBackground = new ScrollingBackground(background);
        scrollingGround = new ScrollingGround(ground);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Background
        scrollingBackground.draw(g2, unitConversion(game.getBall().getVelocity().getX()));

        //Heaven Higher
        g2.drawImage(heavenHigher, 0, -(2*HEIGHT), WIDTH, HEIGHT, this);

        //Heaven
        g2.drawImage(heaven, 0, -HEIGHT, WIDTH, HEIGHT, this);

        //Ground
        scrollingGround.draw(g2, unitConversion(game.getBall().getVelocity().getX()));

        // Ball pt 1
        g2.setColor(game.getBall().getColor());
        g2.fillArc(unitConversion(game.getBall().getxPos()), yConversion(game.getBall().getyPos()),
                    unitConversion(game.getBall().getDiameter()), unitConversion(game.getBall().getDiameter()), (int)angularVelocity, 90);
        g2.fillArc(unitConversion(game.getBall().getxPos()), yConversion(game.getBall().getyPos()),
                unitConversion(game.getBall().getDiameter()), unitConversion(game.getBall().getDiameter()), (int)angularVelocity + 180, 90);

        //Ball pt 2
        g2.setColor(game.getBall().getColor2());
        g2.fillArc(unitConversion(game.getBall().getxPos()), yConversion(game.getBall().getyPos()),
                unitConversion(game.getBall().getDiameter()), unitConversion(game.getBall().getDiameter()), (int)angularVelocity + 90, 90);
        g2.fillArc(unitConversion(game.getBall().getxPos()), yConversion(game.getBall().getyPos()),
                unitConversion(game.getBall().getDiameter()), unitConversion(game.getBall().getDiameter()), (int)angularVelocity + 270, 90);

        angularVelocity -= 50*(game.getBall().getVelocity().getX());
        }


    /**
     * Converts millimeters into pixels
     * @param meters Measurements used in game calculations
     * @return An integer representing pixels
     */
    public int unitConversion(double meters) {
        return (int) (meters * 100);
    }

    /**
     * Converts y-coordinates from game into the graphical coordinate system
     * @param meters Measurements used in game calculations
     * @return An integer representing pixels
     */
    public int yConversion(double meters) {
        return HEIGHT - unitConversion(meters);
            }

    /**
     * Input method to perform action on KeyStroke
     */
    private void setInput() {
        getInputMap().put(KeyStroke.getKeyStroke("pressed SPACE"), "SPACEclicked");
        getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "SPACEreleased");
        getActionMap().put("SPACEclicked", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!SPACECLICK) {
                    keyPressed = System.currentTimeMillis();
                    SPACECLICK = true;
                }
            }
        });

        getActionMap().put("SPACEreleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keyPressedMillis = System.currentTimeMillis() - keyPressed;

                System.out.println(keyPressedMillis);

                SPACECLICK = false;
                keyPressed = 0;

                game.ballLaunch(keyPressedMillis/100);
            }
        });
    }
}
