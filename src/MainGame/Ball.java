package MainGame;

import Physics.GameVector;

import java.awt.*;

/**
 * Created by DanielHolmberg on 2016-05-10.
 */
public class Ball {
    private double x, y, mass, radius;
    private Color color1;
    private Color color2;
    private GameVector velocity;

    /**
     * Constructs a Ball object with a position, mass, radius, color and velocity
     *
     * @param x        Horisontal position of the ball(mm)
     * @param y        Vertical position of the ball(mm)
     * @param mass     The balls mass(kg)
     * @param radius   The balls radius(mm)
     * @param color1    The first color of the ball
     * @param color2    The second color of the ball
     * @param velocity The velocity of the ball(mm/s)
     */
    public Ball(double x, double y, double mass, double radius, Color color1, Color color2, GameVector velocity) {
        this.x = x;
        this.y = y;
        this.color1 = color1;
        this.color2 = color2;
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
        return color1;
    }

    public void setColor1(Color color) {
        this.color1 = color;
    }

    public Color getColor2() {
        return color2;
    }
    public void setColor2(Color color) {
        this.color2 = color;
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

    public GameVector getVelocity() {
        return velocity;
    }

    public void setVelocity(GameVector velocity) {
        this.velocity = velocity;
    }


}