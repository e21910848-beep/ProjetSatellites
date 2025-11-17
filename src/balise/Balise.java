package balise;

import java.awt.Point;
import nicellipse.component.NiEllipse;
import java.awt.Color;

public class Balise {
    private Point pos;
    private NiEllipse view;
    private StrategieDeplacementBalise strategie;

    private int cpt = 0;
    private final int memoireMax = 500;
    private Etat etat = Etat.COLLECT;

    private int collectDepth;
    private StrategieDeplacementBalise collectStrategy; // Store original strategy

    public Balise(int x, int y, StrategieDeplacementBalise initial) {
        this.pos = new Point(x, y);
        this.strategie = initial;
        this.collectDepth = y;
        this.collectStrategy = initial; // Remember original strategy

        view = new NiEllipse();
        view.setSize(20, 20); // Smaller for better visibility
        view.setBackground(Color.YELLOW);
        view.setLocation(pos);
    }

    public NiEllipse getView() { return view; }

    public int getX() { return pos.x; }
    public int getY() { return pos.y; }

    public void setPosition(int x, int y) {
        pos.setLocation(x, y);
        view.setLocation(x, y);

        // Only update collectDepth during COLLECT state to handle small adjustments
        if (etat == Etat.COLLECT && Math.abs(y - collectDepth) > 10) {
            collectDepth = y;
        }
    }

    public int getCollectDepth() { return collectDepth; }
    public Etat getEtat() { return etat; }
    public void setEtat(Etat e) { etat = e; }

    public int getCpt() { return cpt; }
    public void resetCpt() { cpt = 0; }
    public void incCpt() {
        if (cpt < memoireMax) {
            cpt++;
        }
    }

    public void setStrategie(StrategieDeplacementBalise s) {
        strategie = s;
    }

    public StrategieDeplacementBalise getStrategie() {
        return strategie;
    }

    public StrategieDeplacementBalise getCollectStrategy() {
        return collectStrategy;
    }

    public boolean isMemoryFull() {
        return cpt >= memoireMax;
    }


}