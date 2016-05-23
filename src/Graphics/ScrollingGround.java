package Graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Created by Erik-S on 2016-05-12.
 */
public class ScrollingGround {


    private double groundWidth;
    private double groundHeight;
    private BufferedImage ground;
    private double x1;
    private double y1;

    ScrollingGround(BufferedImage image){
        groundWidth = GameComponent.getWIDTH();
        groundHeight = 50;
        this.ground = image;

        y1 = 670;
    }

    void draw(Graphics2D g, int velocityX){
        updatePosition(velocityX);
        g.drawImage(ground,(int) x1,(int) y1, (int) (groundWidth * 2), (int) groundHeight, null);
    }

    private void updatePosition(int velocityX) {

        if(x1 <= -groundWidth){
            x1 = 0;
        } else {
            x1 -= velocityX;
        }
    }
}
