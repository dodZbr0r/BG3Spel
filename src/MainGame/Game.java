package MainGame;

import Physics.Collision;
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

    private final PlayerBall playerBall;
    private GameVector gravity;
    private GameVector friction;
    private double groundHeight, backgroundPos, groundPos, score, highscore, timeStationary;
    private boolean gameOver;
    private boolean isRestarted=false;
    private List<PhysicsObject> objectsOnScreen;
    private List<BonusBall> bonusBalls;
    private final static Random random = new Random();
    private static final int MINMASS = 4;
    private static final int MAXMASS = 8;
    private double travelledSince;
    private final int MINLENGTH=18;
    private final int MAXLENGTH=30;


    //Converts from milliseconds to Seconds
    private final double milliToSeconds = 0.001;

    /**
     * Constructs a Game with a ball object and ground measurements
     * creates a GameVector representing gravity
     */
    Game() {
        playerBall = new PlayerBall(2.0, 1.4, 7.0, 0.3, Color.RED, Color.ORANGE, new GameVector(0.0, 0.0));

        //creates a list with objects on screen
        objectsOnScreen = new ArrayList<PhysicsObject>();
        objectsOnScreen.add(playerBall);


        //List with bonusballs

        bonusBalls = new ArrayList<BonusBall>();

        timeStationary = 0;
        gameOver = false;

        groundHeight = 0.8;
        groundPos = 0.0;
        backgroundPos = 0.0;

        gravity = new GameVector(0, -9.8);

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

        //Sets new position of playerBall
        playerBall.setPos(playerBall.getX(), playerBall.getY() +
                (playerBall.getVelocity().getY() * deltaTime * milliToSeconds));

        //Sets new positions of bonusBalls
        setNewBonusPosition(deltaTime);

        //Sets new ground and background position
        updateGroundAndBackgroundPos(deltaTime);

        //Checks collision with the ground
        checkGroundCollision(deltaTime);

        //Check collision
        checkCollision();

        //Stops the ball if the x-velocity of the ball is less than 0.01
        if(Math.abs(playerBall.getVelocity().getX()) < 0.5 &&
                Math.abs(playerBall.getVelocity().getY()) < 0.5) {
            double velY = playerBall.getVelocity().getY();
            playerBall.setVelocity(new GameVector(0.0, velY));

        }

        //Updates the score and highscore
        setScore(score + playerBall.getVelocity().getX() * deltaTime * milliToSeconds);
        if(score > highscore) {
            setHighscore(score);
        }
        setTravelledSince(travelledSince + playerBall.getVelocity().getX() * deltaTime * milliToSeconds);
        checkBallsOnScreen();
        randomlyAddBonusBalls();

        updateTimeStationary(deltaTime);


        //Printing some information about the ball
        //System.out.printf("X-HASTIGHET:%10f", playerBall.getVelocity().getX());

        //System.out.printf("   Y-HASTIGHET:%10f", playerBall.getVelocity().getY());
        //System.out.printf("   XPOS:%10f", playerBall.getX());
        //System.out.printf("   YPOS:%10f", playerBall.getY());
        //System.out.println("   UPDATE TIME: " + updateTime);
        //System.out.println();
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
     * @param object The ball to be affected
     * @param deltaTime The time between each update
     */
    private void applyAcceleration(GameVector acceleration, PhysicsObject object, double deltaTime) {
        object.setVelocity(GameVector.addVectors(object.getVelocity(),
                GameVector.multiplyVector((deltaTime * milliToSeconds), acceleration)));
    }

    /**
     * Adds acceleration caused by air resistance to the total acceleration
     * @param deltaTime The time between each update
     */
    private void applyAirResistance(double deltaTime){
        for (PhysicsObject object: objectsOnScreen) {
            GameVector airResistance = getAirResistance(object.getVelocity(), object.getArea());
            GameVector acceleration = Force.calculateAcceleration(object.getMass(), airResistance);
            applyAcceleration(acceleration, object, deltaTime);
        }
    }

    /**
     * Adjusts the position of a bonus ball as the player ball moves
     * @param deltaTime The time between each update
     */
    private void setNewBonusPosition(double deltaTime) {
        for (PhysicsObject object: bonusBalls) {
            object.setPos(object.getX() + (object.getVelocity().getX() * deltaTime * milliToSeconds)
                    - playerBall.getVelocity().getX() * deltaTime * milliToSeconds,
                    object.getY() + (object.getVelocity().getY() * deltaTime * milliToSeconds));
        }
    }
    /**
     * Makes the ground and background move as the ball moves
     * @param deltaTime The time between each update
     */
    private void updateGroundAndBackgroundPos(double deltaTime) {
        if(playerBall.getVelocity().getX() >= 0) {
            if (groundPos <= -12.8) {
                groundPos = 0;
            } else {
                groundPos -= playerBall.getVelocity().getX() * deltaTime * milliToSeconds;
            }
            if (backgroundPos <= -12.8) {
                backgroundPos = 0;
            } else {
                backgroundPos -= ((playerBall.getVelocity().getX()) / 3) * deltaTime * milliToSeconds;
            }
        } else {
            if(groundPos >= 0){
                groundPos = -12.8;
            } else {
                groundPos -= playerBall.getVelocity().getX() * deltaTime * milliToSeconds;
            }
            if(backgroundPos >= 0){
                backgroundPos = -12.8;
            } else {
                backgroundPos -= ((playerBall.getVelocity().getX())/3) * deltaTime * milliToSeconds;
            }
        }

    }

    /**
     * Checks if any of the objects on the screen has collided with the ground
     * @param deltaTime The length of time the object has collided with the ground
     */
    private void checkGroundCollision(double deltaTime) {
        for (PhysicsObject object: objectsOnScreen) {
            if(object.getY() - object.getHeight() <= groundHeight && object.getVelocity().getY() < 0) {
                object.getVelocity().setPos(object.getVelocity().getX(), -(object.getVelocity().getY()*0.9));
                object.setPos(object.getX(), groundHeight + object.getHeight());
                friction = Force.getFriction(object.getMass(), gravity.getY(), object.getVelocity().getX());
                applyFriction(object, deltaTime);
            }
        }
    }

    /**
     * Checks if any of the objects on screen have collided with each other
     */
    private void checkCollision() {
        for(int i = 0; i < objectsOnScreen.size() - 1; i++) {
            PhysicsObject ball1 = objectsOnScreen.get(i);
            for(int j = i + 1; j < objectsOnScreen.size(); j++) {
                PhysicsObject ball2 = objectsOnScreen.get(j);
                if (ball1.closeTo(ball2)) {
                    if (ball1.hasCollision(ball2)) {
                        ball1.setPos(ball1.getPreX(), ball1.getPreY());
                        ball2.setPos(ball2.getPreX(), ball2.getPreY());
                        Collision.setVelocityPostCollision(ball1, ball2);
                    }
                }
            }
        }
    }

    /**
     * Adds acceleration caused by friction to the total acceleration
     * @param deltaTime the time between each update
     */
    private void applyFriction(PhysicsObject object, double deltaTime){
        GameVector acceleration = Force.calculateAcceleration(object.getMass(), friction);
        applyAcceleration(acceleration, object, deltaTime);
    }

    public PlayerBall getPlayerBall() {
        return playerBall;
    }

    public List<BonusBall> getBonusBalls() {
        return bonusBalls;
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
     * Checks all bonus balls in the game to see if they're on the visible screen,
     * otherwise unloads them or removes them
     */
    private void checkBallsOnScreen(){
        List<BonusBall> ballsToRemove = new ArrayList<BonusBall>();
        for (BonusBall ball: bonusBalls){
            if(ball.getX()<=-ball.getWidth() || ball.getX()>=14){
                ball.unload();
                if(ball.getX()<=-26) {
                    ballsToRemove.add(ball);
                }
            }
        }
        objectsOnScreen.removeAll(ballsToRemove);
        bonusBalls.removeAll(ballsToRemove);
    }

    /**
     * Randomly adds bonus balls if there are no loaded balls and if there
     * hasn't been any in a certain distance
     */
    private void randomlyAddBonusBalls(){
        int minTravelledLength = random.nextInt(MAXLENGTH-MINLENGTH) + MINLENGTH;
        if(travelledSince >= minTravelledLength && !loadedBallExists()){
            if(random.nextInt(5) != 5){
                generateBonusBall();
            }
            travelledSince = 0;
        }

    }

    /**
     * Generates a new BonusBall with random mass and color.
     */
    private void generateBonusBall(){
        BonusBallType type = BonusBallType.getRandomBallType();
        int mass = random.nextInt(MAXMASS - MINMASS) + MINMASS;
        BonusBall ball = new BonusBall(12.8, 1.6, mass, 0.4, type.getColor(), new GameVector(0, 0),
                new GameVector(0, type.getLoadedVelocity()));
        objectsOnScreen.add(ball);
        bonusBalls.add(ball);
    }

    /**
     * Checks to see if there are loaded balls
     * @return true if there are, otherwise false
     */
    public boolean loadedBallExists(){
        for(BonusBall ball: bonusBalls) {
            if(ball.isLoaded()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a loaded bonus ball if there is one
     * @return a bonus ball
     */
    public BonusBall getLoadedBonusBall(){
        for(BonusBall ball: bonusBalls) {
            if(ball.isLoaded()) {
                return ball;
            }
        }
        return null;
    }

    /**
     * Resets the game
     */
    public void reset() {
        playerBall.setPos(2,0);
        playerBall.setVelocity(new GameVector(0, 0));
        setScore(0);
        setGameOver(false);
        objectsOnScreen.removeAll(bonusBalls);
        bonusBalls.clear();
        setGroundPos(0);
        setBackgroundPos(0);
        travelledSince = 0;
    }

    /**
     * Checks if the ball has been stationary for a certain amount of time.
     * Enables Game Over and resets the game after the ball has been stationary
     * for a specified amount of time
     * @param deltaTime The time since the last update
     */
    private void updateTimeStationary(double deltaTime) {

        //If the ball has a very small velocity in x- and y-direction
        if(Math.abs(playerBall.getVelocity().getX()) < 0.5 && Math.abs(playerBall.getVelocity().getY()) < 0.5
                && getScore() != 0) {

            //Add the time since the last update to the time the ball has been stationary
            timeStationary += deltaTime;

            //If it has been stationary for 1 second, the game is over
            if (timeStationary >= 1000) setGameOver(true);

            //If it has been stationary for 5 seconds, the game resets
            if (timeStationary >= 5000)  isRestarted=true;

        //If the ball reacquires significant speed somehow, it resets the timer.
        } else timeStationary = 0;
    }

    public void setBackgroundPos(double backgroundPos) {
        this.backgroundPos = backgroundPos;
    }

    public void setGroundPos(double groundPos) {
        this.groundPos = groundPos;
    }

    public double getBackgroundPos() {
        return backgroundPos;
    }

    public double getGroundPos() {
        return groundPos;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isRestarted(){return isRestarted; }

    public void setRestarted(boolean isRestarted){this.isRestarted=isRestarted; }

    public double getScore() {
        return score;
    }

    private void setScore(double score) {
        this.score = score;
    }

    public double getHighscore() {
        return highscore;
    }

    public void setHighscore(double highscore) {
        this.highscore = highscore;
    }

    private void setTravelledSince (double travelledSince){
        this.travelledSince=travelledSince;
    }
}
