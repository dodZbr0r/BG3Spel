package MainGame;

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
    private Ball ball;
    private double groundHeight;
    private GameVector gravity;
    private GameVector airResistance;
    private double gravitySize;
    private GameVector friction;
    private double score;


    /**
     * Constructs a Game with a ball object and ground measurements
     * creates a GameVector representing gravity
     */
    Game() {
        ball = new Ball(2.0, 5.0, 2.0, 0.5, Color.RED, Color.ORANGE, new GameVector(0.5/fps, -3.0/fps));
        groundHeight = 0.8;
        gravitySize = -9.8;
        gravity = new GameVector(0, (gravitySize/fps)/fps);


    }

    /**
     * Updates the state of the game
     * @param updateTime The time it took to update in milliseconds
     */
    void update(double updateTime) {
        applyAcceleration(gravity);
        airResistance = getAirResistance(ball.getVelocity(), ball.getRadius());
        applyAirResistance();
        ball.setPos(ball.getX(), ball.getY() + ball.getVelocity().getY());

        if(ball.getY() - ball.getWidth() <= groundHeight && ball.getVelocity().getY() < 0) {
            ball.getVelocity().setPos(ball.getVelocity().getX(), -(ball.getVelocity().getY()));
            ball.setPos(ball.getX(), groundHeight + ball.getWidth());
            friction=Force.getFriction(ball.getMass(), gravitySize, ball.getVelocity().getX());
            applyFriction();
        }

        if(Math.abs(ball.getVelocity().getX()) < 0.01) {
            double velY = ball.getVelocity().getY();
            ball.setVelocity(new GameVector(0.0, velY));
        }

        setScore(score + ball.getVelocity().getX());

        //Printing some information about the ball
        System.out.printf("X-HASTIGHET:%10f", ball.getVelocity().getX());
        System.out.printf("   Y-HASTIGHET:%10f", ball.getVelocity().getY());
        System.out.printf("   XPOS:%10f", ball.getX());
        System.out.printf("   YPOS:%10f", ball.getY());
        System.out.println("   UPDATE TIME: " + updateTime);


    }

    /**
     * Performs calculations for the ball to be affected by an acceleration vector
     * @param acceleration The acceleration vector that will affect the object
     */
    private void applyAcceleration(GameVector acceleration) {ball.setVelocity(GameVector.addVectors(ball.getVelocity(), acceleration));}

    /**
     * Adds acceleration caused by air resistance to the total acceleration
     */
    private void applyAirResistance(){
        double mass = ball.getMass();
        GameVector acceleration = Force.calculateAcceleration(mass, airResistance);
        applyAcceleration(acceleration);
    }

    /**
     * Adds acceleration caused vy friction to the total acceleration
     */
    private void applyFriction(){
        GameVector acceleration = Force.calculateAcceleration(ball.getMass(), friction);
        applyAcceleration(acceleration);
    }

    public Ball getBall() {
        return ball;
    }

    public double getGroundHeight() {
        return groundHeight;
    }

    /**
     * Gives the ball speed when pressing space
     */
    public void ballLaunch(double launchForce) {


        ball.setVelocity( new GameVector(1 * launchForce, 0.1 * launchForce));

    }

    public double getScore() {
        return score;
    }

    private void setScore(double score) {
        this.score = score;
    }
}
