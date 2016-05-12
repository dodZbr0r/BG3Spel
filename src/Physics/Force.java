package Physics;

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
    public static GameVector newtonsSecondLaw (int mass, GameVector acceleration) {
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
    public static GameVector getGravityVector(int mass1, int mass2, GameVector distance) {
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

    public static GameVector getAirResistance(GameVector velocity){
        double coefficient=0.1;
        double density=0.1;
        double area=1;
        double velX=velocity.getX();
        double velY=velocity.getY();
        double forceX = calculateAirRes(coefficient, density, area, velX);
        double forceY = calculateAirRes(coefficient, density, area, velY);
        GameVector airRes = new GameVector(forceX, forceY);

        return airRes;
    }
    public static double calculateAirRes(double coefficient, double density, double area, double velocity){
        int sign=1;
        double res;
        if (velocity >= 0)
                sign=-1;
        return res = sign*0.5*coefficient*density*area*velocity*velocity;
    }

}
