package Graphics;

import MainGame.BonusBall;
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

    // Boolean to decide if the angle is to be determined.
    // Initially set to true because angle is determined first.
    private boolean decideAngle = true;

    // Boolean to decide if the force is to be determined.
    private boolean decideForce = false;

    // Boolean to not allow users to click after the ball has been launched.
    private boolean forceAndAngleDecided = true;

    // Saves the time at which the Space was pressed.
    private double keyPressed;

    // The time the Space was pressed. Computed by taking the current time minus keyPressed.
    private double keyPressedMillis;

    // This value is simply keyPressedMillis/1000, therefore the amount of seconds, not milliseconds,
    // Space has been pressed, to simplify equations for us.
    private double convertedValue;

    // Boolean to draw the initial "spring" or not.
    private boolean drawForceSpring = false;

    // This is the "angle" that is determined using Space.
    // 18 is the x-value and 0 is the y-value, basically giving it the length 18.
    // This length will be the speed (m/s) it will be launched with.
    private Physics.GameVector startAngle = new Physics.GameVector(30, 0, true);

    // Used during determination of angle to know if we're going to add to the angle or remove.
    private boolean goingUp = true;

    // Used to help draw the ball.
    private double angularVelocity;

    // Fonts
    private Font font;

    /**
     * Constructs a GameComponent with a game object and a few images
     * This class handles most of the graphics
     * @param game Contains all the information about the state of the game
     */
    public GameComponent(Game game) {
        this.game = game;
        setInput();

        try{
            background = ImageIO.read(getClass().getResource("/data/Background.png"));
        } catch(IOException e){
            System.out.println("Could not find Background.png");
        }
        try {
            ground = ImageIO.read(getClass().getResource("/data/Ground.png"));
        } catch (IOException e) {
            System.out.println("Could not find Ground.png");
        }

        scrollingBackground = new ScrollingBackground(background, game);
        scrollingGround = new ScrollingGround(ground, game);

        // Set the font of the Score string.
        font = new Font("Arial", Font.BOLD, 22);

    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(font);

        // Background
        scrollingBackground.draw(g2);

        //Ground
        scrollingGround.draw(g2);

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

        //BonusBalls
        for(BonusBall ball: game.getBonusBalls()) {
            g2.setColor(ball.getPrimaryColor());
            g2.fillOval(unitConversion(ball.getX()), yConversion(ball.getY()),
                    unitConversion(ball.getWidth()), unitConversion(ball.getWidth()));
            g2.setColor(Color.WHITE);
            g2.drawString(String.valueOf(Math.round(ball.getMass()) + " kg"),
                    unitConversion(ball.getCenter().getX()) - font.getSize(), yConversion(ball.getCenter().getY()) + font.getSize()/3);
        }
        if(game.isRestarted())
            resetGame();
            game.setRestarted(false);
        // Draws the "spring", if set to.
        if (drawForceSpring) {
            g2.setColor(Color.black);
            g2.drawLine(400, 500, 400+(int)(10*startAngle.getX()), 500+(int)(10*startAngle.getY()));
            g2.drawLine(401, 500, 401+(int)(10*startAngle.getX()), 500+(int)(10*startAngle.getY()));
            g2.setColor(Color.red);
            g2.drawLine(400, 500, 400+(int)(10*startAngle.getX()*convertedValue), 500+(int)(10*startAngle.getY()*convertedValue));
            g2.drawLine(401, 500, 401+(int)(10*startAngle.getX()*convertedValue), 500+(int)(10*startAngle.getY()*convertedValue ));
        }

        angularVelocity -= game.getPlayerBall().getVelocity().getX();

        // Score: Distance Bounced
        g2.setColor(new Color(255, 255, 255));
        g2.setFont(font);
        g2.drawString("Score: " + Math.round(game.getScore()), 20, 30);

        // Highscore
        g2.setColor(new Color(255, 255, 255));
        g2.drawString("Highscore: " + Math.round(game.getHighscore()), 20, 50);


        if(game.isGameOver()) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 100));
            g2.drawString("GAME OVER!", 320, 360);
            if (game.isRestarted()) {
                angularVelocity = 0;
            }
        }
    }

    /**
     * Converts millimeters into pixels
     * @param meters Measurements used in game calculations
     * @return An integer representing pixels
     */
    public static int unitConversion(double meters) {
        return (int) (meters * 100);
    }

    /**
     * Converts y-coordinates from game into the graphical coordinate system
     * @param meters Measurements used in game calculations
     * @return An integer representing pixels
     */
    public static int yConversion(double meters) {
        return HEIGHT - unitConversion(meters);
            }

    /**
     * Input method to perform actions.
     */
    private void setInput() {

        // Starts a listener for when Space is released and creates an event for it.
        getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "SPACEreleased");

        // The event created by the listener. The code in here is performed every time Space is released.
        getActionMap().put("SPACEreleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                // This part is basically set up in stages since multiple things are decided by releasing Spacebar.

                // Since decideAngle is set to true this is executed.
                if (decideAngle) {

                    // Sets to draw the "spring".
                    drawForceSpring = true;

                    // Starts the timer that decides the eventual final angle.
                    angleTimer.start();

                    // Changes it so that force is decided next time Space is released.
                    decideAngle = false;
                    decideForce = true;
                } else if (decideForce) {

                    // Stops the angle timer.
                    angleTimer.stop();

                    // Sets the time for when Space was released.
                    keyPressed = System.currentTimeMillis();

                    // Starts the timer that decides the eventual final force.
                    forceTimer.start();

                    // Sets it so you can't decide force anymore.
                    decideForce = false;
                } else if (forceAndAngleDecided) {

                    // Stops the force timer.
                    forceTimer.stop();

                    // Is used to make sure you can't click after both have been decided.
                    forceAndAngleDecided = false;

                    // Launches the ball with the decided angle and force.
                    // Angle is simply the angle the ball will be launched with.
                    // convertedValue is the multiplication of the length of the vector.
                    // It oscillates between 0 and 1, giving it the maximum speed of 16 m/s.
                    game.ballLaunch(GameVector.multiplyVector(convertedValue, startAngle));

                    // Stops drawing the "spring".
                    drawForceSpring = false;
                } else if (game.loadedBallExists()) {
                    game.getLoadedBonusBall().launch();
                }
            }
        });


        // Starts a listener when R is released and creates an event for it.
        getInputMap().put(KeyStroke.getKeyStroke("released R"), "R");

        // The event created by the listener. The code in here is performed when R is released.
        getActionMap().put("R", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                // Resets score, highscore, and all related values to their initial values.
                resetGame();
            }
        });
    }

    // Resets all related values to their initial values
    private void resetGame() {
        keyPressedMillis = 0;
        keyPressed = 0;
        convertedValue = 0;
        decideAngle = true;
        decideForce = false;
        forceAndAngleDecided = true;
        drawForceSpring = false;
        game.reset();
        angularVelocity = 0;
        angleTimer.stop();
        forceTimer.stop();
    }

    // The timer started the first time Space is released and is used to decide the angle.
    // Executes every 17ms.
    private Timer angleTimer = new Timer(17, new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            // Checks if the angle is supposed to turn up or down.
            if (goingUp) {

                // Is used when the angle is going up.
                startAngle.setAngle(startAngle.getAngle() - 0.05);

                // Checks if it is equal or below -pi/2 and if it is, makes it go down.
                // "-" and "<=" is because of the y-value being upside down, graphics-wise.
                if (startAngle.getAngle() <= - Math.PI/2 + 0.05) {
                    goingUp = false;
                }
            } else {

                // Is used when the angle is going down.
                startAngle.setAngle(startAngle.getAngle() + 0.05);

                // Checks if it is equal or above 0 and if it is, makes it go up again.
                if (startAngle.getAngle() >= - 0.05 )
                {
                    goingUp = true;
                }
            }
        }
    });

    // The timer started the second time Space is released and is used to decide the force.
    // Executes every 17ms.
    private Timer forceTimer = new Timer(17, new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            // Every time this part is executed the keyPressedMillis will be the amount of ms since space was released the second time.
            keyPressedMillis = System.currentTimeMillis() - keyPressed;

            // Converts it to seconds.
            convertedValue = keyPressedMillis / 1000;

            // Basically makes it oscillate between 0 and 1.
            // Contact Adrian Jakobsson at adrja681@student.liu.se for further questions.
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

    // Returns the WIDTH.
    static int getWIDTH() {
        return WIDTH;
    }

    // Returns the HEIGHT.
    static int getHEIGHT() {
        return HEIGHT;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
}
