package MainGame;

import Physics.GameVector;

import java.awt.*;

/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class Game {

    private Ball ball;

    public Game() {
        ball = new Ball(2000, 2000, 2, 500, Color.RED, new GameVector(1000/60, 1000/60));
    }

    //updaterar spelet
    public void update(double updateTime) {
        ball.setPos(ball.getxPos() + ball.getVelocity().getX(), ball.getyPos() + ball.getVelocity().getY());
    }

    public Ball getBall() {
        return ball;
    }

}
