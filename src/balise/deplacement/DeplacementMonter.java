package balise.deplacement;

import balise.Balise;
import balise.StrategieDeplacementBalise;
import java.awt.Point;

public class DeplacementMonter implements StrategieDeplacementBalise {
    private double vitesse = 6.0;

    @Override
    public Point deplacer(Point pos, Balise balise, int largeur, int hauteur) {
        if (pos.y > 30) {
            pos.y -= vitesse;
        }
        return pos;
    }
}