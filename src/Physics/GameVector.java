package Physics;

import java.lang.Math;

/**
 * Created by Erik-S on 2016-05-11.
 */
public class GameVector {

    private int x;
    private int y;
    //Angle is measured in degrees
    private double angle;
    private double length;

    /**
     * Constructs a vector from an x and a y value
     * @param x The x value of the vector
     * @param y The y value of the vector
     */
    public GameVector(int x, int y){
        this.x = x;
        this.y = y;
        updateLengthAndAngle();
    }

    /**
     * Constructs a vector from a length and an angle
     * @param length The length of the vector
     * @param angle The angle of the vector in degrees
     */
    public GameVector(double length, double angle) {
        this.length = length;
        this.angle = angle;
        updateXAndY();
    }

    /**
     * Updates the x value of the vector.
     * To be used when a change has been made
     * to the length or the angle of the vector,
     * and an update needs to be done to the x and y values
     */
    private void updateXAndY() {
        double angle = this.angle;
        angle = Math.toRadians(angle);
        //Updates the x value
        double d = Math.cos(angle) * length;
        Long L = Math.round(d);
        this.x = L.intValue();
        //Updates the y value
        d = Math.sin(angle) * length;
        L = Math.round(d);
        this.y = L.intValue();
    }

    /**
     * Updates the values of length and angle of the vector
     * to match the x and y coordinates.
     */
    private void updateLengthAndAngle() {
        length = calculateLength();
        angle = calculateAngle();
    }

    /**
     * Calculates the angle of the vector in degrees
     * @return The angle of the vector
     */
    public double calculateAngle() {
        double xDouble = (double) getX();
        double hypotenuse = getLength();
        double d = xDouble/hypotenuse;
        double angle = Math.acos(d);
        angle = Math.toDegrees(angle);
        return angle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getLength() {
        return length;
    }

    public double getAngle() {
        return angle;
    }

    public void setPos(int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;
        updateLengthAndAngle();
    }

    /**
     * Method to get the length of the vector using pythagoras theorem
     * on the x and y values of the vector
     * @return the length of the vector
     */
    private double calculateLength() {
        return Math.sqrt((double) x * (double) x + (double) y * (double) y);
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
    public static GameVector multiplyVector (int number, GameVector vector) {
        double newLength = vector.getLength() * number;
        GameVector newVector = new GameVector(newLength, vector.getAngle());
        return newVector;
    }

}
