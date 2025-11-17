package satelite;

import nicellipse.component.NiRectangle;
import java.awt.*;

public class Satellite extends NiRectangle {
    private Point position;
    private int vitesse;
    private int largeurEspace;
    private Color normalColor;
    private Color syncColor;
    private boolean isSyncing;
    private int syncTimer;
    private static final int SYNC_DURATION = 100;

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
}