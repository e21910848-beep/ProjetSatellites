package models;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import events.SatelliteAboveBaliseEvent;
import nicellipse.component.NiRectangle;

public class Satellite extends NiRectangle {
    private Point position;
    private int vitesse;
    private int largeurEspace;
    private Color normalColor;
    private Color syncColor;
    private boolean isSyncing;
    private int syncTimer;
    private static final int SYNC_DURATION = 100;
    // Liste des balises à surveiller (comme "abonnés")
    private final List<Balise> trackedBalises = new ArrayList<>();

    public Satellite(int x, int y, int vitesse, int largeurEspace) {
        this.position = new Point(x, y);
        this.vitesse = vitesse;
        this.largeurEspace = largeurEspace;
        this.normalColor = Color.DARK_GRAY;
        this.syncColor = Color.GREEN;
        this.isSyncing = false;

        this.setSize(25, 15);
        this.setBackground(normalColor);
        this.setLocation(position);
    }

    public void deplacer() {
        if (!isSyncing) {
            position.x += vitesse;
            if (position.x > largeurEspace) {
                position.x = -this.getWidth();
            }
            this.setLocation(position);
        }

        if (isSyncing) {
            syncTimer--;
            if (syncTimer <= 0) {
                stopSync();
            }
        }

        // Après déplacement, on vérifie les balises suivies
        for (Balise b : trackedBalises) {
            if (this.isAvailable() && isAboveBalise(b)) {
                // EventHandler style : on envoie un événement au handler de la balise
                b.getEventHandler().send(new SatelliteAboveBaliseEvent(b, this));
            }
        }
    }

    public void startSync() {
        if (!isSyncing) {
            this.isSyncing = true;
            this.syncTimer = SYNC_DURATION;
            this.setBackground(syncColor);
        }
    }

    public void stopSync() {
        this.isSyncing = false;
        this.setBackground(normalColor);
    }

    // ADD THESE MISSING METHODS:
    public boolean isSyncing() {
        return isSyncing;
    }

    public boolean isAvailable() {
        return !isSyncing;
    }

    public Point getPosition() {
        return position;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void setNormalColor(Color color) {
        this.normalColor = color;
        if (!isSyncing) {
            this.setBackground(color);
        }
    }

    public void setSyncColor(Color color) {
        this.syncColor = color;
        if (isSyncing) {
            this.setBackground(color);
        }
    }

    /******** Partie synchro *******/
    public void trackBalise(Balise balise) {
        if (!trackedBalises.contains(balise)) {
            trackedBalises.add(balise);
        }
    }

    public void untrackBalise(Balise balise) {
        trackedBalises.remove(balise);
    }


    // Vérifie si le satellite est à la verticale de la balise
    public boolean isAboveBalise(Balise balise) {
        int satelliteX = getPosition().x;
        int baliseWorldX = balise.getWorldX();
        return Math.abs(satelliteX - baliseWorldX) < 50;
    }
}

