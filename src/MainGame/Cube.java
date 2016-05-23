package MainGame;

import Physics.GameVector;

import java.awt.*;

/**
 * Created by Victor on 2016-05-19.
 */
public class Cube extends PhysicsObject {

    private Color alternateColor;

    public Cube(double x, double y, double mass, double width, Color primaryColor, Color alternateColor, GameVector velocity) {
        super(x, y, mass, primaryColor, velocity);
        this.alternateColor = alternateColor;
        setWidth(width);
        setHeight(width);
    }

    public boolean hasCollision(PhysicsObject object) {

        return false;

    }

    //GETTERS

    public double getArea() {
        return getWidth() * getWidth();
    }

    public Color getAlternateColor() {
        return alternateColor;
    }

    //SETTERS

    public void setAlternateColor(Color alternateColor) {
        this.alternateColor = alternateColor;
    }
}
