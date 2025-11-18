package events;


public class BaliseMemoryFullEvent extends AbstractEvent {
    private static final long serialVersionUID = 1L;

    public BaliseMemoryFullEvent(Object source) {
        super(source);
    }

    @Override
    public void sendTo(Object target) {
        ((BaliseListener) target).memoryFull(this);
    }
}
