package balise.deplacement;

import balise.Balise;
import balise.StrategieDeplacementBalise;
import java.awt.Point;

public class DeplacementVertical implements StrategieDeplacementBalise {
    private int direction = 1;
    private double vitesse = 10.5;

    @Override
    public Point deplacer(Point pos, Balise balise, int largeur, int hauteur) {
        Point newPos = new Point(pos);
        newPos.y += direction * vitesse;

        if (newPos.y < 20 || newPos.y >= hauteur) {
            direction *= -1;
        }

        return newPos;
    }
}