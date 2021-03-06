package Physics;

import java.lang.Math;

/**
 * Created by Erik-S on 2016-05-11.
 */

/**
 * A class representing a vector, expressed as the x- and y-coordinates of
 * the point the vector is pointing to. This means the vector is applied relatively
 * to its origin point, and not  relative to a global origo.
 */
public class GameVector {

    //The x- and y-coordinates of the point the vector is pointing to
    private double x;
    private double y;

    /**
     * Constructs a vector from an x and a y value
     * @param x The x value of the vector
     * @param y The y value of the vector
     */
    public GameVector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public GameVector(double length, double angle, boolean polarCoordinates){
        x = length * Math.cos(angle);
        y = length * Math.sin(angle);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle(){
        return Math.atan2(y, x);
    }

    public void setAngle(double newAngle) {
        double length = getLength();
        x = length * Math.cos(newAngle);
        y = length * Math.sin(newAngle);
    }

    public void setPos(double xPos, double yPos) {
        x = xPos;
        y = yPos;
    }


    /**
     * Method to get the length of the vector using pythagoras theorem
     * on the x and y values of the vector
     * @return the length of the vector
     */
    public double getLength() {
        return Math.sqrt( x * x + y * y);
    }


    /**
     * Adds two vectors according to standard vector addition
     * @param vec1 The first vector to add
     * @param vec2 The second vector to add
     * @return The sum of the two vectors
     */
    public static GameVector addVectors(GameVector vec1, GameVector vec2){
        return new GameVector(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY());
    }

    /**
     * Multiplies a vector with a number and creates a new vector
     * with the new length and the same angle as the old vector
     * @param number The number with which to multiply the vector
     * @param vector The vector to be multiplied with
     * @return The new vector
     */
    public static GameVector multiplyVector (double number, GameVector vector) {
        double x = vector.getX() * number;
        double y = vector.getY() * number;
        return new GameVector(x, y);
    }

    /**
     * Calculates the dot product between two vectors
     * @param vector1 The first vector
     * @param vector2 The second vector
     * @return The value of the resulting vector
     */
    private static double vectorDotProduct(GameVector vector1, GameVector vector2) {
        return (vector1.getX() * vector2.getX()) + (vector1.getY() * vector2.getY());
    }

    /**
     * Calculates the angle between two vectors in radians
     * @param vector1 The first vector
     * @param vector2 The second vector
     * @return The angle between the vectors
     */
    static double angleBetweenVectors(GameVector vector1, GameVector vector2) {
        return Math.acos(vectorDotProduct(normalizeVector(vector1), normalizeVector(vector2)));
    }

    /**
     * Sets the length of a vector to 1. Used to make checking angles between
     * vectors easier
     * @param vector THe vector to be normalized
     * @return A new vector with length 1 pointing in the same direction as the old angle
     */
    private static GameVector normalizeVector(GameVector vector) {
        return new GameVector((vector.getX()/vector.getLength()),
                (vector.getY()/vector.getLength()));
    }

}
