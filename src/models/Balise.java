package models;


import events.EventHandler;
import events.*;
import models.deplacement.DeplacementDescente;
import models.deplacement.DeplacementMontee;
import nicellipse.component.NiEllipse;

import java.awt.*;

public class Balise {
    private Point pos;
    private NiEllipse view;
    private StrategieDeplacementBalise strategie;

    private int cpt = 0;
    private final int memoireMax = 500;
    private Etat etat = Etat.COLLECT;

    private int collectDepth;
    private StrategieDeplacementBalise collectStrategy; // Store original strategy

    // Event handler
    private final EventHandler eventHandler;

    // Pour calculer la position X “monde” (océan décalé)
    private final int oceanX;

    public Balise(int x, int y, StrategieDeplacementBalise initial, int oceanX) {
        this.pos = new Point(x, y);
        this.strategie = initial;
        this.collectDepth = y;
        this.collectStrategy = initial; // Remember original strategy

        view = new NiEllipse();
        view.setSize(20, 20); // Smaller for better visibility
        view.setBackground(Color.YELLOW);
        view.setLocation(pos);

        this.oceanX = oceanX;
        this.eventHandler = new EventHandler();
    }





    public int getCpt() { return cpt; }
    public void resetCpt() { cpt = 0; }
    public void incCpt() {
        if (cpt < memoireMax) {
            cpt++;
        }
    }



    public NiEllipse getView() { return view; }

    public int getX() { return pos.x; }
    public int getY() { return pos.y; }

    public void setPosition(int x, int y) {
        pos.setLocation(x, y);
        view.setLocation(x, y);

        // Only update collectDepth during COLLECT state to handle small adjustments
        if (etat == Etat.COLLECT && Math.abs(y - collectDepth) > 10) {
            collectDepth = y;
        }
    }
    public int getCollectDepth() { return collectDepth; }
    public Etat getEtat() { return etat; }
    public void setEtat(Etat e) { etat = e; }


    public void setStrategie(StrategieDeplacementBalise s) {
        strategie = s;
    }

    public StrategieDeplacementBalise getStrategie() {
        return strategie;
    }

    public StrategieDeplacementBalise getCollectStrategy() {
        return collectStrategy;
    }

    public boolean isMemoryFull() {
        return cpt >= memoireMax;
    }


    /****************************************************************************/
    // Partie synchronisation

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    /*** X en coordonnées “monde” (espace graphique). */
    public int getWorldX() {
        return getX() + oceanX;
    }

    /*** Lancement de la synchronisation quand un satellite est au-dessus et dispo.*/
    public void startSynchronization(Satellite satellite) {
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

    private void completeSynchronization(Satellite satellite) {
        System.out.println("Synchronization complete for balise " + this);
        resetCpt();
        setEtat(Etat.DESCENDING);
        getView().setBackground(Color.YELLOW);
        setStrategie(new DeplacementDescente());

        eventHandler.send(new BaliseTransferCompletedEvent(this, satellite));
    }

    /*** Méthode pratique si un autre composant veut forcer la remontée.*/
    public void startAscending() {
        setEtat(Etat.ASCENDING);
        setStrategie(new DeplacementMontee());
    }

}
