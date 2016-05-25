package Physics;
import MainGame.PhysicsObject;

import java.lang.Math;

/**
 * Created by David on 2016-05-11.
 */
public class Force {

    /**
     * Calculates a force vector given an objects mass and acceleration
     * @param mass The objects mass
     * @param acceleration The objects acceleration vector
     * @return The force vector acting on the object
     */
    public static GameVector newtonsSecondLaw (double mass, GameVector acceleration) {
        return GameVector.multiplyVector(mass, acceleration);
    }

    /**
     * Calculates the acceleration of an object given the force affecting the object
     * and the mass of the object.
     * @param mass The mass of the object
     * @param force The net force affecting the object
     * @return The object's acceleration
     */
    public static GameVector calculateAcceleration(double mass, GameVector force) {
        double x = force.getX() / mass;
        double y = force.getY() / mass;
        return new GameVector(x, y);
    }

    /**
     * Creates a vector for the total air resistance on a ball based on its density, area and velocity.
     * @param velocity The velocity vector of the ball's movement
     * @param area The cross-section area of the object on which to calculate air resistance
     * @return The air resistance vector
     */
    public static GameVector getAirResistance(GameVector velocity, double area){
        double coefficient=0.47;
        double density=1.2;
        double forceX = calculateAirRes(coefficient, density, area, velocity.getX());
        double forceY = calculateAirRes(coefficient, density, area, velocity.getY());
        return new GameVector(forceX, forceY);
    }

    /**
     * Calculates the air resistance on a ball
     * @param coefficient The resistance coefficient of the air
     * @param density The density of the air
     * @param area The cross section area of the ball
     * @param velocity The velocity of the ball
     * @return The size and direction of the air resistance force
     */

    private static double calculateAirRes(double coefficient, double density, double area, double velocity){
        int sign=1;
        double res;
        if (velocity >= 0)
                sign=-1;
        return res = sign*0.5*coefficient*density*area*velocity*velocity;
    }

    /**
     * Creates a vector for the rolling friction of an object.
     * @param mass The mass of the object
     * @param gravitySize The value of gravitational acceleration
     * @param xVelocity The horizontal velocity of the object
     * @return The friction vector
     */
    public static GameVector getFriction (double mass, double gravitySize, double xVelocity){
        int sign=1;
        double k=0.5;
        double normal = -mass*gravitySize;
        if (xVelocity > 0)
            sign=-1;
        else if (xVelocity == 0)
            sign=0;
        return new GameVector (sign*k*normal, 0);
    }

}
