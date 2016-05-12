package Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Created by DanielHolmberg on 2016-05-12.
 */
public class ScrollingBackground {
    private int screenWidth;
    private int screenHeight;
    private BufferedImage background;
    private BufferedImage background2;
    private double velocityX;
    private int x1, x2;
    private int y1, y2;

    public ScrollingBackground(BufferedImage image, double velocityX){
        screenWidth = GameComponent.WIDTH;
        screenHeight = GameComponent.HEIGHT;
        this.background = image;
        background2 = deepCopy(image);   // Clones the incoming BufferedImage image

        this.velocityX = velocityX;

        x1 = 0;
        y1 = 0;
        x2 = screenWidth;
        y2 = 0;

    }

    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void draw(Graphics2D g){
        updatePosition();
        g.drawImage(background, x1, y1, screenWidth, screenHeight, null);
        g.drawImage(background2, x2, y2, screenWidth, screenHeight, null);
    }

    private void updatePosition() {
        if(x1 <= -screenWidth){
            x1 = screenWidth;
        } else {
            x1 -= velocityX;
        }

        if(x2 <= -screenWidth){
            x2 = screenWidth;
        } else {
            x2 -= velocityX;
        }

    }
}
