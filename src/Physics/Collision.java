package Physics;

import MainGame.PhysicsObject;

/**
 * Created by Victor on 2016-05-19.
 */
public class Collision {

    public static void setVelocityPostCollision(PhysicsObject movingObject,
                                                PhysicsObject restingObject) {

        movingObject.setVelocity(GameVector.multiplyVector(60, movingObject.getVelocity()));
        restingObject.setVelocity(GameVector.multiplyVector(60, restingObject.getVelocity()));

        System.out.println("Boll 1 Hastighet: x = " + movingObject.getVelocity().getX() * 60
                + "  y = " + movingObject.getVelocity().getY() * 60 + "   Boll 2 Hastighet: x = "
                + restingObject.getVelocity().getX() * 60 +"  y = " + restingObject.getVelocity().getY() * 60);

        //relative velocity calculated
        GameVector movingRelativeVelocity = new GameVector(movingObject.getVelocity().getX()
                - restingObject.getVelocity().getX(),
                movingObject.getVelocity().getY()
                        - restingObject.getVelocity().getY());

        System.out.println("Boll 1 relativ: x = " + movingRelativeVelocity.getX() * 60
                + "  y = " + movingRelativeVelocity.getY() * 60);

        //Vector in the direction of the collision calculated
        GameVector collisionVector = new GameVector(restingObject.getCenter().getX()
                - movingObject.getCenter().getX(),
                restingObject.getCenter().getY()
                        - movingObject.getCenter().getY());

        System.out.println("Kollisionslinje: x = " + collisionVector.getX() +
                "  y = " + collisionVector.getY());

        //Calculate conversion angle
        double conversionAngle = GameVector.angleBetweenVectors(new GameVector(1, 0), collisionVector);

        System.out.println("Konverteringsvinkel: " + conversionAngle);

        //Creates new Vector with length of the relativeVelocity and converted with the new angle
        if(collisionVector.getY() >= 0) {
            conversionAngle = -conversionAngle;
        }
        System.out.println("Boll 1 rel vinkel: " + movingRelativeVelocity.getAngle()
                + "  minus konvertering: "  + (movingRelativeVelocity.getAngle() + conversionAngle)
                + "  längden: " + movingRelativeVelocity.getLength() * 60);

        GameVector movingVelocityRelativeToCollision = new GameVector(movingRelativeVelocity.getLength(),
                (movingRelativeVelocity.getAngle() + conversionAngle));

        System.out.println("Boll 1 kollisionsrelativ: x = " + movingVelocityRelativeToCollision.getX() * 60
                + "  y = " + movingVelocityRelativeToCollision.getY() * 60);

        //Calculates the new velocities for X-direction still relative to the collision
        double newVelocityXMoving = getVelocityMovingCollision(movingObject.getMass(),
                restingObject.getMass(), movingVelocityRelativeToCollision.getX());

        double newVelocityXResting = getVelocityRestingCollision(movingObject.getMass(),
                restingObject.getMass(), movingVelocityRelativeToCollision.getX());

        //Vectors updated new velocities still relative to the collision
        movingVelocityRelativeToCollision.setPos(newVelocityXMoving, movingVelocityRelativeToCollision.getY());
        GameVector restingVelocityRelativeToCollision = new GameVector(newVelocityXResting, 0);

        GameVector finalMovingVelocity = new GameVector(movingVelocityRelativeToCollision.getLength(),
                movingVelocityRelativeToCollision.getAngle() - conversionAngle, true);
        GameVector finalRestingVelocity = new GameVector(restingVelocityRelativeToCollision.getLength(),
                restingVelocityRelativeToCollision.getAngle() - conversionAngle, true);

        //sets new velocity for moving object
        movingObject.setVelocity((finalMovingVelocity.getX() / 60) + (restingObject.getVelocity().getX())/ 60,
                (finalMovingVelocity.getY())/60 + (restingObject.getVelocity().getY())/60);

        //sets new velocity for resting object
        restingObject.setVelocity((finalRestingVelocity.getX())/60 + (restingObject.getVelocity().getX())/60,
                (finalRestingVelocity.getY())/60 + (restingObject.getVelocity().getY())/60);

        System.out.println("Boll 1 Hastighet: x = " + movingObject.getVelocity().getX() * 60
                + "  y = " + movingObject.getVelocity().getY() * 60 + "   Boll 2 Hastighet: x = "
                + restingObject.getVelocity().getX() * 60 +"  y = " + restingObject.getVelocity().getY() * 60);
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
