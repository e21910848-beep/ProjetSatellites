package satelite;

import satelite.Satellite;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SatelliteController {
    private Satellite satellite;
    private Timer timer;

    public SatelliteController(Satellite satellite) {
        this.satellite = satellite;
        setupTimer();
    }

    private void setupTimer() {
        int delay = 50; // milliseconds
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                satellite.deplacer();
                satellite.repaint();
            }
        };

        timer = new Timer(delay, taskPerformer);
        timer.setRepeats(true);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void synchronize() {
        satellite.startSync();
    }

    public Satellite getSatellite() {
        return satellite;
    }
}