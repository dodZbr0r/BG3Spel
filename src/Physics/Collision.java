package Physics;

import MainGame.PhysicsObject;

/**
 * Created by Victor on 2016-05-19.
 */
public class Collision {

    public static void setVelocityPostCollision(PhysicsObject movingObject,
                                                PhysicsObject restingObject) {

        //movingObject.setVelocity(GameVector.multiplyVector(60, movingObject.getVelocity()));
        //restingObject.setVelocity(GameVector.multiplyVector(60, restingObject.getVelocity()));

        System.out.println("Boll 1 Hastighet: X = " + movingObject.getVelocity().getX()
                + "  Y = " + movingObject.getVelocity().getY() + "\nBoll 2 Hastighet: X = "
                + restingObject.getVelocity().getX() +"  Y = " + restingObject.getVelocity().getY() + "\n");

        //relative velocity calculated
        GameVector movingRelativeVelocity = new GameVector(movingObject.getVelocity().getX()
                - restingObject.getVelocity().getX(),
                movingObject.getVelocity().getY()
                        - restingObject.getVelocity().getY());

        System.out.println("Boll 1 relativ: X = " + movingRelativeVelocity.getX()
                + "  Y = " + movingRelativeVelocity.getY() + "  Längd: " + movingRelativeVelocity.getLength() +  "\n");

        //Vector in the direction of the collision calculated
        GameVector collisionVector = new GameVector(restingObject.getCenter().getX()
                - movingObject.getCenter().getX(),
                restingObject.getCenter().getY()
                        - movingObject.getCenter().getY());

        System.out.println("Kollisionslinje: X = " + collisionVector.getX() +
                "  Y = " + collisionVector.getY() + "\n");

        //Calculate conversion angle
        double conversionAngle = GameVector.angleBetweenVectors(new GameVector(1, 0), collisionVector);

        System.out.println("Konverteringsvinkel: " + conversionAngle);

        //Creates new Vector with length of the relativeVelocity and converted with the new angle
        if(collisionVector.getY() >= 0) {
            conversionAngle = -conversionAngle;
        }

        System.out.println("Konverteringsvinkel efter check: " + conversionAngle + "\n");

        System.out.println("Boll 1 rel vinkel: " + movingRelativeVelocity.getAngle()
                + "\nMinus konvertering: "  + (movingRelativeVelocity.getAngle() + conversionAngle)
                + "\nLängden: " + movingRelativeVelocity.getLength() + "\n");

        GameVector movingVelocityRelativeToCollision = new GameVector(movingRelativeVelocity.getLength(),
                (movingRelativeVelocity.getAngle() + conversionAngle), true);

        System.out.println("Boll 1 kollisionsrelativ: X = " + movingVelocityRelativeToCollision.getX()
                + "  Y = " + movingVelocityRelativeToCollision.getY() + "\n");

        //Calculates the new velocities for X-direction still relative to the collision
        double newVelocityXMoving = getVelocityMovingCollision(movingObject.getMass(),
                restingObject.getMass(), movingVelocityRelativeToCollision.getX());

        double newVelocityXResting = getVelocityRestingCollision(movingObject.getMass(),
                restingObject.getMass(), movingVelocityRelativeToCollision.getX());

        System.out.println("Boll 1 efter kollision: X = " + newVelocityXMoving
                + "  Y = " + movingVelocityRelativeToCollision.getY() + "\nBoll 2 efter kollision: X = "
                + newVelocityXResting +"  Y = " + "0.0" + "\n");

        //Vectors updated new velocities still relative to the collision
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

        System.out.println("Boll 1 Hastighet: X = " + movingObject.getVelocity().getX()
                + "  Y = " + movingObject.getVelocity().getY() + "\nBoll 2 Hastighet: X = "
                + restingObject.getVelocity().getX() + "  Y = " + restingObject.getVelocity().getY());
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
