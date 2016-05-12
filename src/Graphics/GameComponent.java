package Graphics;

import MainGame.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Victor, Daniel, Henrik, Linnea, David, Erik on 2016-05-10.
 */
public class GameComponent extends JComponent {

    public Game game;
    // Window bounds
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;
    // Images
    BufferedImage background;
    BufferedImage ground;
    //boolean so you can only give speed to the ball once
    public boolean SPACECLICK = true;


    /**
     * Connstructs a GameComponent with a game object and a few images
     * This class handles most of the graphics
     * @param game Contains all the information about the state of the game
     */
    public GameComponent(Game game) {
        this.game = game;
        setInput();

        try{
            background = ImageIO.read(new File("data/layer-1.png"));
        } catch(IOException e){
            System.out.println("Background Image Not Found");
        }
        try {
            ground = ImageIO.read(new File("data/layer-2.png"));
        } catch (IOException e) {
            System.out.println("Ground Image Not Found");
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.drawImage(background, 0, 0, 1280, 770, this);

        //Ground
        g2.drawImage(ground, 0, 600, WIDTH, unitConversion(game.getGroundHeight()), this);

        // Ball
        g2.setColor(game.getBall().getColor());
        g2.fillOval(unitConversion(game.getBall().getxPos()), yConversion(game.getBall().getyPos()),
                unitConversion(game.getBall().getDiameter()), unitConversion(game.getBall().getDiameter()));

    }

    /**
     * Converts millimeters into pixels
     * @param millimeters Measurements used in game calculations
     * @return An integer representing pixels
     */
    public int unitConversion(int millimeters) {
        return millimeters/10;
    }

    /**
     * Converts y-coordinates from game into the graphical coordinate system
     * @param millimeters Measurements used in game calculations
     * @return An integer representing pixels
     */
    public int yConversion(int millimeters) {
        return HEIGHT - unitConversion(millimeters);
            }

    /**
     * Input method to perform action on KetStroke
     */
    private void setInput() {



        getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "SPACE");
        getActionMap().put("SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(SPACECLICK == true) {
                    game.ballLaunch();
                }
                SPACECLICK = false;
            }
        }
        );
    }
}
