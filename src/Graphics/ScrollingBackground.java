package Graphics;

import MainGame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Created by DanielHolmberg on 2016-05-12.
 */
class ScrollingBackground {
    private Game game;
    private BufferedImage backGround;
    private int x, y;

    ScrollingBackground(BufferedImage image, Game game){
        this.game = game;
        backGround = image;
        x = GameComponent.unitConversion(game.getBackgroundPos());
        y = GameComponent.HEIGHT;
    }

    void draw(Graphics2D g){
        x = GameComponent.unitConversion(game.getBackgroundPos());
        g.drawImage(backGround, x, 0, GameComponent.WIDTH * 2, y, null);
    }
}
