package NameGenerator;

public class NameOrigin {

    private final String origin;
    private final Double weighting;

    public NameOrigin(String origin, Double weighting) {
        this.origin = origin;
        this.weighting = weighting;
    }

    public String getOrigin() {
        return origin;
    }

    public Double getWeighting() {
        return weighting;
    }
}
