package satelite;

public class SatelliteSceneTest {
//    public static void main(String[] args) {
//        NiSpace space = new NiSpace("Simulation Satellites et Balises", new Dimension(800, 600));
//        space.setBackground(Color.WHITE);
//
//        // Create sea area (bottom part - about 2/3 of the space)
//        NiRectangle sea = new NiRectangle();
//        sea.setBackground(new Color(30, 144, 255)); // Blue sea
//        sea.setLocation(0, 400); // Sea starts at y=400
//        sea.setSize(800, 200);   // Sea height is 200 pixels
//        space.add(sea);
//
//        // Create sky area (top part - about 1/3 of the space)
//        // The sky is the white background above the sea
//
//        // Create satellites in the sky (moving left to right)
//        Satellite sat1 = new Satellite(0, 100, 100, 2, space.getWidth()); // Fast satellite
//        Satellite sat2 = new Satellite(200, 150, 150, 1, space.getWidth()); // Medium speed
//        Satellite sat3 = new Satellite(400, 200, 200, 0, space.getWidth()); // Geostationary (speed=0)
//        sat3.setGeostationnaire(true);
//
//        space.add(sat1);
//        space.add(sat2);
//        space.add(sat3);
//
//        // Create balises in the sea
//        Balise balise1 = new Balise(100, 450, 50, new DeplacementHorizontal());
//        Balise balise2 = new Balise(300, 450, 50, new DeplacementVertical());
//        Balise balise3 = new Balise(500, 450, 50, new DeplacementSinusoidal());
//        Balise balise4 = new Balise(700, 450, 50, new DeplacementImmobile());
//
//        sea.add(balise1);
//        sea.add(balise2);
//        sea.add(balise3);
//        sea.add(balise4);
//
//        // Create controllers
//        SatelliteController satController1 = new SatelliteController(sat1);
//        SatelliteController satController2 = new SatelliteController(sat2);
//        SatelliteController satController3 = new SatelliteController(sat3);
//
//        BaliseController baliseController1 = new BaliseController(balise1, sea.getWidth(), sea.getHeight());
//        BaliseController baliseController2 = new BaliseController(balise2, sea.getWidth(), sea.getHeight());
//        BaliseController baliseController3 = new BaliseController(balise3, sea.getWidth(), sea.getHeight());
//        BaliseController baliseController4 = new BaliseController(balise4, sea.getWidth(), sea.getHeight());
//
//        // Start everything
//        satController1.start();
//        satController2.start();
//        satController3.start();
//
//        baliseController1.start();
//        baliseController2.start();
//        baliseController3.start();
//        baliseController4.start();
//
//        space.openInWindow();
//    }
}
