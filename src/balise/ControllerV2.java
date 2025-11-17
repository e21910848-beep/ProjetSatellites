package balise;


import balise.Controller;
import balise.Etat;
import balise.deplacement.DeplacementMontee;

public class ControllerV2 extends Controller {

    public ControllerV2(balise.OceanBounds bounds) {
        super(bounds);
    }

    @Override
    public void tick(balise.Balise b) {
        super.tick(b); // Use V1 logic for movement

        // Add V2 logic for synchronization
        if (b instanceof BaliseV2) {
            BaliseV2 baliseV2 = (BaliseV2) b;

            switch (baliseV2.getEtat()) {
                case WAITING_FOR_SYNC:
                    baliseV2.trySynchronize();
                    break;
                case TRANSFERRING:
                    // Data transfer in progress
                    break;
            }
        }
    }

    @Override
    public void notifyReachedSurface(balise.Balise b) {
        if (b.getEtat() == Etat.ASCENDING) {
            System.out.println("Reached surface! Waiting for satellite...");
            b.setEtat(Etat.WAITING_FOR_SYNC);
        }
    }
}