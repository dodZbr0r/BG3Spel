package Graphics;

import MainGame.Game;

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
class ScrollingGround {

    private Game game;
    private BufferedImage ground;
    private int x, y;

    ScrollingGround(BufferedImage image, Game game){
        this.game = game;
        ground = image;
        x = GameComponent.unitConversion(game.getGroundPos());
        y = GameComponent.yConversion(game.getGroundHeight());
    }

    void draw(Graphics2D g){
        System.out.println("X = " + x + "   Y = " + y);
        x = GameComponent.unitConversion(game.getGroundPos());
        g.drawImage(ground, x, y, GameComponent.WIDTH * 2, GameComponent.HEIGHT - y, null);
    }

}
