package MainGame;

import Physics.GameVector;

import java.awt.*;

/**
 * Created by Erik-S on 2016-05-24.
 */
public class PlayerBall extends Ball {
    private Color alternateColor;
    /**
     * Constructs a PlayerBall object with a position, mass, radius, color and velocity
     *
     * @param x              Horisontal position of the ball(mm)
     * @param y              Vertical position of the ball(mm)
     * @param mass           The balls mass(kg)
     * @param radius         The balls radius(mm)
     * @param primaryColor   The first color of the ball
     * @param alternateColor The second color of the ball
     * @param velocity       The velocity of the ball(mm/s)
     */
    public PlayerBall(double x, double y, double mass, double radius, Color primaryColor, Color alternateColor, GameVector velocity) {
        super(x, y, mass, radius, primaryColor, velocity);
        this.alternateColor = alternateColor;
    }
    @Override
    public Color getAlternateColor() {
        return alternateColor;
    }
    //@Override
    public void setAlternateColor(Color alternateColor) {
        this.alternateColor = alternateColor;
    }
}
