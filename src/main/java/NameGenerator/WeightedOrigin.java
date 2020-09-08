package NameGenerator;

/**
 * A WeightedOrigin is a name origin mapped to it's associated weighting.
 */
public class WeightedOrigin {

    /**
     * All possible origins.
     */
    enum Origin {
        DUTCH, ENGLISH, FRENCH, IRISH, MAORI, SCOTTISH
    }

    private final Origin origin;
    private final Double weighting;

    /**
     * Constructor for a WeightedOrigin.
     *
     * @param origin Origin of the WeightedOrigin.
     * @param weighting Weighting of the WeightedOrigin.
     */
    public WeightedOrigin(Origin origin, Double weighting) {
        this.origin = origin;
        this.weighting = weighting;
    }

    /**
     * Getter for origin.
     *
     * @return origin.
     */
    public Origin getOrigin() {
        return origin;
    }

    /**
     * Getter for weighting.
     *
     * @return weighting.
     */
    public Double getWeighting() {
        return weighting;
    }
}
