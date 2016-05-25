package MainGame;

import java.awt.*;
import java.util.Random;

/**
 * Created by David on 2016-05-25.
 */
enum BonusBallType {

    SLOW (Color.GREEN, 2.5),
    MEDIUM(Color.BLUE, 5),
    FAST(Color.RED, 7.5),
    VERY_FAST(Color.BLACK, 10);

    private final Color color;
    private final double loadedVelocity;

    BonusBallType(Color color, double yVelocity) {
        this.color = color;
        this.loadedVelocity = yVelocity;
    }

    public static BonusBallType getRandomBallType() {
        Random rng = new Random();
        int randomNumber = rng.nextInt(4);
        switch(randomNumber) {
            case 0:
                return SLOW;
            case 1:
                return MEDIUM;
            case 2:
                return FAST;
            case 3:
                return VERY_FAST;
            default:
                return MEDIUM;
        }
    }

    public Color getColor() {
        return color;
    }

    public double getLoadedVelocity() {
        return loadedVelocity;
    }
}
