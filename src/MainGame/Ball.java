package MainGame;

import Physics.GameVector;

import java.awt.*;

/**
 * Created by DanielHolmberg on 2016-05-10.
 */
public class Ball {
    private Point position;
    private Color color;
    private int mass;
    private int radius;
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
    public Ball(int x, int y, int mass, int radius, Color color, GameVector velocity){
        position = new Point(x, y);
        this.color = color;
        this.mass = mass;
        this.radius = radius;
        this.velocity = velocity;
    }

    public int getxPos() {
        return position.x;
    }

    public void setxPos(double xPos) {
        position.setLocation(xPos, position.y);
    }

    public int getyPos() {
        return position.y;
    }

    public void setyPos(int yPos) {
        position.setLocation(position.x, yPos);
    }

    public void setPos(int xPos, int yPos) {
        position.setLocation(xPos, yPos);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getDiameter() {
        return radius * 2;
    }

    public GameVector getVelocity() { return velocity; }

    public void setVelocity(GameVector velocity) { this.velocity = velocity; }
}
