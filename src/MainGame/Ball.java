package MainGame;

import Physics.GameVector;

import java.awt.*;

/**
 * Created by DanielHolmberg on 2016-05-10.
 */
public class Ball {
    private double xPos;
    private double yPos;
    private Color color;
    private int mass;
    private int radius;
    private GameVector velocity;

    public Ball(int mass, int radius, Color color, GameVector velocity){
        this.color = color;
        this.mass = mass;
        this.radius = radius;
        this.velocity = velocity;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
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

    public GameVector getVector(GameVector velocity) { return velocity; }

    public void setVector(GameVector velocity) { this.velocity = velocity; }
}
