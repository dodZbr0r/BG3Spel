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
        this.x = length * Math.cos(angle);
        this.y = length * Math.sin(angle);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle(){
        if (y>=0){
          return Math.acos(x/getLength());
        }
        else {
            if (x>=0){
                return Math.asin(y/getLength()) + 2 * Math.PI;
            }
            else {
                return Math.acos(-x/getLength()) + Math.PI;
            }
        }
    }

    public void setPos(double xPos, double yPos) {
        this.x = xPos;
        this.y = yPos;
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

    public static double vectorDotProduct(GameVector vector1, GameVector vector2) {
        return (vector1.getX() * vector2.getX()) + (vector1.getY() * vector2.getY());
    }

    public static double angleBetweenVectors(GameVector vector1, GameVector vector2) {
        return Math.acos(vectorDotProduct(normalizeVector(vector1), normalizeVector(vector2)));
    }

    public static GameVector normalizeVector(GameVector vector) {
        return new GameVector((vector.getX()/vector.getLength()),
                (vector.getY()/vector.getLength()));
    }

    public static void printVector(GameVector vector) {
        //System.out.println("X Value: " + vector.getX());
        //System.out.println("Y Value: " + vector.getY());
    }

}
