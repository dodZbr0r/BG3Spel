package MainGame;

import Physics.GameVector;


import java.awt.*;



/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class Game {

    private Ball ball;
    private int GroundHeight = 500;
    private int GroundWidth = 12800;



    public Game() {
        ball = new Ball(2000, 2000, 2, 500, Color.RED, new GameVector(1000/60, 1000/60));
    }

    //updaterar spelet
    public void update(double updateTime) {
        ball.setPos(ball.getxPos() + ball.getVelocity().getxPos(), ball.getyPos() + ball.getVelocity().getyPos());
        if(ball.getyPos() >= 6200 ){
            ball.setVelocity(new GameVector(ball.getVelocity().getxPos(), -ball.getVelocity().getyPos()));
        }
        if(ball.getyPos() <= 0){
            ball.setVelocity(new GameVector(ball.getVelocity().getxPos(), -ball.getVelocity().getyPos()));
        }
        if(ball.getxPos()+ ball.getRadius() >= 12800){
            ball.setVelocity(new GameVector(-ball.getVelocity().getxPos(), ball.getVelocity().getyPos()));
        }
    }

    public Ball getBall() {
        return ball;
    }

    public int getGroundHeight() {
        return GroundHeight;
    }

    public int getGroundWidth() {
        return GroundWidth;
    }
}
