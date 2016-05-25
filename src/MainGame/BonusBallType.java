package MainGame;

import java.awt.*;
import java.util.Random;

/**
 * Created by David on 2016-05-25.
 * Represents the types of bonus balls available for random generation
 */
enum BonusBallType {

    SLOW (Color.GREEN, 5),
    MEDIUM(Color.BLUE, 7.5),
    FAST(Color.RED, 10),
    VERY_FAST(Color.BLACK, 12.5);

    private final Color color;
    private final double loadedVelocity;

    BonusBallType(Color color, double yVelocity) {
        this.color = color;
        this.loadedVelocity = yVelocity;
    }

    /**
     * Generates a random bonus ball type
     * @return A randomly picked bonus ball type
     */
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
