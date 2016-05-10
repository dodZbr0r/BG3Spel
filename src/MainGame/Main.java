package MainGame;

import Graphics.GameComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Victor on 2016-05-10.
 */
public class Main {

    private static double lastUpdate;

    public static void main(String[] args) {
        final Game sim = new Game();
        JFrame frame = new JFrame("Pendel");
        final GameComponent component = new GameComponent(sim);
        frame.setLayout(new BorderLayout());
        frame.add(component, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        lastUpdate = System.currentTimeMillis();

        AbstractAction updateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.currentTimeMillis();
                sim.update(currentTime - lastUpdate);
                System.out.println(currentTime - lastUpdate);
                lastUpdate = currentTime;
                component.repaint();
            }
        };

        Timer timer = new Timer(17, updateAction);
        timer.setCoalesce(true);
        timer.start();
    }


}
