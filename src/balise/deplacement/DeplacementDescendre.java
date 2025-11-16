package balise.deplacement;

import balise.Balise;
import balise.StrategieDeplacementBalise;
import java.awt.Point;

public class DeplacementDescendre implements StrategieDeplacementBalise {
    private double vitesse = 2.0;

    @Override
    public Point deplacer(Point pos, Balise balise, int largeur, int hauteur) {
        if (pos.y < hauteur - 30) {
            pos.y += vitesse;
        }
        return pos;
    }
}