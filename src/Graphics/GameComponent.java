package Graphics;

import MainGame.Game;
import Physics.GameVector;
import javafx.application.Application;

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

    private Game game;
    private ScrollingBackground scrollingBackground;
    private ScrollingGround scrollingGround;
    // Window bounds
    private static final int HEIGHT = 720;
    private static final int WIDTH = 1280;
    // Images
    private BufferedImage background, ground, heaven, heavenHigher, bed;
    //boolean so you can only give speed to the ball once
    private boolean SPACECLICK = false;
    private double keyPressedMillis;
    public double tempKeyPressed;
    private double keyPressed;
    private double convertedValue;
    private double angularVelocity;
    // Fonts
    private Font font;

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
        } catch(IOException e){
            System.out.println("Could not find layer-1-double.png");
        }
        try {
            ground = ImageIO.read(new File("data/layer-2-double.png"));
        } catch (IOException e) {
            System.out.println("Could not find layer-2-double.png");
        }
        try {
            heaven = ImageIO.read(new File("data/Heaven.png"));
        } catch (IOException e) {
            System.out.println("Could not find Heaven.png");
        }
        try {
            heavenHigher = ImageIO.read(new File("data/HeavenHigher.png"));
        } catch (IOException e) {
            System.out.println("Could not find HeavenHigher.png");
        }

        scrollingBackground = new ScrollingBackground(background);
        scrollingGround = new ScrollingGround(ground);

        // Set the font of the Score string.
        font = new Font("Arial", Font.BOLD, 22);

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

        // Spring
        g2.drawImage(bed, 400, HEIGHT-180, 100, 100, null);

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

        //Crude temporary spring
        g2.setColor(Color.black);
        g2.drawLine(400, 500, 400+(int)(convertedValue*50), 500-(int)(convertedValue*50));
        g2.drawLine(401, 500, 401+(int)(convertedValue*50), 500-(int)(convertedValue*50));

        angularVelocity -= 50*(game.getBall().getVelocity().getX());

        // Score: Distance Bounced
        g2.setColor(new Color(255, 255, 255));
        g2.setFont(font);
        g2.drawString("Score: " + Math.round(game.getScore()), 20, 40);
    }


    /**
     * Converts millimeters into pixels
     * @param meters Measurements used in game calculations
     * @return An integer representing pixels
     */
    private int unitConversion(double meters) {
        return (int) (meters * 100);
    }

    /**
     * Converts y-coordinates from game into the graphical coordinate system
     * @param meters Measurements used in game calculations
     * @return An integer representing pixels
     */
    private int yConversion(double meters) {
        return HEIGHT - unitConversion(meters);
            }

    /**
     * Input method to perform action on KeyStroke
     */
    private void setInput() {
        getInputMap().put(KeyStroke.getKeyStroke("pressed SPACE"), "SPACEclicked");
        getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "SPACEreleased");
        getActionMap().put("SPACEclicked", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!SPACECLICK) {
                    keyPressed = System.currentTimeMillis();
                    SPACECLICK = true;
                }

                keyPressedMillis = System.currentTimeMillis() - keyPressed;
                convertedValue = keyPressedMillis/1000;

                if (convertedValue >= 5) {
                    if (convertedValue >= 10) {
                        convertedValue = convertedValue%10;
                        if (convertedValue >= 5) {
                            convertedValue = 5 - (convertedValue % 5);
                        }
                    } else {
                        convertedValue = 5 - (convertedValue % 5);
                    }
                }
            }
        });

        getActionMap().put("SPACEreleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keyPressedMillis = System.currentTimeMillis() - keyPressed;

                System.out.println(keyPressedMillis);
                convertedValue = keyPressedMillis/1000;

                SPACECLICK = false;
                keyPressed = 0;

                if (convertedValue >= 5) {
                    if (convertedValue >= 10) {
                        convertedValue = convertedValue%10;
                        if (convertedValue >= 5) {
                            convertedValue = 5 - (convertedValue % 5);
                        }
                    } else {
                        convertedValue = 5 - (convertedValue % 5);
                    }
                }

                game.ballLaunch(convertedValue);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("released R"), "R");
        getActionMap().put("R", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.getBall().setxPos(2);
                game.getBall().setyPos(0);
                game.getBall().setVelocity(new GameVector(0, 0));
            }
        });
    }

    static int getWIDTH() {
        return WIDTH;
    }

    static int getHEIGHT() {
        return HEIGHT;
    }
}
