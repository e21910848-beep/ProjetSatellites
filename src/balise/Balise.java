package balise;

import nicellipse.component.NiEllipse;

import java.awt.*;

public class Balise extends NiEllipse {

    private Point position;
    private double profondeur;
    private static final Integer CAPACITER = 500;
    private Integer donne;
    private Etat etat;

    private  Point lastPosition;

    private boolean wasFull= false;

    public Balise(int x , int y,double profondeur){
        this.position = new Point(x,y);
        this.lastPosition = this.position;
        this.profondeur = profondeur;
        this.donne =0;
        this.setSize(30,30);
        this.setBackground(Color.YELLOW);
        this.setCenter(this.position);

    }

    /*
    * return vrai si la mémoires de la balise et  pleine.
    */
    public boolean isfull(){
        boolean full = this.donne.equals(CAPACITER);

        if(full && !wasFull ){
            this.lastPosition = new Point(this.position);
            wasFull = true;
        }

        if(this.isEmpty()){
            wasFull = false;
        }

        return  full;
    }

    public boolean isEmpty(){
        return this.donne == 0;
    }

    /*
    * methode de collection de donnee
    */
    public boolean collectDonnee(){
        if (!isfull()){
          this.donne++;
          return true;
        }
        return false;
    }

    /*
    *methode de changement d'etat d'une balise.
    */
    public void transitionEtat(){
        if (isfull() && this.etat == Etat.COLLECT){
            this.etat = Etat.SYNCRONISER;
        }else if(isEmpty() && this.etat == Etat.SYNCRONISER){
            this.etat = Etat.COLLECT;
        }
    }


    public double getProfondeur() {
        return profondeur;
    }

    public Integer getCapaciter() {
        return CAPACITER;
    }

    public Integer getDonne() {
        return donne;
    }

    public Point getLastPosition(){
        return  this.lastPosition;
    }

    public void setDonne(Integer donne) {
        this.donne = donne;
    }

    public Point getPosition() {
        return position;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setPosition(final Point position) {
        this.position = position;
        this.setCenter(position);
    }


    public void setProfondeur(final Double profondeur){
        this.profondeur = profondeur;
    }

    public void setEtat(final Etat etat){
        this.etat = etat;
    }

    public void setLastPosition(final Point lastPosition){
        this.lastPosition = lastPosition;
    }
}
