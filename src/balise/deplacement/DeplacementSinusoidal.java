package balise.deplacement;

import balise.Balise;
import balise.OceanBounds;
import balise.StrategieDeplacementBalise;

public class DeplacementSinusoidal implements StrategieDeplacementBalise {
    private int t = 0;
    private int direction = 1; // 1 for right, -1 for left
    private int speed = 2;

    @Override
    public void deplacer(Balise b, OceanBounds bounds) {
        t++;

        // Calculate new position with sinusoidal movement
        int nx = b.getX() + direction * speed;
        int ny = b.getY() + (int)(5 * Math.sin(t / 10.0));

        // Handle horizontal bouncing
        if (nx <= bounds.left) {
            nx = bounds.left;
            direction = 1; // Change direction to right
        } else if (nx >= bounds.right-15) {
            nx = bounds.right-15;
            direction = -1; // Change direction to left
        }

        // Handle vertical boundaries (bounce or clamp)
        if (ny < bounds.top) {
            ny = bounds.top;
        } else if (ny > bounds.bottom-15) {
            ny = bounds.bottom-15;
        }

        b.setPosition(nx, ny);
    }
}