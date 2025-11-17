package balise;


import balise.events.AbstractEvent;

public class BaliseReachedSurfaceEvent extends AbstractEvent {
    private static final long serialVersionUID = 1L;

    public BaliseReachedSurfaceEvent(Object source) {
        super(source);
    }

    @Override
    public void sendTo(Object target) {
        ((BaliseListener) target).reachedSurface(this);
    }
}
