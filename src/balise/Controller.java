package balise;

import balise.deplacement.DeplacementDescente;
import balise.deplacement.DeplacementMontee;

public class Controller {
    private OceanBounds bounds;
    private int transferTimer = 0;
    private static final int TRANSFER_TIME = 180; // ~3 seconds at 60fps

    public Controller(OceanBounds bounds) {
        this.bounds = bounds;
    }

    public void tick(Balise b) {
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
    }

    private void completeDataTransfer(Balise b) {
        System.out.println("Data transfer complete! Descending...");
        b.resetCpt();
        transferTimer = 0;
        b.setEtat(Etat.DESCENDING);
        b.setStrategie(new DeplacementDescente());
    }

    public void notifyReachedSurface(Balise b) {
        if (b.getEtat() == Etat.ASCENDING) {
            System.out.println("Reached surface! Starting data transfer...");
            b.setEtat(Etat.TRANSFER);
            transferTimer = 0; // Start transfer timer
        }
    }

    public void notifyReachedCollectDepth(Balise b) {
        if (b.getEtat() == Etat.DESCENDING) {
            System.out.println("Reached collection depth! Resuming collection...");
            b.setEtat(Etat.COLLECT);
            b.setStrategie(b.getCollectStrategy());
        }
    }
}