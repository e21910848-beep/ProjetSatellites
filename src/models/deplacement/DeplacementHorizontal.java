package models.deplacement;


import models.Balise;
import models.OceanBounds;
import models.StrategieDeplacementBalise;

public class DeplacementHorizontal implements StrategieDeplacementBalise {

    private int dir = 1;
    private int speed = 7;

    @Override
    public void deplacer(Balise b, OceanBounds bounds) {
        int nx = b.getX() + dir * speed;

        if (nx < bounds.left) {
            nx = bounds.left;
            dir = 1;
        }
        if (nx > bounds.right) {
            nx = bounds.right;
            dir = -1;
        }

        b.setPosition(nx, b.getY());
    }
}
