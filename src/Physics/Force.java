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
        double result = (massesMultiplied/distanceSquared) * gravConstant;
        GameVector resVector = new GameVector(result, distance.getAngle());
        return resVector;
    }




}
