package MainGame;

import Physics.GameVector;


import java.awt.*;



/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class Game {

    private Ball ball;
    public static final int fps = 60;
    private int groundHeight = 500;
    private int groundWidth = 12800;



    public Game() {
        ball = new Ball(2000, 5000, 2, 500, Color.RED, new GameVector(1000/fps, -1000/fps));
    }

    //updaterar spelet
    public void update(double updateTime) {
        ball.setPos(ball.getxPos() + ball.getVelocity().getX(), ball.getyPos() + ball.getVelocity().getY());
        if(ball.getyPos() - ball.getDiameter() <= groundHeight) {
            ball.getVelocity().setPos(ball.getVelocity().getX(), -(ball.getVelocity().getY()));
        }


    }

    public Ball getBall() {
        return ball;
    }

    public int getGroundHeight() {
        return groundHeight;
    }

    public int getGroundWidth() {
        return groundWidth;
    }
}
