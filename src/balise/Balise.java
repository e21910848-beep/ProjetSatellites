package balise;

import nicellipse.component.NiEllipse;
import java.awt.*;

public class Balise extends NiEllipse {
    private Point position;
    private double profondeur;
    private static final Integer CAPACITE = 500;
    private Integer donnees;
    private Etat etat;
    private Point lastPosition;
    private boolean wasFull = false;
    private StrategieDeplacementBalise strategieDeplacement;

    public Balise(int x, int y, double profondeur) {
        this.position = new Point(x, y);
        this.lastPosition = new Point(this.position);
        this.profondeur = profondeur;
        this.donnees = 0;
        this.etat = Etat.COLLECT;
        this.setSize(30, 30);
        this.setBackground(Color.YELLOW);
        this.setCenter(this.position);
    }

    public Balise(int x, int y, double profondeur, StrategieDeplacementBalise strategie) {
        this(x, y, profondeur);
        this.strategieDeplacement = strategie;
    }

    public boolean isfull() {
        boolean full = this.donnees.equals(CAPACITE);

        if (full && !wasFull) {
            this.lastPosition = new Point(this.position);
            wasFull = true;
        }

        if (this.isEmpty()) {
            wasFull = false;
        }

        return full;
    }

    public boolean isEmpty() {
        return this.donnees == 0;
    }

    public boolean collectDonnee() {
        if (!isfull() && etat == Etat.COLLECT) {
            this.donnees++;
            return true;
        }
        return false;
    }

    // Getters and setters
    public double getProfondeur() { return profondeur; }
    public Integer getCapacite() { return CAPACITE; }
    public Integer getDonnees() { return donnees; }
    public Point getLastPosition() { return this.lastPosition; }
    public void setDonnees(Integer donnees) { this.donnees = donnees; }
    public Point getPosition() { return position; }
    public Etat getEtat() { return etat; }
    public void setPosition(final Point position) {
        this.position = position;
        this.setCenter(position);
    }
    public void setProfondeur(final Double profondeur) { this.profondeur = profondeur; }
    public void setEtat(final Etat etat) { this.etat = etat; }
    public void setLastPosition(final Point lastPosition) { this.lastPosition = lastPosition; }
    public StrategieDeplacementBalise getStrategieDeplacement() { return strategieDeplacement; }
    public void setStrategieDeplacement(StrategieDeplacementBalise strategie) {
        this.strategieDeplacement = strategie;
    }
}