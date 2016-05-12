package Physics;
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
     * Calculates and creates a gravity force vector between two object,
     * given their masses and the distance and angle between the two object
     * @param mass1 The mass of the first object
     * @param mass2 The mass of the second object
     * @param distance The distancevector between the first and the second object
     * @return The gravity force vector acting on the two objects.
     */
    public static GameVector getGravityVector(double mass1, double mass2, GameVector distance) {
        double massesMultiplied = mass1 * mass2;
        double distanceSquared = Math.pow(distance.getLength(), 2);
        double gravConstant = 6.67 * Math.pow(10, -11);
        double result = (massesMultiplied / distanceSquared) * gravConstant;
        GameVector resVector = new GameVector(result, distance.getAngle());
        return resVector;
    }

    /**
     * Calcualtes the acceleration of an object given the force affecting the object
     * and the mass of the object.
     * @param mass The mass of the object
     * @param force The net force affecting the object
     * @return The object's acceleration
     */
    public static GameVector calculateAcceleration(double mass, GameVector force) {
        double value = force.getLength();
        double newLength = value/mass;
        GameVector acceleration = new GameVector(newLength, force.getAngle());
        return acceleration;
    }

    /**
     * Creates a vector for the total air resistance on a ball based on its density, area and velocity.
     * @param velocity The velocity vector of the ball's movement
     * @param radius The radius of the ball
     * @return The air resistance vector
     */
    public static GameVector getAirResistance(GameVector velocity, double radius){
        double coefficient=1;
        double density=1.2;
        double area=2*radius*Math.PI;
        double forceX = calculateAirRes(coefficient, density, area, velocity.getX());
        double forceY = calculateAirRes(coefficient, density, area, velocity.getY());
        GameVector airRes = new GameVector(forceX, forceY);

        return airRes;
    }

    /**
     * Calculates the air resistance on a ball
     * @param coefficient
     * @param density The density of the air
     * @param area The cross section area of the ball
     * @param velocity The velocity of the ball
     * @return The size and direction of the air resistance force
     */

    public static double calculateAirRes(double coefficient, double density, double area, double velocity){
        int sign=1;
        double res;
        if (velocity >= 0)
                sign=-1;
        return res = sign*0.5*coefficient*density*area*velocity*velocity;
    }

}
