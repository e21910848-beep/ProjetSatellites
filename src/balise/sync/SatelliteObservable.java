package balise.sync;

public interface SatelliteObservable {
    void addObserver(BaliseObserver observer);
    void removeObserver(BaliseObserver observer);
    void notifyObservers();
}