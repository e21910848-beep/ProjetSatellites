package balise;

import balise.deplacement.DeplacementHorizontal;
import balise.deplacement.DeplacementSinusoidal;
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
        int test1 = (int)(Math.random() * (sea.getWidth() / 2 - sea.getHeight() / 2)) + sea.getHeight() / 2;
        int test2 = (int)(Math.random() * (sea.getHeight() / 2 - sea.getWidth() / 2)) + sea.getWidth() / 2;

        Balise balise = new Balise(test1, test2, 150);
        sea.add(balise);

        int test3 = (int)(Math.random() * (sea.getWidth() / 2 - sea.getHeight() / 2)) + sea.getHeight() / 2;
        int test6 = (int)(Math.random() * (sea.getHeight() / 2 - sea.getWidth() / 2)) + sea.getWidth() / 2;

        Balise baliseVertical = new Balise(test6, test3, 150);
        sea.add(baliseVertical);

        int test5 = (int)(Math.random() * (sea.getWidth() / 2 - sea.getHeight() / 2)) + sea.getHeight() / 2;
        int test4 = (int)(Math.random() * (sea.getHeight() / 2 - sea.getWidth() / 2)) + sea.getWidth() / 2;
        Balise baliseSin = new Balise(test4, test5, 150);
        sea.add(baliseSin);

        DeplacementHorizontal dep = new DeplacementHorizontal(balise, sea.getWidth(), sea.getHeight());
        dep.deplacer(balise);

        DeplacementVertical deplacementVertical = new DeplacementVertical(baliseVertical, sea.getWidth(), sea.getHeight());
        deplacementVertical.deplacer(baliseVertical);

        DeplacementSinusoidal deplacementSin = new DeplacementSinusoidal(baliseSin, sea.getWidth(), sea.getHeight());
        deplacementSin.deplacer(baliseSin);

        space.openInWindow();
    }
}