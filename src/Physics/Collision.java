package Physics;

import MainGame.PhysicsObject;

/**
 * Created by Victor on 2016-05-19.
 */
public class Collision {

    /**
     * takes two objects,calculates the angle of their collision and turns one relative to the other.
     * At this point one is moving and one is resting. Finds the moving objects velocity-component in the
     * direction of the collision and calculates a one-dimensional elastic collision. After this the new
     * velocities are added to the objects hence the name.
     * @param movingObject object which is moving when relative to the resting object
     * @param restingObject the relative object.
     */
    public static void setVelocityPostCollision(PhysicsObject movingObject,
                                                PhysicsObject restingObject) {

        //relative velocity calculated
        GameVector movingRelativeVelocity = new GameVector(movingObject.getVelocity().getX()
                - restingObject.getVelocity().getX(),
                movingObject.getVelocity().getY()
                        - restingObject.getVelocity().getY());


        //Vector in the direction of the collision calculated
        GameVector collisionVector = new GameVector(restingObject.getCenter().getX()
                - movingObject.getCenter().getX(),
                restingObject.getCenter().getY()
                        - movingObject.getCenter().getY());


        //Calculate conversion angle
        double conversionAngle = GameVector.angleBetweenVectors(new GameVector(1, 0), collisionVector);


        //Creates new Vector with length of the relativeVelocity and converted with the new angle
        if(collisionVector.getY() >= 0) {
            conversionAngle = -conversionAngle;
        }

        GameVector movingVelocityRelativeToCollision = new GameVector(movingRelativeVelocity.getLength(),
                (movingRelativeVelocity.getAngle() + conversionAngle), true);


        //Calculates the new velocities for X-direction still relative to the collision
        double newVelocityXMoving = getVelocityMovingCollision(movingObject.getMass(),
                restingObject.getMass(), movingVelocityRelativeToCollision.getX());

        double newVelocityXResting = getVelocityRestingCollision(movingObject.getMass(),
                restingObject.getMass(), movingVelocityRelativeToCollision.getX());


        //Vectors updated with new velocities still relative to the collision
        movingVelocityRelativeToCollision.setPos(newVelocityXMoving, movingVelocityRelativeToCollision.getY());
        GameVector restingVelocityRelativeToCollision = new GameVector(newVelocityXResting, 0);

        GameVector finalMovingVelocity = new GameVector(movingVelocityRelativeToCollision.getLength(),
                movingVelocityRelativeToCollision.getAngle() - conversionAngle, true);

        GameVector finalRestingVelocity = new GameVector(restingVelocityRelativeToCollision.getLength(),
                restingVelocityRelativeToCollision.getAngle() - conversionAngle, true);


        //sets new velocity for moving object
        movingObject.setVelocity((finalMovingVelocity.getX()) + (restingObject.getVelocity().getX()),
                (finalMovingVelocity.getY()) + (restingObject.getVelocity().getY()));


        //sets new velocity for resting object
        restingObject.setVelocity((finalRestingVelocity.getX()) + (restingObject.getVelocity().getX()),
                (finalRestingVelocity.getY()) + (restingObject.getVelocity().getY()));
    }

    /**
     * Calculates a the final velocity for the moving object in an elastic collision
     * @param movingMass mass of the moving object
     * @param restingMass mass of the resting object
     * @param movingInitialVelocity initial velocity of the moving object
     * @return final velocity
     */
    private static double getVelocityMovingCollision(double movingMass, double restingMass,
                                                          double movingInitialVelocity) {

        return ((movingMass - restingMass) / (movingMass + restingMass)) * movingInitialVelocity;
    }

    /**
     * Calculates a the final velocity for the resting object in an elastic collision
     * @param movingMass mass of the moving object
     * @param restingMass mass of the resting object
     * @param movingInitialVelocity initial velocity of the moving object
     * @return final velocity
     */
    private static double getVelocityRestingCollision(double movingMass, double restingMass,
                                                    double movingInitialVelocity) {

        return ((2 * movingMass) / (movingMass + restingMass)) * movingInitialVelocity;
    }
}
