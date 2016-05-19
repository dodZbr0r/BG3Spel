package MainGame;

import Physics.GameVector;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by DanielHolmberg on 2016-05-10.
 */
public class Ball extends PhysicsObject {
    private double radius;
    private Color alternateColor;

    /**
     * Constructs a Ball object with a position, mass, radius, color and velocity
     *
     * @param x               Horisontal position of the ball(mm)
     * @param y               Vertical position of the ball(mm)
     * @param mass            The balls mass(kg)
     * @param radius          The balls radius(mm)
     * @param primaryColor    The first color of the ball
     * @param alternateColor  The second color of the ball
     * @param velocity        The velocity of the ball(mm/s)
     */

    public Ball(double x, double y, double mass, double radius, Color primaryColor, Color alternateColor, GameVector velocity) {
        super(x, y, mass, primaryColor, velocity);
        this.radius = radius;
        this.alternateColor = alternateColor;
        setWidth(radius * 2);
        setHeight(radius * 2);

    }

    public boolean hasCollision(PhysicsObject object) {
        return false;
    }

    //SETTTERS

    public void setAlternateColor(Color alternateColor) {
        this.alternateColor = alternateColor;
    }

    //GETTERS

    public Color getAlternateColor() {
        return alternateColor;
    }

    public double getRadius() {
        return radius;
    }

}