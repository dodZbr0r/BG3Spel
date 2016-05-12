package Physics;

import javax.xml.bind.SchemaOutputResolver;

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
     * Calcualtes the acceleration of an object given the force affecting the object
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

    public static GameVector getAirResistance(GameVector velocity){
        double coefficient=1;
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
