package MainGame;

import Physics.GameVector;


import java.awt.*;



/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class Game {

    public static final double fps = 60;
    private Ball ball;
    private double groundHeight;
    private GameVector gravity;


    /**
     * Constructs a Game with a ball object and ground measurements
     * creates a GameVector representing gravity
     */
    public Game() {
        ball = new Ball(2000, 5000, 2, 500, Color.RED, new GameVector(0/fps, -3000/fps));
        groundHeight = 500;
        gravity = new GameVector(0, -4*(9800/fps)/fps);


    }

    /**
     * Updates the state of the game
     * @param updateTime The time it took to update in milliseconds
     */
    public void update(double updateTime) {
        applyGravity();
        ball.setPos(ball.getxPos() + ball.getVelocity().getX(), ball.getyPos() + ball.getVelocity().getY());

        if(ball.getyPos() - ball.getDiameter() <= groundHeight && ball.getVelocity().getY() < 0) {
            ball.getVelocity().setPos(ball.getVelocity().getX(), -(ball.getVelocity().getY()));
            ball.setPos(ball.getxPos(), groundHeight + ball.getDiameter());
        }

        //Printing some information about the ball
        System.out.printf("HASTIGHET:%5f", ball.getVelocity().getY());
        System.out.printf("   YPOS:%6f", ball.getyPos());

    }

    /**
     * Performs calculations fot the ball to be affected by our gravity vector
     */
    public void applyGravity() {
        ball.setVelocity(GameVector.addVectors(ball.getVelocity(), gravity));
    }

    public Ball getBall() {
        return ball;
    }

    public double getGroundHeight() {
        return groundHeight;
    }
}
