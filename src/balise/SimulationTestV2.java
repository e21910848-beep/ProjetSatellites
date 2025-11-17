package balise;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import balise.deplacement.DeplacementHorizontal;
import balise.deplacement.DeplacementImmobile;
import balise.deplacement.DeplacementSinusoidal;
import balise.deplacement.DeplacementVertical;
import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

public class SimulationTestV2 {

    NiSpace space = new NiSpace("Simulation V2 - EventHandler Pattern", new Dimension(600, 600));
    NiRectangle sky = new NiRectangle();
    NiRectangle ocean = new NiRectangle();

    OceanBounds bounds;
    ControllerV2 controller;

    BaliseV2 b1;
    BaliseV2 b2;
    BaliseV2 b3;
    BaliseV2 b4;

    SatelliteV2 sat1;
    SatelliteV2 sat2;
    SatelliteV2 sat3;

    public SimulationTestV2() {
        setupEnvironment();
        setupBalises();
        setupSatellites();
        registerBaliseListeners();

        space.openInWindow();
        startAnimation();
    }

    private void setupEnvironment() {
        space.setLayout(null);

        // Ciel : partie haute
        sky.setBackground(Color.WHITE);
        sky.setBounds(0, 0, 600, 250);
        space.add(sky);

        // Océan : partie basse
        ocean.setBackground(Color.BLUE);
        ocean.setBounds(0, 250, 600, 350);
        space.add(ocean);

        // bornes de l’océan (coordonnées relatives à l'ocean)
        bounds = new OceanBounds(
                0,          // left
                600,        // right
                0,          // top (surface)
                350,        // bottom
                0,          // surfaceY = 0 en coord. océan
                null
        );

        controller = new ControllerV2(bounds);
        bounds.controller = controller;
    }

    private void setupBalises() {
        int oceanHeight = ocean.getHeight();
        int oceanX = ocean.getX(); // décalage en X dans la fenêtre

        b1 = new BaliseV2(
                180,
                (int) (Math.random() * oceanHeight * 0.8) + (int) (oceanHeight * 0.1),
                new DeplacementVertical(),
                oceanX
        );

        b2 = new BaliseV2(
                155,
                (int) (Math.random() * oceanHeight * 0.8) + (int) (oceanHeight * 0.1),
                new DeplacementHorizontal(),
                oceanX
        );

        b3 = new BaliseV2(
                100,
                (int) (Math.random() * oceanHeight * 0.8) + (int) (oceanHeight * 0.1),
                new DeplacementSinusoidal(),
                oceanX
        );

        b4 = new BaliseV2(
                140,
                (int) (Math.random() * oceanHeight * 0.8) + (int) (oceanHeight * 0.1),
                new DeplacementImmobile(),
                oceanX
        );

        ocean.add(b1.getView());
        ocean.add(b2.getView());
        ocean.add(b3.getView());
        ocean.add(b4.getView());
    }

    private void setupSatellites() {
        // satellites dans le ciel
        sat1 = new SatelliteV2(0,   50, 2, sky.getWidth());
        sat2 = new SatelliteV2(200, 100, 3, sky.getWidth());
        sat3 = new SatelliteV2(400, 150, 1, sky.getWidth());

        sat1.setNormalColor(Color.RED);
        sat2.setNormalColor(Color.RED);
        sat3.setNormalColor(Color.RED);

        sky.add(sat1);
        sky.add(sat2);
        sky.add(sat3);

        // Les satellites "suivent" les balises (pour pouvoir déclencher l’événement SatelliteAboveBaliseEvent)
        sat1.trackBalise(b1);
        sat1.trackBalise(b2);
        sat1.trackBalise(b3);
        sat1.trackBalise(b4);

        sat2.trackBalise(b1);
        sat2.trackBalise(b2);
        sat2.trackBalise(b3);
        sat2.trackBalise(b4);

        sat3.trackBalise(b1);
        sat3.trackBalise(b2);
        sat3.trackBalise(b3);
        sat3.trackBalise(b4);

        // Debug / info sur clic
        space.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();

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

    private void registerBaliseListeners() {
        // On enregistre le controller comme listener pour toutes les balises V2
        registerForBalise(b1);
        registerForBalise(b2);
        registerForBalise(b3);
        registerForBalise(b4);
    }

    private void registerForBalise(BaliseV2 b) {
        b.getEventHandler().registerListener(BaliseMemoryFullEvent.class, controller);
        b.getEventHandler().registerListener(BaliseReachedSurfaceEvent.class, controller);
        b.getEventHandler().registerListener(SatelliteAboveBaliseEvent.class, controller);
        b.getEventHandler().registerListener(BaliseTransferStartEvent.class, controller);
        b.getEventHandler().registerListener(BaliseTransferCompletedEvent.class, controller);
    }

    private void startAnimation() {
        Timer t = new Timer(20, e -> {
            // Mise à jour des balises
            controller.tick(b1);
            controller.tick(b2);
            controller.tick(b3);
            controller.tick(b4);

            // Mise à jour des satellites
            sat1.deplacer();
            sat2.deplacer();
            sat3.deplacer();

            sky.repaint();
            ocean.repaint();
        });
        t.start();
    }

    public static void main(String[] args) {
        new SimulationTestV2();
    }
}
