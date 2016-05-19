package MainGame;

import Physics.GameVector;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Victor on 2016-05-19.
 */
public abstract class PhysicsObject {

    private double x, y, mass, width, height;
    private Color primaryColor;
    private GameVector velocity;

    public PhysicsObject(double x, double y, double mass, Color primaryColor, GameVector velocity) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.primaryColor = primaryColor;
        this.velocity = velocity;
    }


    //COLLISION

    public boolean closeTo(PhysicsObject object) {
        double vecX = object.getCenter().getX() - this.getCenter().getX();
        double vecY = object.getCenter().getX() - this.getCenter().getX();

        GameVector distance = new GameVector(vecX, vecY);

        return distance.getLength() <= width + object.getWidth();
    }

    public abstract boolean hasCollision(PhysicsObject object);

    //SETTERS

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setVelocity(GameVector velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double x, double y) {
        setVelocity(new GameVector(x, y));
    }

    //GETTERS

    public Point2D.Double getCenter() {
        return new Point2D.Double((x + width / 2), (y - height / 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMass() {
        return mass;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public GameVector getVelocity() {
        return velocity;
    }
}
