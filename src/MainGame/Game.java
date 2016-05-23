package MainGame;

import Physics.Collision;
import Physics.Force;
import Physics.GameVector;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static Physics.Force.getAirResistance;


/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class Game {

    private Ball ball1, ball2;
    private double groundHeight;
    private GameVector gravity;
    private double gravitySize;
    private GameVector friction;
    private double score;
    private double highscore;
    private List<PhysicsObject> objectsOnScreen;

    //Converts from milliseconds to Seconds
    private final double milliToSeconds = 0.001;

    /**
     * Constructs a Game with a ball object and ground measurements
     * creates a GameVector representing gravity
     */
    Game() {
        ball1 = new Ball(0.0, 5.0, 3.0, 0.5, Color.RED, Color.ORANGE, new GameVector(5.0, 3.0));
        ball2 = new Ball(10, 5.0, 3.0, 0.5, Color.BLUE, Color.GREEN, new GameVector(-5.0, 4.0));
        objectsOnScreen = new ArrayList<PhysicsObject>();
        objectsOnScreen.add(ball1);
        objectsOnScreen.add(ball2);
        groundHeight = 0.5;
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
        if(ball1.closeTo(ball2)){
            if(ball1.hasCollision(ball2)){
                ball1.setPos(ball1.getPreX(), ball1.getPreY());
                ball2.setPos(ball2.getPreX(), ball2.getPreY());
                Collision.setVelocityPostCollision(ball1, ball2);
            }
        }

        //Stops the ball if the x-velocity of the ball is less than 0.01
        /*if(Math.abs(ball1.getVelocity().getX()) < 0.01) {
            double velY = ball1.getVelocity().getY();
            ball1.setVelocity(new GameVector(0.0, velY));

        }

        if(Math.abs(ball2.getVelocity().getX()) < 0.01) {
            double velY = ball2.getVelocity().getY();
            ball2.setVelocity(new GameVector(0.0, velY));

        }*/

        //Updates the score and highscore
        setScore(score + ball1.getVelocity().getX());
        if(score > highscore) {
            setHighscore(score);
        }

        //Printing some information about the ball
        /*System.out.printf("X-HASTIGHET:%10f", ball1.getVelocity().getX());
        System.out.printf("   Y-HASTIGHET:%10f", ball1.getVelocity().getY());
        System.out.printf("   XPOS:%10f", ball1.getX());
        System.out.printf("   YPOS:%10f", ball1.getY());
        System.out.println("   UPDATE TIME: " + updateTime);*/


    }


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

    public Ball getBall() {
        return ball1;
    }

    public Ball getBall2() {
        return ball2;
    }

    public double getGroundHeight() {
        return groundHeight;
    }

    /**
     * Gives the ball speed when pressing space
     */
    public void ballLaunch(double launchForce) {


        ball1.setVelocity( new GameVector(1 * launchForce, 0.1 * launchForce));

    }

    /**
     * Resets the game
     */
    public void reset() {
        ball1.setPos(2,0);
        ball1.setVelocity(new GameVector(0, 0));
        setScore(0);
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getHighscore() {
        return highscore;
    }

    public void setHighscore(double highscore) {
        this.highscore = highscore;
    }
}
