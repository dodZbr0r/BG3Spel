package MainGame;

import Graphics.GameComponent;
import Physics.GameVector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Erik on 2016-05-10.
 */
public class Main{

    private static double lastUpdate;

    /**
     * Main method, creating a Game, a Jframe, and a GameComponent
     * Specifying some parameters of the frame
     * Using an AbstractAction and a timer to update state of the program
     * @param args currently unused
     */
    public static void main(String[] args) {
        final Game game = new Game();
        JFrame frame = new JFrame("CALICUTTA");
        final GameComponent component = new GameComponent(game);
        frame.setLayout(new BorderLayout());
        frame.add(component, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        lastUpdate = System.currentTimeMillis();

        GameVector norm = new GameVector(1, 0);
        GameVector vec1 = new GameVector(5.7, -0.64, true);
        GameVector vec2 = new GameVector(-2, 1);
        GameVector vec3 = new GameVector(-2, -2);
        GameVector vec4 = new GameVector(2, -1);

        System.out.println("X: " + vec1.getX() + "  Y: " + vec1.getY() + "  L: " + vec1.getLength() );
        /*System.out.println(GameVector.angleBetweenVectors(norm, vec1));
        System.out.println(GameVector.angleBetweenVectors(norm, vec2));
        System.out.println(GameVector.angleBetweenVectors(norm, vec3));
        System.out.println(GameVector.angleBetweenVectors(norm, vec4));*/


        AbstractAction updateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Using currentTime and lastUpdate to calculate time for every update
                double currentTime = System.currentTimeMillis();
                game.update(currentTime - lastUpdate);
                lastUpdate = currentTime;
                component.repaint();
            }
        };

        //Using 17 milliseconds since 17ms is close to 1/60s
        Timer timer = new Timer(17, updateAction);
        timer.setCoalesce(true);
        timer.start();
    }


}
