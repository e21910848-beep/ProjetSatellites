package events;

import models.Balise;
import models.Satellite;

public class BaliseTransferStartEvent extends AbstractEvent {
    private static final long serialVersionUID = 1L;

    private final Balise balise;
    private final Satellite satellite;

    public BaliseTransferStartEvent(Balise balise, Satellite satellite) {
        super(balise);
        this.balise = balise;
        this.satellite = satellite;
    }

    public Balise getBalise() {
        return balise;
    }

    public Satellite getSatellite() {
        return satellite;
    }

    @Override
    public void sendTo(Object target) {
        ((BaliseListener) target).transferStart(this);
    }
}
