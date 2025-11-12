package balise.deplacement;

import balise.Balise;
import balise.StrategieDeplacementBalise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DeplacementVertical implements StrategieDeplacementBalise {

    private Balise balise;
    private Integer largeur;
    private Integer surfaceY;
    private Integer fondY;
    private Integer direction = 1;
    private boolean enSurface = false;
    private boolean enDecente = false;
    private double vitesse = 1.25;
    Random r = new Random();
    private  int attenteSurface = 0;

    public DeplacementVertical(Balise b, int seaWidth, int seaHeight) {
        this.balise = b;
        this.largeur = seaWidth;
        this.surfaceY = 0;
        this.fondY = seaHeight;
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

                handleVerticalDeplacement(pos);

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

    public void handleVerticalDeplacement(Point point){
        point.y += direction * vitesse;

        if (point.y < 0 || point.y > fondY - balise.getHeight()) {
            direction *= -1;
        }
        balise.collectDonnee();
        balise.setPosition(point);
        balise.repaint();
    }


}
