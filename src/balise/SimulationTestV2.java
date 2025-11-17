package balise;

import java.awt.*;
import javax.swing.Timer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import balise.BaliseV2;
import balise.ControllerV2;
import balise.OceanBounds;
import balise.SatelliteV2;
import balise.deplacement.DeplacementHorizontal;
import balise.deplacement.DeplacementImmobile;
import balise.deplacement.DeplacementSinusoidal;
import balise.deplacement.DeplacementVertical;
import nicellipse.component.*;

public class SimulationTestV2 {

    NiSpace space = new NiSpace("Simulation V2 - Observer Pattern", new Dimension(600, 600));
    NiRectangle sky = new NiRectangle();
    NiRectangle ocean = new NiRectangle();

    OceanBounds bounds;
    ControllerV2 controller;

    BaliseV2 b1;
    BaliseV2 b2;
    BaliseV2 b3;
    BaliseV2 b4;

    // V2 Satellites
    SatelliteV2 sat1;
    SatelliteV2 sat2;
    SatelliteV2 sat3;

    public SimulationTestV2() {
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

        controller = new ControllerV2(bounds);
        bounds.controller = controller;
    }

    private void setupBalises() {
        int oceanHeight = ocean.getHeight();
        int oceanX = ocean.getX(); // Get ocean's X position for coordinate conversion

        // Create V2 balises with different movement strategies
        b1 = new BaliseV2(180, (int)(Math.random() * oceanHeight * 0.8) + (int)(oceanHeight * 0.1),
                new DeplacementVertical(), oceanX);
        b2 = new BaliseV2(155, (int)(Math.random() * oceanHeight * 0.8) + (int)(oceanHeight * 0.1),
                new DeplacementHorizontal(), oceanX);
        b3 = new BaliseV2(100, (int)(Math.random() * oceanHeight * 0.8) + (int)(oceanHeight * 0.1),
                new DeplacementSinusoidal(), oceanX);
        b4 = new BaliseV2(140, (int)(Math.random() * oceanHeight * 0.8) + (int)(oceanHeight * 0.1),
                new DeplacementImmobile(), oceanX);



        ocean.add(b1.getView());
        ocean.add(b2.getView());
        ocean.add(b3.getView());
        ocean.add(b4.getView());
    }

    private void setupSatellites() {
        // Create V2 satellites with different speeds and heights in the sky
        sat1 = new SatelliteV2(0, 50, 2, sky.getWidth());
        sat2 = new SatelliteV2(200, 100, 3, sky.getWidth());
        sat3 = new SatelliteV2(400, 150, 1, sky.getWidth());

        // Set different colors for satellites
        sat1.setNormalColor(Color.RED);
        sat2.setNormalColor(Color.RED);
        sat3.setNormalColor(Color.RED);

        // Add satellites to sky
        sky.add(sat1);
        sky.add(sat2);
        sky.add(sat3);

        // Subscribe all balises to all satellites (Observer pattern)
        b1.subscribeToSatellite(sat1);
        b1.subscribeToSatellite(sat2);
        b1.subscribeToSatellite(sat3);

        b2.subscribeToSatellite(sat1);
        b2.subscribeToSatellite(sat2);
        b2.subscribeToSatellite(sat3);

        b3.subscribeToSatellite(sat1);
        b3.subscribeToSatellite(sat2);
        b3.subscribeToSatellite(sat3);

        b4.subscribeToSatellite(sat1);
        b4.subscribeToSatellite(sat2);
        b4.subscribeToSatellite(sat3);

        // Add mouse listener to test satellite synchronization (manual override)
        space.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();

                // Check if click is on any satellite
                if (sat1.getBounds().contains(clickPoint)) {
                    sat1.startSync();
                    System.out.println("Satellite 1 manually synchronized!");
                } else if (sat2.getBounds().contains(clickPoint)) {
                    sat2.startSync();
                    System.out.println("Satellite 2 manually synchronized!");
                } else if (sat3.getBounds().contains(clickPoint)) {
                    sat3.startSync();
                    System.out.println("Satellite 3 manually synchronized!");
                }

                // Also allow clicking balises to see their state
                if (b1.getView().getBounds().contains(clickPoint)) {
                    System.out.println("Balise 1 - State: " + b1.getEtat() + ", Data: " + b1.getCpt() + "/500");
                } else if (b2.getView().getBounds().contains(clickPoint)) {
                    System.out.println("Balise 2 - State: " + b2.getEtat() + ", Data: " + b2.getCpt() + "/500");
                } else if (b3.getView().getBounds().contains(clickPoint)) {
                    System.out.println("Balise 3 - State: " + b3.getEtat() + ", Data: " + b3.getCpt() + "/500");
                } else if (b4.getView().getBounds().contains(clickPoint)) {
                    System.out.println("Balise 4 - State: " + b4.getEtat() + ", Data: " + b4.getCpt() + "/500");
                }
            }
        });
    }

    private void startAnimation() {
        Timer t = new Timer(20, e -> {
            // Update balises using V2 controller
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
        new SimulationTestV2();
    }
}