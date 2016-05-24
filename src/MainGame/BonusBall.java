package MainGame;

import Physics.GameVector;

import java.awt.*;
import java.util.Random;

/**
 * Created by Erik-S on 2016-05-24.
 */
public class BonusBall extends Ball {
    private Random random;
    private GameVector loadedForce;

    /**
     * Constructs a BonusBall object with a position, mass, radius, color and velocity
     *
     * @param x              Horisontal position of the ball(mm)
     * @param y              Vertical position of the ball(mm)
     * @param mass           The balls mass(kg)
     * @param radius         The balls radius(mm)
     * @param primaryColor   The first color of the ball
     * @param velocity       The velocity of the ball(mm/s)
     */
    public BonusBall(double x, double y, double mass, double radius, Color primaryColor, GameVector velocity) {
        super(x, y, mass, radius, primaryColor, velocity);
        this.loadedForce = setLoadedForce();
    }

    /**
     * Generates a force that will be used for launching the BonusBall.
     */
    private GameVector setLoadedForce(){
        random = new Random();
        int minForce = 1;
        int maxForce = 15;
        return new GameVector(0, random.nextInt(maxForce - minForce) + minForce);
    }

    public GameVector getLoadedForce() {
        return loadedForce;
    }

    @Override
    public Color getAlternateColor() {
        return null;
    }
}
