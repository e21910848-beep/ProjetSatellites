package balise;


import balise.events.AbstractEvent;

public class BaliseTransferCompletedEvent extends AbstractEvent {
    private static final long serialVersionUID = 1L;

    private final BaliseV2 balise;
    private final SatelliteV2 satellite;

    public BaliseTransferCompletedEvent(BaliseV2 balise, SatelliteV2 satellite) {
        super(balise);
        this.balise = balise;
        this.satellite = satellite;
    }

    public BaliseV2 getBalise() {
        return balise;
    }

    public SatelliteV2 getSatellite() {
        return satellite;
    }

    @Override
    public void sendTo(Object target) {
        ((BaliseListener) target).transferCompleted(this);
    }
}
