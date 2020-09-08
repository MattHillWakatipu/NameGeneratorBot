package NameGenerator;

import java.util.ArrayList;

/**
 * A region represents any cultural region with a set of demographics, demographics are a list of origins and their
 * associated weightings. These represent the probability that a randomly selected person is of that origin.
 */
public class Region {

    private final String name;
    private final ArrayList<WeightedOrigin> demographics;

    /**
     * Constructor for Region.
     *
     * @param name Name of the region.
     * @param demographics List of WeightedOrigins.
     */
    public Region(String name, ArrayList<WeightedOrigin> demographics) {
        this.name = name;
        this.demographics = demographics;
    }

    /**
     * Getter for name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for demographics.
     *
     * @return demographics.
     */
    public ArrayList<WeightedOrigin> getDemographics() {
        return demographics;
    }
}
