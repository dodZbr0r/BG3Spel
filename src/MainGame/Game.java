package MainGame;

import Physics.Force;
import Physics.GameVector;


import java.awt.*;
import java.util.*;
import java.util.List;

import static Physics.Force.getAirResistance;


/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class Game {

    private PlayerBall playerBall;
    private BonusBall bonusBall;
    private double groundHeight;
    private GameVector gravity;
    private double gravitySize;
    private GameVector friction;
    private double score;
    private double highscore;
    private double timeStationary;
    private boolean gameOver;
    private List<PhysicsObject> objectsOnScreen;
    private List<Color> bonusBallColors;

    //Converts from milliseconds to Seconds
    private final double milliToSeconds = 0.001;

    /**
     * Constructs a Game with a ball object and ground measurements
     * creates a GameVector representing gravity
     */
    Game() {
        playerBall = new PlayerBall(2.0, 1.4, 3.0, 0.3, Color.RED, Color.ORANGE, new GameVector(0.0, 0.0));
        bonusBallColors = new ArrayList<Color>();
        bonusBallColors.addAll(Arrays.asList(Color.BLACK, Color.BLUE, Color.RED, Color.GREEN));
        bonusBall = generateBonusBall();
        objectsOnScreen = new ArrayList<PhysicsObject>();
        objectsOnScreen.add(playerBall);
        objectsOnScreen.add(bonusBall);
        timeStationary = 0;
        gameOver = false;
        groundHeight = 0.8;
        gravitySize = -9.8;
        gravity = new GameVector(0, gravitySize);
    }

    /**
     * Updates the state of the game
     * @param deltaTime The time it took to update in milliseconds
     */
    void update(double deltaTime) {

        //Applies gravity acceleration to all objects
        applyGravity(deltaTime);

        //Applies Air resistance to all objects
        applyAirResistance(deltaTime);

        //Sets new positions of objects
        setNewObjectPosition(deltaTime);

        //Checks collision with the ground
        checkGroundCollision(deltaTime);

        //Check collision
        /*if(playerBall.closeTo(bonusBall)){
            if(playerBall.hasCollision(bonusBall)){
                playerBall.setPos(playerBall.getPreX(), playerBall.getPreY());
                bonusBall.setPos(bonusBall.getPreX(), bonusBall.getPreY());
                Collision.setVelocityPostCollision(playerBall, bonusBall);
            }
        }*/

        //Stops the ball if the x-velocity of the ball is less than 0.01
        if(Math.abs(playerBall.getVelocity().getX()) < 0.01) {
            double velY = playerBall.getVelocity().getY();
            playerBall.setVelocity(new GameVector(0.0, velY));

        }

        //Updates the score and highscore
        setScore(score + playerBall.getVelocity().getX() * deltaTime * milliToSeconds);
        if(score > highscore) {
            setHighscore(score);
        }

        updateTimeStationary(deltaTime);

        //Printing some information about the ball
        /*System.out.printf("X-HASTIGHET:%10f", playerBall.getVelocity().getX());
        System.out.printf("   Y-HASTIGHET:%10f", playerBall.getVelocity().getY());
        System.out.printf("   XPOS:%10f", playerBall.getX());
        System.out.printf("   YPOS:%10f", playerBall.getY());
        System.out.println("   UPDATE TIME: " + updateTime);*/
    }

    /**
     * Applies gravity on every object on the screen as if
     * it had been affected by gravity over the time deltaTime.
     * @param deltaTime The time the object has been affected by gravity
     */
    private void applyGravity(double deltaTime) {
        for (PhysicsObject object: objectsOnScreen) {
            applyAcceleration(gravity, object, deltaTime);
        }
    }

    /**
     * Performs calculations for the ball to be affected by an acceleration vector
     * @param acceleration The acceleration vector that will affect the object
     */
    private void applyAcceleration(GameVector acceleration, PhysicsObject object, double deltaTime) {
        object.setVelocity(GameVector.addVectors(object.getVelocity(),
                GameVector.multiplyVector((deltaTime * milliToSeconds), acceleration)));
    }

    /**
     * Adds acceleration caused by air resistance to the total acceleration
     */
    private void applyAirResistance(double deltaTime){
        for (PhysicsObject object: objectsOnScreen) {
            GameVector airResistance = getAirResistance(object.getVelocity(), object.getArea());
            GameVector acceleration = Force.calculateAcceleration(object.getMass(), airResistance);
            applyAcceleration(acceleration, object, deltaTime);
        }
    }

    private void setNewObjectPosition(double deltaTime) {
        for (PhysicsObject object: objectsOnScreen) {
            object.setPos(object.getX() + (object.getVelocity().getX() * deltaTime * milliToSeconds),
                    object.getY() + (object.getVelocity().getY() * deltaTime * milliToSeconds));
        }
    }

    /**
     * Checks if any of the obejcts on the screen has collided with the ground
     * @param deltaTime The length of time the object has collided with the ground
     */
    private void checkGroundCollision(double deltaTime) {
        for (PhysicsObject object: objectsOnScreen) {
            if(object.getY() - object.getHeight() <= groundHeight && object.getVelocity().getY() < 0) {
                object.getVelocity().setPos(object.getVelocity().getX(), -(object.getVelocity().getY()));
                object.setPos(object.getX(), groundHeight + object.getHeight());
                friction = Force.getFriction(object.getMass(), gravitySize, object.getVelocity().getX());
                applyFriction(object, deltaTime);
            }
        }
    }

    /**
     * Adds acceleration caused vy friction to the total acceleration
     */
    private void applyFriction(PhysicsObject object, double deltaTime){
        GameVector acceleration = Force.calculateAcceleration(object.getMass(), friction);
        applyAcceleration(acceleration, object, deltaTime);
    }

    public PlayerBall getPlayerBall() {
        return playerBall;
    }

    public BonusBall getBonusBall() {
        return bonusBall;
    }

    public double getGroundHeight() {
        return groundHeight;
    }

    /**
     * Gives the ball speed when pressing Space
     */
    public void ballLaunch(GameVector initialVelocity) {
        playerBall.setVelocity(initialVelocity);

    }

    /**
     * Launches the bonus ball from the ground.
     */
    public void bonusBallLaunch(BonusBall ball){
        ball.setVelocity(ball.getLoadedForce());
    }

    /**
     * Generates a new BonusBall with random mass and color.
     */
    public BonusBall generateBonusBall(){
        Random random = new Random();
        double mass = Math.random() * 10;
        Color primaryColor = bonusBallColors.get(random.nextInt(bonusBallColors.size()));
        return new BonusBall(10, 0.5, mass, 0.5, primaryColor, new GameVector(0, 0));
    }

    /**
     * Resets the game
     */
    public void reset() {
        playerBall.setPos(2,0);
        playerBall.setVelocity(new GameVector(0, 0));
        setScore(0);
        setGameOver(false);
    }

    /**
     * Checks if the ball has been stationary for a certain amount of time.
     * Enables Game Over and resets the game after the ball has been stationary
     * for a specified amount of time
     * @param deltaTime The time since the last update
     */
    private void updateTimeStationary(double deltaTime) {
        //If the ball has a very small velocity in x- and y-direction
        if(Math.abs(playerBall.getVelocity().getX()) < 0.01 && Math.abs(playerBall.getVelocity().getY()) < 0.01
                && getScore() != 0) {
            //Add the time since the last update to the time the ball has been stationary
            timeStationary += deltaTime;
            //If it has been stationary for 1 second, the game is over
            if (timeStationary >= 1000) setGameOver(true);
            //If it has been stationary for 5 seconds, the game resets
            if (timeStationary >= 5000) reset();
        //If the ball reaquires significant speed somehow, it resets the timer.
        } else timeStationary = 0;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public double getScore() {
        return score;
    }

    private void setScore(double score) {
        this.score = score;
    }

    public double getHighscore() {
        return highscore;
    }

    private void setHighscore(double highscore) {
        this.highscore = highscore;
    }
}
