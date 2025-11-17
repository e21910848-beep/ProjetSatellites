package balise;


import balise.events.EventHandler;

public class ControllerV2 extends Controller implements BaliseListener {

    public ControllerV2(OceanBounds bounds) {
        super(bounds);
    }

    @Override
    public void tick(Balise b) {
        // On garde le comportement de base (déplacement + machine d’état v1)
        Etat oldEtat = b.getEtat();
        int oldCpt = b.getCpt();

        super.tick(b);

        // Partie EventHandler : on génère les events si la balise est une BaliseV2
        if (b instanceof BaliseV2) {
            BaliseV2 bv2 = (BaliseV2) b;
            EventHandler handler = bv2.getEventHandler();

            // MÉMOIRE PLEINE : passage COLLECT -> ASCENDING
            if (oldEtat == Etat.COLLECT &&
                    b.getEtat() == Etat.ASCENDING &&
                    b.isMemoryFull()) {

                handler.send(new BaliseMemoryFullEvent(bv2));
            }
        }
    }

    @Override
    public void notifyReachedSurface(Balise b) {
        if (b.getEtat() == Etat.ASCENDING && b instanceof BaliseV2) {
            BaliseV2 bv2 = (BaliseV2) b;
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
        BaliseV2 b = e.getBalise();
        SatelliteV2 sat = e.getSatellite();

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
