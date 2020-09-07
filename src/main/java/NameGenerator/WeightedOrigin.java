package NameGenerator;

public class WeightedOrigin {

    enum Origin{
        SCOTTISH
    }

    private final Origin origin;
    private final Double weighting;

    public WeightedOrigin(Origin origin, Double weighting) {
        this.origin = origin;
        this.weighting = weighting;
    }

    public Origin getOrigin() {
        return origin;
    }

    public Double getWeighting() {
        return weighting;
    }
}
