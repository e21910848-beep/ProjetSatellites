package balise.deplacement;

import balise.Balise;
import balise.OceanBounds;
import balise.Etat;
import balise.StrategieDeplacementBalise;

public class DeplacementDescente implements StrategieDeplacementBalise {
    private int speed = 2;

    @Override
    public void deplacer(Balise b, OceanBounds bounds) {
        // Only move if we're still descending
        if (b.getEtat() == Etat.DESCENDING) {
            int ny = b.getY() + speed;

            if (ny >= b.getCollectDepth()) {
                ny = b.getCollectDepth();
                bounds.controller.notifyReachedCollectDepth(b);
            }

            b.setPosition(b.getX(), ny);
        }
        // If we reached target depth, stay at current position
    }
}