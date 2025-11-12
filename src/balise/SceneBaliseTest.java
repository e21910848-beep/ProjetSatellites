package balise;

import balise.deplacement.DeplacementHorizontal;
import balise.deplacement.DeplacementVertical;
import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

import java.awt.*;

public class SceneBaliseTest {

    public static void main(String[] args) {
        NiSpace space = new NiSpace("Simulation Balise", new Dimension(600, 400));
        space.setBackground(Color.WHITE);

        NiRectangle sea = new NiRectangle();
        sea.setBackground(new Color(30, 144, 255));
        sea.setLocation(0, 200);  // Sea starts at y=200 in space coordinates
        sea.setSize(600, 200);    // Sea height is 200 pixels
        space.add(sea);

        int startX = sea.getWidth() / 2 - 15;
        int startY = sea.getHeight() / 2 - 15;

        Balise balise = new Balise(startX, startY, 150);
        sea.add(balise);

        Balise baliseVertical = new Balise(startX, startY, 150);
        sea.add(baliseVertical);

        DeplacementHorizontal dep = new DeplacementHorizontal(balise, sea.getWidth(), sea.getHeight());
        dep.deplacer(balise);

        DeplacementVertical deplacementVertical = new DeplacementVertical(baliseVertical, sea.getWidth(), sea.getHeight());
        deplacementVertical.deplacer(baliseVertical);

        space.openInWindow();
    }
}