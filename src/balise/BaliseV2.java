package balise;

import java.awt.Color;
import balise.deplacement.DeplacementDescente;
import balise.deplacement.DeplacementMontee;
import balise.events.EventHandler;

public class BaliseV2 extends Balise {

    // Event handler à la Account2
    private final EventHandler eventHandler;

    // Pour calculer la position X “monde” (océan décalé)
    private final int oceanX;

    public BaliseV2(int x, int y, StrategieDeplacementBalise initial, int oceanX) {
        super(x, y, initial);
        this.oceanX = oceanX;
        this.eventHandler = new EventHandler();
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * X en coordonnées “monde” (espace graphique).
     */
    public int getWorldX() {
        return getX() + oceanX;
    }

    /**
     * Lancement de la synchronisation quand un satellite est au-dessus et dispo.
     */
    public void startSynchronization(SatelliteV2 satellite) {
        if (!satellite.isAvailable()) {
            return;
        }

        System.out.println("Starting synchronization with satellite " + satellite);
        setEtat(Etat.TRANSFERRING);
        getView().setBackground(Color.CYAN);

        satellite.startSync();
        eventHandler.send(new BaliseTransferStartEvent(this, satellite));

        // Simulation simple : transfert pendant ~2s puis fin
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            completeSynchronization(satellite);
        }).start();
    }

    private void completeSynchronization(SatelliteV2 satellite) {
        System.out.println("Synchronization complete for balise " + this);
        resetCpt();
        setEtat(Etat.DESCENDING);
        getView().setBackground(Color.YELLOW);
        setStrategie(new DeplacementDescente());

        eventHandler.send(new BaliseTransferCompletedEvent(this, satellite));
    }

    /**
     * Méthode pratique si un autre composant veut forcer la remontée.
     */
    public void startAscending() {
        setEtat(Etat.ASCENDING);
        setStrategie(new DeplacementMontee());
    }
}
