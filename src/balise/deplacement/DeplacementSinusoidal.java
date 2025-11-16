package balise.deplacement;

import balise.Balise;
import balise.StrategieDeplacementBalise;
import java.awt.Point;

public class DeplacementSinusoidal implements StrategieDeplacementBalise {
    private int direction = 1;
    private double vitesse = 9;
    private int startY = -1;

    @Override
    public Point deplacer(Point pos, Balise balise, int largeur, int hauteur) {
        Point newPos = new Point(pos);

        if (startY == -1) {
            startY = newPos.y;
        }

        newPos.x += direction * vitesse;
        newPos.y = startY + (int) (20 * Math.sin(newPos.x * 0.03));

        if (newPos.x < 10 || newPos.x > largeur - 40) {
            direction *= -1;
        }

        return newPos;
    }
}