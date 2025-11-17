package balise;

import java.util.ArrayList;
import java.util.List;

import satelite.Satellite;

public class SatelliteV2 extends Satellite {

    // Liste des balises à surveiller (comme "abonnés")
    private final List<BaliseV2> trackedBalises = new ArrayList<>();

    public SatelliteV2(int x, int y, int vitesse, int largeurEspace) {
        super(x, y, vitesse, largeurEspace);
    }

    public void trackBalise(BaliseV2 balise) {
        if (!trackedBalises.contains(balise)) {
            trackedBalises.add(balise);
        }
    }

    public void untrackBalise(BaliseV2 balise) {
        trackedBalises.remove(balise);
    }

    @Override
    public void deplacer() {
        super.deplacer();

        // Après déplacement, on vérifie les balises suivies
        for (BaliseV2 b : trackedBalises) {
            if (this.isAvailable() && isAboveBalise(b)) {
                // EventHandler style : on envoie un événement au handler de la balise
                b.getEventHandler().send(new SatelliteAboveBaliseEvent(b, this));
            }
        }
    }

    // Vérifie si le satellite est à la verticale de la balise
    public boolean isAboveBalise(BaliseV2 balise) {
        int satelliteX = getPosition().x;
        int baliseWorldX = balise.getWorldX();
        return Math.abs(satelliteX - baliseWorldX) < 50;
    }
}
