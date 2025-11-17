package balise;

import balise.Balise;
import balise.Etat;
import balise.deplacement.DeplacementDescente;
import balise.deplacement.DeplacementMontee;
import balise.sync.BaliseObserver;
import satelite.Satellite;
import java.util.ArrayList;
import java.util.List;

public class BaliseV2 extends Balise implements BaliseObserver {
    // Observer pattern additions
    private List<SatelliteV2> observedSatellites = new ArrayList<>();
    private SatelliteV2 availableSatellite;
    private int oceanX;

    public BaliseV2(int x, int y, balise.StrategieDeplacementBalise initial, int oceanX) {
        super(x, y, initial);
        this.oceanX = oceanX;
    }

    // Observer pattern method
    @Override
    public void onSatelliteAbove(Satellite satellite) {
        if (satellite instanceof SatelliteV2) {
            SatelliteV2 satV2 = (SatelliteV2) satellite;
            if (getEtat() == Etat.WAITING_FOR_SYNC && satV2.isAvailable()) {
                if (satV2.isAboveBalise(getX(), oceanX)) {
                    this.availableSatellite = satV2;
                    System.out.println("BaliseV2 detected satellite above!");
                }
            }
        }
    }

    public void subscribeToSatellite(SatelliteV2 satellite) {
        satellite.addObserver(this);
        observedSatellites.add(satellite);
    }

    public void trySynchronize() {
        if (availableSatellite != null && availableSatellite.isAvailable()) {
            startSynchronization(availableSatellite);
        }
    }

    private void startSynchronization(SatelliteV2 satellite) {
        System.out.println("Starting synchronization!");
        satellite.startSync();
        setEtat(Etat.TRANSFERRING);
        getView().setBackground(java.awt.Color.CYAN);

        // Simulate data transfer
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                completeSynchronization();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void completeSynchronization() {
        System.out.println("Synchronization complete!");
        resetCpt();
        setEtat(Etat.DESCENDING);
        this.availableSatellite = null;
        getView().setBackground(java.awt.Color.YELLOW);
        setStrategie(new DeplacementDescente());
    }
}