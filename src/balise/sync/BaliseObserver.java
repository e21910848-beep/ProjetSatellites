package balise.sync;


import satelite.Satellite;

public interface BaliseObserver {
    void onSatelliteAbove(Satellite satellite);
}