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
    private int x1, x2;
    private int y1, y2;

    public ScrollingBackground(BufferedImage image, int x, int y, int width, int height){
        screenWidth = width;
        screenHeight = height;
        this.background = image;
        background2 = deepCopy(image);   // Clones the incoming BufferedImage image

        x1 = x;
        y1 = y;
        x2 = width;
        y2 = y;

    }

    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void draw(Graphics2D g, int velocityX){
        updatePosition(velocityX);
        g.drawImage(background, x1, y1, screenWidth, screenHeight, null);
        g.drawImage(background2, x2, y2, screenWidth, screenHeight, null);
    }

    private void updatePosition(int velocityX) {

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
