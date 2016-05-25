package MainGame;

import Physics.GameVector;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Erik-S on 2016-05-24.
 */
public class BonusBall extends Ball {
    private final static int MINFORCE = 1;
    private final static int MAXFORCE = 10;
    private GameVector loadedVelocity;
    private boolean isLoaded;
    /**
     * Constructs a BonusBall object with a position, mass, radius, color and velocity
     *
     * @param x              Horizontal position of the ball(mm)
     * @param y              Vertical position of the ball(mm)
     * @param mass           The balls mass(kg)
     * @param radius         The balls radius(mm)
     * @param velocity       The velocity of the ball(mm/s)
     */
    BonusBall(double x, double y, double mass, double radius, Color color, GameVector velocity, GameVector loadedVelocity) {
        super(x, y, mass, radius, color, velocity);
        this.loadedVelocity = loadedVelocity;
        isLoaded = true;

    }

    /**
     * Launches to bonus ball with the a predetermined velocity
     * Changes the color of the bonus ball to gray, to indicate
     * that it cannot be launched anymore
     */
    public void launch() {
        setVelocity(GameVector.addVectors(loadedVelocity, getVelocity()));
        isLoaded = false;
        setPrimaryColor(Color.LIGHT_GRAY);
    }

    GameVector getLoadedVelocity() {
        return loadedVelocity;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    /**
     * Unloads the ball, making it not have any loaded velocity
     */
    public void unload(){
        isLoaded=false;
        setPrimaryColor(Color.LIGHT_GRAY);
    }


}
