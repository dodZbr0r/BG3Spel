package MainGame;

import Physics.GameVector;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Victor on 2016-05-19.
 */
public abstract class PhysicsObject {

    private double x, y, preX, preY, mass, width, height;
    private Color primaryColor;
    private GameVector velocity;

    PhysicsObject(double x, double y, double mass, Color primaryColor, GameVector velocity) {
        this.x = x;
        this.y = y;
        preX = x;
        preY = y;
        this.mass = mass;
        this.primaryColor = primaryColor;
        this.velocity = velocity;
    }


    //COLLISION

    /**
     * Checks if this object is close to another object
     * @param object The other object
     * @return true if they are close, else false.
     */
    boolean closeTo(PhysicsObject object) {
        double vecX = object.getCenter().getX() - this.getCenter().getX();
        double vecY = object.getCenter().getY() - this.getCenter().getY();

        GameVector distance = new GameVector(vecX, vecY);

        return distance.getLength() <= width + object.getWidth();
    }

    /**
     * Calculates if an object has collided with this object
     * @param object The other object
     * @return true if the objects have collided, else false
     */
    public abstract boolean hasCollision(PhysicsObject object);

    //SETTERS

    void setHeight(double height) {
        this.height = height;
    }

    void setWidth(double width) {
        this.width = width;
    }

    void setPos(double x, double y) {
        preX = this.x;
        preY = this.y;
        this.x = x;
        this.y = y;
    }
    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    void setVelocity(GameVector velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double x, double y) {
        setVelocity(new GameVector(x, y));
    }

    //GETTERS

    public abstract double getArea();

    public Point2D.Double getCenter() {
        return new Point2D.Double((x + width / 2), (y - height / 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    double getPreX() {
        return preX;
    }

    double getPreY() {
        return preY;
    }

    public double getMass() {
        return mass;
    }

    public double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public GameVector getVelocity() {
        return velocity;
    }
}
