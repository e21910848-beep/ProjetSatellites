package balise;

import satelite.Satellite;
import balise.sync.BaliseObserver;
import java.util.ArrayList;
import java.util.List;

public class SatelliteV2 extends Satellite implements balise.sync.SatelliteObservable {
    // Observer pattern additions
    private List<BaliseObserver> observers = new ArrayList<>();

    public SatelliteV2(int x, int y, int vitesse, int largeurEspace) {
        super(x, y, vitesse, largeurEspace);
    }

    // Observer pattern methods
    @Override
    public void addObserver(BaliseObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(BaliseObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (BaliseObserver observer : observers) {
            observer.onSatelliteAbove(this);
        }
    }

    @Override
    public void deplacer() {
        super.deplacer(); // Call V1 movement
        notifyObservers(); // V2 addition: notify observers when moving
    }

    // Helper method to check if satellite is above a balise
    public boolean isAboveBalise(int baliseX, int oceanX) {
        int baliseWorldX = baliseX + oceanX;
        int satelliteX = getPosition().x;
        return Math.abs(satelliteX - baliseWorldX) < 50;
    }
}