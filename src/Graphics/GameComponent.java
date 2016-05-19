package Graphics;

import MainGame.Game;
import Physics.GameVector;
import javafx.application.Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private boolean decideAngle = true;
    private boolean decideForce = false;
    private boolean finalClick = true;
    private double keyPressedMillis;
    private double keyPressed;
    private double convertedValue;
    private boolean drawForceSpring = true;
    private Physics.GameVector startAngle = new Physics.GameVector(1, 0);

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

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

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
        g2.setColor(game.getBall().getPrimaryColor());
        g2.fillArc(unitConversion(game.getBall().getX()), yConversion(game.getBall().getY()),
                    unitConversion(game.getBall().getWidth()), unitConversion(game.getBall().getWidth()), (int)angularVelocity, 90);
        g2.fillArc(unitConversion(game.getBall().getX()), yConversion(game.getBall().getY()),
                unitConversion(game.getBall().getWidth()), unitConversion(game.getBall().getWidth()), (int)angularVelocity + 180, 90);

        //Ball pt 2
        g2.setColor(game.getBall().getAlternateColor());
        g2.fillArc(unitConversion(game.getBall().getX()), yConversion(game.getBall().getY()),
                unitConversion(game.getBall().getWidth()), unitConversion(game.getBall().getWidth()), (int)angularVelocity + 90, 90);
        g2.fillArc(unitConversion(game.getBall().getX()), yConversion(game.getBall().getY()),
                unitConversion(game.getBall().getWidth()), unitConversion(game.getBall().getWidth()), (int)angularVelocity + 270, 90);

        if (drawForceSpring) {
            g2.setColor(Color.yellow);
            g2.drawLine(400, 500, 400+(int)(250*startAngle.getX()), 500+(int)(250*startAngle.getY()));
            g2.drawLine(401, 500, 401+(int)(250*startAngle.getX()), 500+(int)(250*startAngle.getY()));
            g2.setColor(Color.black);
            g2.drawLine(400, 500, 400+(int)(convertedValue*250), 500-(int)(convertedValue*250));
            g2.drawLine(401, 500, 401+(int)(convertedValue*250), 500-(int)(convertedValue*250));
        }
        //Crude temporary spring

        angularVelocity -= 50*(game.getBall().getVelocity().getX());

        // Score: Distance Bounced
        g2.setColor(new Color(255, 255, 255));
        g2.setFont(font);
        g2.drawString("Score: " + Math.round(game.getScore()), 20, 30);

        // Highscore
        g2.setColor(new Color(255, 255, 255));
        g2.setFont(font);
        g2.drawString("Highscore: " + Math.round(game.getHighscore()), 20, 50);

        if(game.isGameOver()) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 100));
            g2.drawString("GAME OVER!", 320, 360);
        }
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
        getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "SPACEreleased");
        getActionMap().put("SPACEreleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (decideAngle) {
                    keyPressed = System.currentTimeMillis();

                    angleTimer.start();

                    decideAngle = false;
                    decideForce = true;
                } else if (decideForce) {
                    angleTimer.stop();

                    keyPressed = System.currentTimeMillis();

                    forceTimer.start();

                    decideForce = false;
                } else if (!decideAngle & !decideForce & finalClick) {
                    forceTimer.stop();
                    finalClick = false;

                    game.ballLaunch(convertedValue);
                    drawForceSpring = false;
                }
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("released R"), "R");
        getActionMap().put("R", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.reset();
            }
        });
    }

    public void resetGame() {
        keyPressedMillis = 0;
        keyPressed = 0;
        convertedValue = 0;
        decideAngle = true;
        decideForce = false;
        finalClick = true;
        drawForceSpring = true;
        game.reset();
    }

    Timer angleTimer = new Timer(17, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (startAngle.getX() >= 0 && startAngle.getY() >= -1) {
                startAngle.setPos(startAngle.getX() - 0.01, startAngle.getY() - 0.01);
            }
            System.out.println(startAngle.getX() + " " + startAngle.getY());
        }
    });

    Timer forceTimer = new Timer(17, new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            keyPressedMillis = System.currentTimeMillis() - keyPressed;
            convertedValue = keyPressedMillis / 1000;

            if (convertedValue >= 1) {
                if (convertedValue >= 2) {
                    convertedValue = convertedValue % 2;
                    if (convertedValue >= 1) {
                        convertedValue = 1 - (convertedValue % 1);
                    }
                } else {
                    convertedValue = 1 - (convertedValue % 1);
                }
            }
        }
    });

    static int getWIDTH() {
        return WIDTH;
    }

    static int getHEIGHT() {
        return HEIGHT;
    }
}
