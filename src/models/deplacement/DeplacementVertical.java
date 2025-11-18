package models.deplacement;


import models.Balise;
import models.OceanBounds;
import models.StrategieDeplacementBalise;

public class DeplacementVertical implements StrategieDeplacementBalise {
    private int dir = 1;
    private int speed = 1;

    @Override
    public void deplacer(Balise b, OceanBounds bounds) {
        int ny = b.getY() + dir * speed;

        // Bounce at ocean boundaries
        if (ny <= bounds.top) {
            ny = bounds.top;
            dir = 1; // Go down
        }
        if (ny >= bounds.bottom -15) {
            ny = bounds.bottom -15;
            dir = -1; // Go up
        }

        b.setPosition(b.getX(), ny);
    }
}