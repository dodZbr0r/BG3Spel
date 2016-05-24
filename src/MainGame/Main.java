package MainGame;

import Graphics.GameComponent;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import Physics.GameVector;
import sun.audio.*;
import java.io.*;

/**
 * Created by Erik on 2016-05-10.
 */
public class Main{

    private static double lastGraphicUpdate;
    private static double lastGameUpdate;

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
        lastGraphicUpdate = System.currentTimeMillis();
        lastGameUpdate = System.currentTimeMillis();

        /*GameVector vec1 = new GameVector(2, 0);
        GameVector vec2 = new GameVector(0, 4);
        GameVector vec3 = new GameVector(-2, -0.0001);
        GameVector vec4 = new GameVector(0, -1);

        System.out.println("vec1: " + vec1.getAngle());
        System.out.println("vec2: " + vec2.getAngle());
        System.out.println("vec3: " + vec3.getAngle());
        System.out.println("vec4: " + vec4.getAngle());*/

        AbstractAction updateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Using currentTime and lastUpdate to calculate time for every update
                double currentTime = System.currentTimeMillis();
                //game.update(currentTime - lastUpdate);
                //System.out.println("\nGFX UPDATETIME: " + (currentTime - lastGraphicUpdate + "\n"));
                lastGraphicUpdate = currentTime;
                component.repaint();
            }
        };

        //Using 17 milliseconds since 17ms is close to 1/60s
        Timer timer = new Timer(17, updateAction);
        timer.setCoalesce(true);
        timer.start();
        //play();

        while(true) {
            double currentTime = System.currentTimeMillis();
            if((currentTime - lastGameUpdate) >= 1 && (currentTime - lastGameUpdate) % 5 == 0) {
                game.update(currentTime - lastGameUpdate);
                //System.out.println("Game Updatetime: " + (currentTime - lastGameUpdate));
                lastGameUpdate = currentTime;
            }
        }
    }

    public static void play() {
        try {
            File file = new File("data/Soundtrack/" + "Calcutta" + ".wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
