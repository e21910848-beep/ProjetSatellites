package balise;

import balise.deplacement.*;
import javax.swing.Timer;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaliseController {
    private Balise balise;
    private Timer timer;
    private int spaceWidth;
    private int spaceHeight;
    private StrategieDeplacementBalise strategieMontee;
    private StrategieDeplacementBalise strategieDescente;
    private StrategieDeplacementBalise strategieImmobile;
    private double profondeurCible; // Target depth for immobile deployment

    public BaliseController(Balise balise, int spaceWidth, int spaceHeight) {
        this.balise = balise;
        this.spaceWidth = spaceWidth;
        this.spaceHeight = spaceHeight;

        this.strategieMontee = new DeplacementMonter();
        this.strategieDescente = new DeplacementDescendre();
        this.strategieImmobile = new DeplacementImmobile();
        this.profondeurCible = balise.getProfondeur();

        setupTimer();
    }

    private void setupTimer() {
        int delay = 50; // milliseconds
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBalise();
            }
        };

        timer = new Timer(delay, taskPerformer);
        timer.setRepeats(true);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    private void updateBalise() {
        Point currentPos = balise.getPosition();
        Point newPos = new Point(currentPos); // Create new point to avoid reference issues

        switch (balise.getEtat()) {
            case COLLECT:
                if (balise.isfull()) {
                    balise.setEtat(Etat.SYNCRONISER);
                    balise.setLastPosition(new Point(currentPos));
                } else {
                    StrategieDeplacementBalise strategie = balise.getStrategieDeplacement();
                    if (strategie != null) {
                        newPos = strategie.deplacer(new Point(currentPos), balise, spaceWidth, spaceHeight);
                        balise.setPosition(newPos);
                        balise.collectDonnee();
                    }
                }
                break;

            case SYNCRONISER:
                // Move to surface
                newPos = strategieMontee.deplacer(new Point(currentPos), balise, spaceWidth, spaceHeight);
                balise.setPosition(newPos);

                // Check if reached surface
                if (newPos.y <= 30) {
                    // Simulate data transfer (in real scenario, wait for satellite)
                    simulateDataTransfer();
                }
                break;

            case TRANSFERT:
                // Descend to target depth
                newPos = strategieDescente.deplacer(new Point(currentPos), balise, spaceWidth, spaceHeight);
                balise.setPosition(newPos);

                // Check if reached target depth
                if (newPos.y >= profondeurCible - 2 && newPos.y <= profondeurCible + 2) {
                    // If strategy is immobile, stay at this depth
                    if (balise.getStrategieDeplacement() instanceof DeplacementImmobile) {
                        balise.setEtat(Etat.IMMOBILE);
                    } else {
                        balise.setEtat(Etat.COLLECT);
                    }
                }
                break;

            case IMMOBILE:
                // Stay immobile but keep collecting data
                if (balise.isfull()) {
                    balise.setEtat(Etat.SYNCRONISER);
                    balise.setLastPosition(new Point(currentPos));
                } else {
                    balise.collectDonnee();
                }
                break;
        }

        balise.repaint();
    }

    private void simulateDataTransfer() {
        // Simulate waiting for satellite synchronization
        try {
            Thread.sleep(1000); // Wait 1 second (simulated transfer time)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Transfer data
        balise.setDonnees(0);
        balise.setEtat(Etat.TRANSFERT);
    }
}