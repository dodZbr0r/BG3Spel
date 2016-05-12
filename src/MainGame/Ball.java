package MainGame;

import Physics.GameVector;

import java.awt.*;

/**
 * Created by DanielHolmberg on 2016-05-10.
 */
public class Ball {
    private double x, y, mass, radius;
    private Color color;
    private GameVector velocity;

    /**
     * Constructs a Ball object with a position, mass, radius, color and velocity
     * @param x Horisontal position of the ball(mm)
     * @param y Vertical position of the ball(mm)
     * @param mass The balls mass(kg)
     * @param radius The balls radius(mm)
     * @param color The color of the ball
     * @param velocity The velocity of the ball(mm/s)
     */
    public Ball(double x, double y, double mass, double radius, Color color, GameVector velocity){
        this.x = x;
        this.y = y;
        this.color = color;
        this.mass = mass;
        this.radius = radius;
        this.velocity = velocity;
    }

    public double getxPos() {
        return x;
    }

    public void setxPos(double xPos) {
        x = xPos;
    }

    public double getyPos() {
        return y;
    }

    public void setyPos(double yPos) {
        y = yPos;
    }

    public void setPos(double xPos, double yPos) {
        x = xPos;
        y = yPos;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDiameter() {
        return radius * 2;
    }

    public GameVector getVelocity() { return velocity; }

    public void setVelocity(GameVector velocity) { this.velocity = velocity; }
}
