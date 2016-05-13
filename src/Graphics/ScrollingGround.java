package Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Created by Erik-S on 2016-05-12.
 */
public class ScrollingGround {


    private int groundWidth;
    private int groundHeight;
    private BufferedImage ground;
    private BufferedImage ground2;
    private int x1, x2;
    private int y1, y2;

    public ScrollingGround(BufferedImage image){
        groundWidth = GameComponent.WIDTH;
        groundHeight = 80;
        this.ground = image;
        ground2 = deepCopy(image);   // Clones the incoming BufferedImage image

        x1 = 0;
        y1 = 640;
        x2 = groundWidth;
        y2 = 640;

    }

    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void draw(Graphics2D g, int velocityX){
        updatePosition(velocityX);
        g.drawImage(ground, x1, y1, groundWidth * 2, groundHeight, null);
        g.drawImage(ground2, x2, y2, groundWidth * 2, groundHeight, null);
    }

    private void updatePosition(int velocityX) {

        if(x1 <= -groundWidth){
            x1 = groundWidth;
        } else {
            x1 -= velocityX;
        }

        if(x2 <= -groundWidth){
            x2 = groundWidth;
        } else {
            x2 -= velocityX;
        }

    }
}
