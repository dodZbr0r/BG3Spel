package MainGame;

import java.awt.*;

/**
 * Created by Victor on 2016-05-10.
 */
public class Game {

    private Point position;
    private int radius;
    private int speed;

    public Game() {
        this.position = new Point(200, 200);
        this.radius = 20;
        this.speed = 100;
    }

    public void update(double updateTime) {
        position.setLocation(position.x + (speed * updateTime * 0.001), position.y);
    }

    public int getRadius() {
        return radius;
    }

    public Point getPosition() {
        return position;
    }
}
