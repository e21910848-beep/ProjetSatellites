package balise;

import java.awt.Point;

public interface StrategieDeplacementBalise {
    Point deplacer(Point currentPosition, Balise balise, int spaceWidth, int spaceHeight);
}