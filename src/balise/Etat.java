package balise;

public enum Etat {
    COLLECT,
    ASCENDING,    // Moving up to surface
    TRANSFER,     // At surface transferring data
    DESCENDING ,
    TRANSFERRING,
    WAITING_FOR_SYNC,// Moving down to collection depth
    WAIT_SYNC
}