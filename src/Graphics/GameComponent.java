package Graphics;

import MainGame.Game;
import Physics.GameVector;

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
public class GameComponent extends JComponent{

    private Game game;
    private ScrollingBackground scrollingBackground;
    private ScrollingGround scrollingGround;
    // Window bounds
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;
    // Images
    private BufferedImage background, ground;
    private boolean decideAngle = true; //boolean so you can only give speed to the ball once
    private boolean decideForce = false;
    private boolean finalClick = true;
    private double keyPressedMillis;
    private double keyPressed;
    private double convertedValue;
    private boolean drawForceSpring = false;
    private Physics.GameVector startAngle = new Physics.GameVector(16, 0, true);

    boolean goingUp = true;

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
            background = ImageIO.read(new File("data/Background.png"));
        } catch(IOException e){
            System.out.println("Could not find Background.png");
        }
        try {
            ground = ImageIO.read(new File("data/Ground.png"));
        } catch (IOException e) {
            System.out.println("Could not find Ground.png");
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
        scrollingBackground.draw(g2, unitConversion(game.getPlayerBall().getVelocity().getX()/60));

        //Ground
        scrollingGround.draw(g2, unitConversion(game.getPlayerBall().getVelocity().getX()/60));

        // Ball pt 1
        g2.setColor(game.getPlayerBall().getPrimaryColor());
        g2.fillArc(unitConversion(game.getPlayerBall().getX()), yConversion(game.getPlayerBall().getY()),
                    unitConversion(game.getPlayerBall().getWidth()), unitConversion(game.getPlayerBall().getWidth()), (int)angularVelocity, 90);
        g2.fillArc(unitConversion(game.getPlayerBall().getX()), yConversion(game.getPlayerBall().getY()),
                unitConversion(game.getPlayerBall().getWidth()), unitConversion(game.getPlayerBall().getWidth()), (int)angularVelocity + 180, 90);

        //Ball pt 2
        g2.setColor(game.getPlayerBall().getAlternateColor());
        g2.fillArc(unitConversion(game.getPlayerBall().getX()), yConversion(game.getPlayerBall().getY()),
                unitConversion(game.getPlayerBall().getWidth()), unitConversion(game.getPlayerBall().getWidth()), (int)angularVelocity + 90, 90);
        g2.fillArc(unitConversion(game.getPlayerBall().getX()), yConversion(game.getPlayerBall().getY()),
                unitConversion(game.getPlayerBall().getWidth()), unitConversion(game.getPlayerBall().getWidth()), (int)angularVelocity + 270, 90);


        if (drawForceSpring) {
            g2.setColor(Color.black);
            g2.drawLine(400, 500, 400+(int)(20*startAngle.getX()), 500+(int)(20*startAngle.getY()));
            g2.drawLine(401, 500, 401+(int)(20*startAngle.getX()), 500+(int)(20*startAngle.getY()));
            g2.setColor(Color.red);
            g2.drawLine(400, 500, 400+(int)(20*startAngle.getX()*convertedValue), 500+(int)(20*startAngle.getY()*convertedValue));
            g2.drawLine(401, 500, 401+(int)(20*startAngle.getX()*convertedValue), 500+(int)(20*startAngle.getY()*convertedValue ));
        }

        angularVelocity -= game.getPlayerBall().getVelocity().getX();

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
                    drawForceSpring = true;

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

                    game.ballLaunch(GameVector.multiplyVector(convertedValue, startAngle));
                    drawForceSpring = false;
                }
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("released R"), "R");
        getActionMap().put("R", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.reset();
                resetGame();
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
            if (goingUp) {
                if (startAngle.getAngle() <= -  1.55) {
                    goingUp = false;
                }
                startAngle.setAngle(startAngle.getAngle() - 0.05);
            } else {
                if (startAngle.getAngle() >= 0)
                {
                    goingUp = true;
                }
                startAngle.setAngle(startAngle.getAngle() + 0.05);
            }
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

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
}
