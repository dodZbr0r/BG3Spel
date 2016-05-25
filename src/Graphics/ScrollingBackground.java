package Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Created by DanielHolmberg on 2016-05-12.
 */
class ScrollingBackground {
    private final double screenWidth;
    private final double screenHeight;
    private final BufferedImage background;
    private double x1;
    private double y1;

    ScrollingBackground(BufferedImage image){
        screenWidth = GameComponent.getWIDTH();
        screenHeight = GameComponent.getHEIGHT();
        this.background = image;

        x1 = 0;
        y1 = 0;

    }

    void draw(Graphics2D g, double velocityX){
        updatePosition(velocityX/4);
        g.drawImage(background, (int) x1, (int) y1, (int) (screenWidth * 2), (int) screenHeight, null);
    }

    private void updatePosition(double velocityX) {

        if(x1 <= -screenWidth){
            x1 = 0;
        } else {
            x1 -= velocityX;
        }
    }
}
