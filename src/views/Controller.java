package views;

import events.*;
import models.*;
import models.deplacement.DeplacementDescente;
import models.deplacement.DeplacementMontee;


public class Controller implements BaliseListener {
    private OceanBounds bounds;
    private int transferTimer = 0;
    private static final int TRANSFER_TIME = 180; // ~3 seconds at 60fps

    public Controller(OceanBounds bounds) {
        this.bounds = bounds;
    }

    public void tick(Balise b) {
        // On garde le comportement de base (déplacement + machine d’état v1)
        Etat oldEtat = b.getEtat();
        int oldCpt = b.getCpt();

        // Move the balise
        b.getStrategie().deplacer(b, bounds);

        // State machine logic
        switch (b.getEtat()) {
            case COLLECT:
                b.incCpt();
                if (b.isMemoryFull()) {
                    b.setEtat(Etat.ASCENDING);
                    b.setStrategie(new DeplacementMontee());
                    System.out.println("Memory full! Ascending to surface...");
                }
                break;

            case ASCENDING:
                // Movement handled by DeplacementMontee strategy
                // notifyReachedSurface will transition to TRANSFER
                break;

            case TRANSFER:
                transferTimer++;
                System.out.println("Transferring data... " + transferTimer + "/" + TRANSFER_TIME);
                if (transferTimer >= TRANSFER_TIME) {
                    completeDataTransfer(b);
                }
                break;

            case DESCENDING:
                // Movement handled by DeplacementDescente strategy
                // notifyReachedCollectDepth will transition to COLLECT
                break;
        }

        // Partie EventHandler : on génère les events si la balise est une BaliseV2
        if (b instanceof Balise) {
            Balise bv2 = (Balise) b;
            EventHandler handler = bv2.getEventHandler();

            // MÉMOIRE PLEINE : passage COLLECT -> ASCENDING
            if (oldEtat == Etat.COLLECT &&
                    b.getEtat() == Etat.ASCENDING &&
                    b.isMemoryFull()) {

                handler.send(new BaliseMemoryFullEvent(bv2));
            }
        }
    }

    private void completeDataTransfer(Balise b) {
        System.out.println("Data transfer complete! Descending...");
        b.resetCpt();
        transferTimer = 0;
        b.setEtat(Etat.DESCENDING);
        b.setStrategie(new DeplacementDescente());
    }

    public void notifyReachedCollectDepth(Balise b) {
        if (b.getEtat() == Etat.DESCENDING) {
            System.out.println("Reached collection depth! Resuming collection...");
            b.setEtat(Etat.COLLECT);
            b.setStrategie(b.getCollectStrategy());
        }
    }

    public void notifyReachedSurface(Balise b) {
        if (b.getEtat() == Etat.ASCENDING && b instanceof Balise) {
            Balise bv2 = (Balise) b;
            System.out.println("Reached surface! Waiting for satellite... " + bv2);
            bv2.getEventHandler().send(new BaliseReachedSurfaceEvent(bv2));

            // On met la balise en attente de synchro
            b.setEtat(Etat.WAITING_FOR_SYNC);
        }
    }

    // ======= Implémentation de BaliseListener =======

    @Override
    public void memoryFull(BaliseMemoryFullEvent e) {
        Balise b = (Balise) e.getSource();
        System.out.println("[EVENT] Memory full for balise: " + b);
        // Le changement d’état (COLLECT -> ASCENDING) est déjà géré par Controller.tick()
    }

    @Override
    public void reachedSurface(BaliseReachedSurfaceEvent e) {
        Balise b = (Balise) e.getSource();
        System.out.println("[EVENT] Balise reached surface: " + b);
    }

    @Override
    public void satelliteAbove(SatelliteAboveBaliseEvent e) {
        Balise b = e.getBalise();
        Satellite sat = e.getSatellite();

        System.out.println("[EVENT] Satellite above balise: " + b + " / " + sat);

        if (b.getEtat() == Etat.WAITING_FOR_SYNC && sat.isAvailable()) {
            // On déclenche la synchro
            b.startSynchronization(sat);
        }
    }

    @Override
    public void transferStart(BaliseTransferStartEvent e) {
        System.out.println("[EVENT] Transfer started for " + e.getBalise() +
                " with " + e.getSatellite());
    }

    @Override
    public void transferCompleted(BaliseTransferCompletedEvent e) {
        System.out.println("[EVENT] Transfer completed for " + e.getBalise() +
                " with " + e.getSatellite());
        // La balise repasse en DESCENDING dans BaliseV2.completeSynchronization()
    }
}

