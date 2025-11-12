package balise.deplacement;

import balise.Balise;
import balise.StrategieDeplacementBalise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DeplacementSinusoidal implements StrategieDeplacementBalise {

    private Balise balise;
    private Integer largeur;
    private Integer surfaceY;
    private Integer fondY;
    private Integer direction = 1;
    private boolean enSurface = false;
    private boolean enDecente = false;
    private double vitesse = 1.7;
    Random r = new Random();
    private  int attenteSurface = 0;
    private int startY;

    public DeplacementSinusoidal(Balise b, int seaWidth, int seaHeight){
        this.balise = b;
        this.largeur = seaWidth;
        this.surfaceY = 0;
        this.fondY = seaHeight;
        this.startY = balise.getPosition().y;
    }


    @Override
    public void deplacer(Balise b) {

        int delay = 1; // milliseconds
        ActionListener taskPerformer = new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                Point pos = balise.getPosition();

                if (balise.isfull() && !enSurface) {
                    handleMontee(pos);
                    return;
                }

                if (enSurface) {
                    handleSurface(pos);
                    return;
                }

                if(enDecente){
                    handleDecente(pos);
                    return;
                }

                handleSinusoidalDeplacement(pos);

            }
        };

        Timer animation = new Timer(delay, taskPerformer);
        animation.setRepeats(true);
        animation.start();

    }


    private void handleMontee(Point pos) {
        if (pos.y > surfaceY + 30) {
            pos.y -= 2;
        } else {
            enSurface = true;
            attenteSurface = 100;
        }
        balise.setPosition(pos);
        balise.repaint();
    }

    private void handleSurface(Point pos) {
        if (attenteSurface > 0) {
            attenteSurface--;
        } else {
            enSurface = false;
            enDecente = true;
            balise.setDonne(0);
        }
        balise.setPosition(pos);
        balise.repaint();
    }

    private void handleDecente(Point point){
        int targetDepth = balise.getLastPosition().y;

        if (point.y < targetDepth){
            point.y +=2;
            balise.setPosition(point);
            balise.repaint();
        }else {
            enDecente = false;
            this.direction = 1;
            balise.setPosition(point);
            balise.repaint();
        }
    }


    public void handleSinusoidalDeplacement(Point point){

        point.x += direction * vitesse;

        point.y = this.startY + (int) (30 * Math.sin(point.x * 0.05));

        // Rebondir sur les bords
        if (point.x < 0 || point.x > largeur - balise.getWidth()) {
            direction *= -1;
        }

        balise.collectDonnee();
        balise.setPosition(point);
        balise.repaint();

    }
}
