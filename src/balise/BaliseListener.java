package balise;

public interface BaliseListener {

    default void memoryFull(BaliseMemoryFullEvent e) {}
    default void reachedSurface(BaliseReachedSurfaceEvent e) {}
    default void satelliteAbove(SatelliteAboveBaliseEvent e) {}
    default void transferStart(BaliseTransferStartEvent e) {}
    default void transferCompleted(BaliseTransferCompletedEvent e) {}
}
