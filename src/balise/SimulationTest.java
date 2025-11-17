package balise;

import java.awt.*;
import javax.swing.Timer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import balise.deplacement.DeplacementHorizontal;
import balise.deplacement.DeplacementImmobile;
import balise.deplacement.DeplacementSinusoidal;
import balise.deplacement.DeplacementVertical;
import nicellipse.component.*;
import satelite.Satellite;

public class SimulationTest {

    NiSpace space = new NiSpace("Simulation", new Dimension(600, 600));
    NiRectangle sky = new NiRectangle();
    NiRectangle ocean = new NiRectangle();

    OceanBounds bounds;
    Controller controller;

    Balise b1;
    Balise b2;
    Balise b3;
    Balise b4;

    // Satellites - no controllers needed
    Satellite sat1;
    Satellite sat2;
    Satellite sat3;

    public SimulationTest() {
        setupEnvironment();
        setupBalises();
        setupSatellites();
        space.openInWindow();
        startAnimation();
    }

    private void setupEnvironment() {
        space.setLayout(null);

        // Sky: top part (0 to 250)
        sky.setBackground(Color.WHITE);
        sky.setBounds(0, 0, 600, 250);
        space.add(sky);

        // Ocean: bottom part (250 to 600)
        ocean.setBackground(Color.BLUE);
        ocean.setBounds(0, 250, 600, 350);
        space.add(ocean);

        // Ocean bounds relative to OCEAN coordinates (not space coordinates)
        // In ocean: x from 0 to 600, y from 0 to 350
        bounds = new OceanBounds(
                0,          // left boundary of ocean
                600,        // right boundary of ocean
                0,          // top of ocean (surface line)
                350,        // bottom of ocean
                0,          // surface is at y=0 in ocean coordinates
                null
        );

        controller = new Controller(bounds);
        bounds.controller = controller;
    }

    private void setupBalises() {
        int oceanHeight = ocean.getHeight();

        b1 = new Balise(180, (int)(Math.random() * oceanHeight * 0.8) + (int)(oceanHeight * 0.1), new DeplacementVertical());
        b2 = new Balise(155, (int)(Math.random() * oceanHeight * 0.8) + (int)(oceanHeight * 0.1), new DeplacementHorizontal());
        b3 = new Balise(100, (int)(Math.random() * oceanHeight * 0.8) + (int)(oceanHeight * 0.1), new DeplacementSinusoidal());
        b4 = new Balise(140, (int)(Math.random() * oceanHeight * 0.8) + (int)(oceanHeight * 0.1), new DeplacementImmobile());



        ocean.add(b1.getView());
        ocean.add(b2.getView());
        ocean.add(b3.getView());
        ocean.add(b4.getView());
    }

    private void setupSatellites() {
        // Create satellites with different speeds and heights in the sky
        sat1 = new Satellite(0, 50, 2, sky.getWidth());
        sat2 = new Satellite(200, 100, 3, sky.getWidth());
        sat3 = new Satellite(400, 150, 1, sky.getWidth());

        // Set different colors for satellites
        sat1.setNormalColor(Color.RED);
        sat2.setNormalColor(Color.RED);
        sat3.setNormalColor(Color.RED
        );

        // Add satellites to sky
        sky.add(sat1);
        sky.add(sat2);
        sky.add(sat3);

        // Add mouse listener to test satellite synchronization
        space.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();

                // Check if click is on any satellite
                if (sat1.getBounds().contains(clickPoint)) {
                    sat1.startSync();
                    System.out.println("Satellite 1 synchronizing!");
                } else if (sat2.getBounds().contains(clickPoint)) {
                    sat2.startSync();
                    System.out.println("Satellite 2 synchronizing!");
                } else if (sat3.getBounds().contains(clickPoint)) {
                    sat3.startSync();
                    System.out.println("Satellite 3 synchronizing!");
                }
            }
        });
    }

    private void startAnimation() {
        Timer t = new Timer(20, e -> {
            // Update balises
            controller.tick(b1);
            controller.tick(b2);
            controller.tick(b3);
            controller.tick(b4);

            // Update satellites - same way as balises!
            sat1.deplacer();
            sat2.deplacer();
            sat3.deplacer();

            // Repaint both sky and ocean
            sky.repaint();
            ocean.repaint();
        });
        t.start();
    }

    public static void main(String[] args) {
        new SimulationTest();
    }
}