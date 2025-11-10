package Balises;

import donnee.Donnee;

import java.awt.*;
import java.security.PublicKey;

public class Balise {

    private Point position;
    private double profondeur;
    private final Integer capaciter = 20;
    private Integer donne;
    private Etat etat;

    public Balise(int x , int y,double profondeur){
        this.position = new Point(x,y);
        this.profondeur = profondeur;
        this.donne =0;
    }

    /*
    * return vrai si la mémoires de la balise et  pleine.
    */
    public boolean isfull(){
        return this.donne == this.capaciter;
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

   // public void sendData(){}

    public double getProfondeur() {
        return profondeur;
    }

    public Integer getCapaciter() {
        return capaciter;
    }

    public Integer getDonne() {
        return donne;
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
    }

    public void setProfondeur(final Double profondeur){
        this.profondeur = profondeur;
    }

    public void setEtat(final Etat etat){
        this.etat = etat;
    }
}
