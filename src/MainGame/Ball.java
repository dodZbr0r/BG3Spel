package MainGame;

import Physics.GameVector;

import java.awt.*;

/**
 * Created by DanielHolmberg on 2016-05-10.
 */
public abstract class Ball extends PhysicsObject {
    private final double radius;

    /**
     * Constructs a Ball object with a position, mass, radius, color and velocity
     *  @param x              Horizontal position of the ball(mm)
     * @param y               Vertical position of the ball(mm)
     * @param mass            The balls mass(kg)
     * @param radius          The balls radius(mm)
     * @param primaryColor    The first color of the ball
     * @param velocity        The velocity of the ball(mm/s)
     */

    Ball(double x, double y, double mass, double radius, Color primaryColor, GameVector velocity) {
        super(x, y, mass, primaryColor, velocity);
        this.radius = radius;
        setWidth(radius * 2);
        setHeight(radius * 2);

    }

    public boolean hasCollision(PhysicsObject object) {
        if(object instanceof Ball) {
            double vecX = object.getCenter().getX() - this.getCenter().getX();
            double vecY = object.getCenter().getY() - this.getCenter().getY();
            GameVector distance = new GameVector(vecX, vecY);

            return distance.getLength() <= ((Ball) object).getRadius() + radius;

        } else if(object instanceof Cube) {
            return false;
        }
        return false;
    }

    //GETTERS

    public double getArea() {
        return radius * radius * Math.PI;
    }

    private double getRadius() {
        return radius;
    }


}