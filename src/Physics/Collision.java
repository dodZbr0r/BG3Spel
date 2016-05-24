package Physics;

import MainGame.PhysicsObject;

/**
 * Created by Victor on 2016-05-19.
 */
public class Collision {

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

    public static double getVelocityMovingCollision(double movingMass, double restingMass,
                                                          double movingInitialVelocity) {
        double movingFinalVelocity = ((movingMass - restingMass) /
                (movingMass + restingMass)) * movingInitialVelocity;
        return movingFinalVelocity;
    }

    public static double getVelocityRestingCollision(double movingMass, double restingMass,
                                                    double movingInitialVelocity) {
        double movingFinalVelocity = ((2 * movingMass) /
                (movingMass + restingMass)) * movingInitialVelocity;
        return movingFinalVelocity;
    }
}
