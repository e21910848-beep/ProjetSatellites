package models.deplacement;


import models.Balise;
import models.Etat;
import models.OceanBounds;
import models.StrategieDeplacementBalise;

public class DeplacementMontee implements StrategieDeplacementBalise {
    private int speed = 2;

    @Override
    public void deplacer(Balise b, OceanBounds bounds) {
        // Only move if we're still ascending
        if (b.getEtat() == Etat.ASCENDING) {
            int ny = b.getY() - speed;

            if (ny <= bounds.surfaceY) {
                ny = bounds.surfaceY;
                bounds.controller.notifyReachedSurface(b);
            }

            b.setPosition(b.getX(), ny);
        }
        // If we're in TRANSFER state, stay at current position
    }
}