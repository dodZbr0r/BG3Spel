package MainGame;

import Physics.GameVector;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Erik-S on 2016-05-24.
 */
public class BonusBall extends Ball {
    private final static Random random = new Random();
    private final static List<Color> bonusBallColors = Arrays.asList(Color.BLACK, Color.BLUE, Color.RED, Color.GREEN);
    private final static int MINFORCE = 1;
    private final static int MAXFORCE = 10;
    private GameVector loadedForce;
    /**
     * Constructs a BonusBall object with a position, mass, radius, color and velocity
     *
     * @param x              Horizontal position of the ball(mm)
     * @param y              Vertical position of the ball(mm)
     * @param mass           The balls mass(kg)
     * @param radius         The balls radius(mm)
     * @param velocity       The velocity of the ball(mm/s)
     */
    BonusBall(double x, double y, double mass, double radius, GameVector velocity) {
        super(x, y, mass, radius, bonusBallColors.get(random.nextInt(bonusBallColors.size())), velocity);
        this.loadedForce = setLoadedForce();

    }

    public void launch() {
        setVelocity(loadedForce);
    }

    /**
     * Generates a force that will be used for launching the BonusBall.
     */
    private GameVector setLoadedForce(){
        return new GameVector(0, random.nextInt(MAXFORCE - MINFORCE) + MINFORCE);
    }

    GameVector getLoadedForce() {
        return loadedForce;
    }


}
