package Physics;

import java.lang.Math;

/**
 * Created by Erik-S on 2016-05-11.
 */
public class GameVector {

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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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
     * Multiplys a vector with a number and creates a new vector
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

    public static void printVector(GameVector vector) {
        System.out.println("X Value: " + vector.getX());
        System.out.println("Y Value: " + vector.getY());
    }

}
