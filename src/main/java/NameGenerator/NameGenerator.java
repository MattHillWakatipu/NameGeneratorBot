package NameGenerator;

import java.util.ArrayList;



/**
 *
 */
public class NameGenerator {

    public NameGenerator() {
        ArrayList<NameOrigin> demographics = new ArrayList<>();
        demographics.add(new NameOrigin("scottish",1.0));
        Region bastille = new Region("Bastille", demographics);
    }

    private void generateName() {

    }
}
