package balise.deplacement;

import balise.Balise;
import balise.StrategieDeplacementBalise;
import java.awt.Point;

public class DeplacementHorizontal implements StrategieDeplacementBalise {
    private int direction = 1;
    private double vitesse = 7.5;

    @Override
    public Point deplacer(Point pos, Balise balise, int largeur, int hauteur) {
        // Create new point to avoid reference issues
        Point newPos = new Point(pos);
        newPos.x += direction * vitesse;

        if (newPos.x < 10 || newPos.x > largeur - 40) {
            direction *= -1;
        }

        return newPos;
    }
}