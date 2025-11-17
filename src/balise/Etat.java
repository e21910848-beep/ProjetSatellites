package balise;

public enum Etat {
    COLLECT,
    ASCENDING,    // Moving up to surface
    TRANSFER,     // At surface transferring data
    DESCENDING    // Moving down to collection depth
}