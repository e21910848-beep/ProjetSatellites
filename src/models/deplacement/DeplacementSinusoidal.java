package models.deplacement;


import models.Balise;
import models.OceanBounds;
import models.StrategieDeplacementBalise;

public class DeplacementSinusoidal implements StrategieDeplacementBalise {
    private int t = 0;
    private int direction = 1;
    private int speed = 2;

    @Override
    public void deplacer(Balise b, OceanBounds bounds) {
        t++;

        int nx = b.getX() + direction * speed;
        int ny = b.getY() + (int)(5 * Math.sin(t / 10.0));

        if (nx <= bounds.left) {
            nx = bounds.left;
            direction = 1;
        } else if (nx >= bounds.right-15) {
            nx = bounds.right-15;
            direction = -1;
        }

        if (ny < bounds.top) {
            ny = bounds.top;
        } else if (ny > bounds.bottom-15) {
            ny = bounds.bottom-15;
        }

        b.setPosition(nx, ny);
    }
}