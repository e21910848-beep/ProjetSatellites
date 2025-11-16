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
        sea.setLocation(0, 200);
        sea.setSize(600, 200);
        space.add(sea);

        int startX = sea.getWidth() / 2 - 15;
        int startY = sea.getHeight() / 2 - 15;

        // Create balises with different strategies
        int test1 = (int)(Math.random() * (sea.getWidth() / 2 - sea.getHeight() / 2)) + sea.getHeight() / 2;
        int test2 = (int)(Math.random() * (sea.getHeight() / 2 - sea.getWidth() / 2)) + sea.getWidth() / 2;

        Balise baliseHorizontal = new Balise(startX, startY, 150, new DeplacementHorizontal());
        sea.add(baliseHorizontal);

        int test3 = (int)(Math.random() * (sea.getWidth() / 2 - sea.getHeight() / 2)) + sea.getHeight() / 2;
        int test6 = (int)(Math.random() * (sea.getHeight() / 2 - sea.getWidth() / 2)) + sea.getWidth() / 2;

        Balise baliseVertical = new Balise(startX, startY, 150, new DeplacementVertical());
        sea.add(baliseVertical);

        int test5 = (int)(Math.random() * (sea.getWidth() / 2 - sea.getHeight() / 2)) + sea.getHeight() / 2;
        int test4 = (int)(Math.random() * (sea.getHeight() / 2 - sea.getWidth() / 2)) + sea.getWidth() / 2;
        Balise baliseSin = new Balise(startX, startY, 150, new DeplacementSinusoidal());
        sea.add(baliseSin);

        // Create controllers for each balise
        BaliseController controllerHorizontal = new BaliseController(baliseHorizontal, sea.getWidth(), sea.getHeight());
        BaliseController controllerVertical = new BaliseController(baliseVertical, sea.getWidth(), sea.getHeight());
        BaliseController controllerSin = new BaliseController(baliseSin, sea.getWidth(), sea.getHeight());

        // Start all controllers
        controllerHorizontal.start();
        controllerVertical.start();
        controllerSin.start();

        space.openInWindow();
    }
}