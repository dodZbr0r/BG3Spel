package MainGame;

import Physics.Collision;
import Physics.Force;
import Physics.GameVector;


import java.awt.*;
import java.awt.event.*;

import static Physics.Force.getAirResistance;


/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class Game {

    private static final double fps = 60;
    private Ball ball1, ball2;
    private double groundHeight;
    private GameVector gravity;
    private double gravitySize;
    private GameVector friction;
    private double score;
    private double highscore;


    /**
     * Constructs a Game with a ball object and ground measurements
     * creates a GameVector representing gravity
     */
    Game() {
        ball1 = new Ball(2.0, 1.5, 1.0, 0.5, Color.RED, Color.ORANGE, new GameVector(2.0/fps, 6.0/fps));
        ball2 = new Ball(6.0, 1.5, 3.0, 0.5, Color.BLUE, Color.GREEN, new GameVector(-1.0/fps, 8.0/fps));
        groundHeight = 0.5;
        gravitySize = -9.8;
        gravity = new GameVector(0, (gravitySize/fps)/fps);
    }

    /**
     * Updates the state of the game
     * @param updateTime The time it took to update in milliseconds
     */
    void update(double updateTime) {
        applyAcceleration(ball1, gravity);
        applyAcceleration(ball2, gravity);

        applyAirResistance(ball1);
        applyAirResistance(ball2);
        ball1.setPos(ball1.getX() + ball1.getVelocity().getX(), ball1.getY() + ball1.getVelocity().getY());
        ball2.setPos(ball2.getX() + ball2.getVelocity().getX(), ball2.getY() + ball2.getVelocity().getY());

        if(ball1.getY() - ball1.getWidth() <= groundHeight && ball1.getVelocity().getY() < 0) {
            ball1.getVelocity().setPos(ball1.getVelocity().getX(), -(ball1.getVelocity().getY()));
            ball1.setPos(ball1.getX(), groundHeight + ball1.getWidth());
            friction=Force.getFriction(ball1.getMass(), gravitySize, ball1.getVelocity().getX());
            applyFriction(ball1);
        }

        if(ball2.getY() - ball2.getWidth() <= groundHeight && ball2.getVelocity().getY() < 0) {
            ball2.getVelocity().setPos(ball2.getVelocity().getX(), -(ball2.getVelocity().getY()));
            ball2.setPos(ball2.getX(), groundHeight + ball2.getWidth());
            friction=Force.getFriction(ball2.getMass(), gravitySize, ball2.getVelocity().getX());
            applyFriction(ball2);
        }

        //Check collision
        if(ball1.closeTo(ball2)){
            if(ball1.hasCollision(ball2)){
                ball1.setPos(ball1.getPreX(), ball1.getPreY());
                ball2.setPos(ball2.getPreX(), ball2.getPreY());
                Collision.setVelocityPostCollision(ball1, ball2);
            }
        }

        //Stops the ball if the x-velocity of the ball is less than 0.01
        if(Math.abs(ball1.getVelocity().getX()) < 0.01) {
            double velY = ball1.getVelocity().getY();
            ball1.setVelocity(new GameVector(0.0, velY));

        }

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

    /**
     * Performs calculations for the ball to be affected by an acceleration vector
     * @param acceleration The acceleration vector that will affect the object
     */
    private void applyAcceleration(PhysicsObject object, GameVector acceleration) {
        object.setVelocity(GameVector.addVectors(object.getVelocity(), acceleration));}

    /**
     * Adds acceleration caused by air resistance to the total acceleration
     */
    private void applyAirResistance(PhysicsObject object){
        GameVector airResistance = getAirResistance(object.getVelocity(), object.getArea());
        double mass = object.getMass();
        GameVector acceleration = Force.calculateAcceleration(mass, airResistance);
        applyAcceleration(object, acceleration);
    }

    /**
     * Adds acceleration caused vy friction to the total acceleration
     */
    private void applyFriction(PhysicsObject object){
        GameVector acceleration = Force.calculateAcceleration(object.getMass(), friction);
        applyAcceleration(object, acceleration);
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
