package model;

public class Satellite {
    // ATTRIBUTS
    private double x;
    private double y;
    private double vitesse;
    private boolean estDisponible;
    private boolean enCoursDeSync;

    public Satellite(double x, double y, double vitesse) {
        this.x = x;
        this.y = y;
        this.vitesse = vitesse;
        this.estDisponible = false;
        this.enCoursDeSync = false;
    }

    //Méthode de déplacement du satellite
    public void deplacer(double largeurFenetre) {
        x += vitesse;

        if (x > largeurFenetre) {
            x = 0;
        }
    }

    // GETTERS
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getVitesse() {
        return vitesse;
    }
    public boolean isEstDisponible() {
        return estDisponible;
    }
    public boolean isEnCoursDeSync() {
        return enCoursDeSync;
    }

    // SETTERS
    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }
    public void setEnCoursDeSync(boolean enCoursDeSync) {
        this.enCoursDeSync = enCoursDeSync;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }
}
