package Physics;

import java.lang.Math;

/**
 * Created by Erik-S on 2016-05-11.
 */
public class GameVector {

    private int xPos;
    private int yPos;

    public GameVector(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getLength() {
        return (int) Math.sqrt((double) xPos * (double) xPos + (double) yPos * (double) yPos);
    }

    public static GameVector addVectors(GameVector vec1, GameVector vec2){
        return new GameVector(vec1.getxPos() + vec2.getxPos(), vec1.getyPos() + vec2.getyPos());
    }


}
