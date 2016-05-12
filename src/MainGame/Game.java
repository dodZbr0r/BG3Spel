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

    public static final double fps = 60;
    private Ball ball;
    private double groundHeight;
    private GameVector gravity;
    private GameVector airResistance;



    /**
     * Constructs a Game with a ball object and ground measurements
     * creates a GameVector representing gravity
     */
    public Game() {
        ball = new Ball(2.0, 5.0, 2.0, 0.5, Color.RED, new GameVector(0/fps, -3.0/fps));
        groundHeight = 0.5;
        gravity = new GameVector(0, -4*(9.8/fps)/fps);


    }

    /**
     * Updates the state of the game
     * @param updateTime The time it took to update in milliseconds
     */
    public void update(double updateTime) {
        airResistance = getAirResistance(ball.getVelocity());

        System.out.println("NEW UPDATE");
        System.out.println("--------------------------------------------------");
        System.out.printf("HASTIGHET:%10f", ball.getVelocity().getY());
        System.out.printf("   YPOS:%10f", ball.getyPos());
        System.out.println("   UPDATETIME: " + updateTime);
        System.out.println("Air Resistance Vector: ");
        GameVector.printVector(airResistance);
        System.out.println("Ball Velocity Vector: ");
        GameVector.printVector(ball.getVelocity());

        applyAirResistance();
        applyAcceleration(gravity);
        ball.setPos(ball.getxPos() + ball.getVelocity().getX(), ball.getyPos() + ball.getVelocity().getY());

        if(ball.getyPos() - ball.getDiameter() <= groundHeight && ball.getVelocity().getY() < 0) {
            ball.getVelocity().setPos(ball.getVelocity().getX(), -(ball.getVelocity().getY()));
            ball.setPos(ball.getxPos(), groundHeight + ball.getDiameter());
        }

        //Printing some information about the ball

    }

    /**
     * Performs calculations for the ball to be affected by an acceleration vector
     * @param acceleration The acceleration vector that will affect the object
     */
    public void applyAcceleration(GameVector acceleration) {ball.setVelocity(GameVector.addVectors(ball.getVelocity(), acceleration));}

    public void applyAirResistance(){
        double mass = ball.getMass();
        GameVector acceleration = Force.calculateAcceleration(mass, airResistance);
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
    public void ballLaunch(){

        ball.setVelocity( new GameVector(2.0/fps, 10.0/fps));

    }
}
