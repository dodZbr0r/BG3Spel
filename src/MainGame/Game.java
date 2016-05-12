package MainGame;

import Physics.GameVector;


import java.awt.*;
import java.awt.event.*;

import static Physics.Force.getAirResistance;


/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class Game {

    public static final int fps = 60;
    private Ball ball;
    private int groundHeight;
    private GameVector gravity;
    private GameVector airResistance;



    /**
     * Constructs a Game with a ball object and ground measurements
     * creates a GameVector representing gravity
     */
    public Game() {


        ball = new Ball(100, 1000, 2, 500, Color.RED, new GameVector(0/fps, 0/fps));
        groundHeight = 500;
        gravity = new GameVector(0, -4*(9800/fps)/fps);


    }

    /**
     * Updates the state of the game
     * @param updateTime The time it took to update in milliseconds
     */
    public void update(double updateTime) {
        applyAcceleration(gravity);
        airResistance = getAirResistance(ball.getVelocity());
        applyAirResistance();
        ball.setPos(ball.getxPos() + ball.getVelocity().getX(), ball.getyPos() + ball.getVelocity().getY());
        if(ball.getyPos() - ball.getDiameter() <= groundHeight && ball.getVelocity().getY() < 0) {
            ball.getVelocity().setPos(ball.getVelocity().getX(), -(ball.getVelocity().getY()));
            ball.setPos(ball.getxPos(), groundHeight + ball.getDiameter());
        }

        //Printing some information about the ball
        System.out.printf("HASTIGHET:%5d", ball.getVelocity().getY());
        System.out.printf("   YPOS:%6d", ball.getyPos());

    }

    /**
     * Performs calculations for the ball to be affected by an acceleration vector
     * @param acceleration The acceleration vector that will affect the object
     */
    public void applyAcceleration(GameVector acceleration) {ball.setVelocity(GameVector.addVectors(ball.getVelocity(), acceleration));}
    public void applyAirResistance(){
        ball.setVelocity(GameVector.addVectors(ball.getVelocity(), airResistance));
    }

    public Ball getBall() {
        return ball;
    }

    public int getGroundHeight() {
        return groundHeight;
    }


    /**
     * Gives the ball speed when pressing space
     */
    public void ballLaunch(){

        ball.setVelocity( new GameVector(2000/fps, 10000/fps));

    }
}
