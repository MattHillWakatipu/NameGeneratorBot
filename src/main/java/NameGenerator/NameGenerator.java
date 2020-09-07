package NameGenerator;

import java.util.ArrayList;

import static NameGenerator.WeightedOrigin.Origin.*;


/**
 *
 */
public class NameGenerator {

    enum Gender {
        MASCULINE,
        FEMININE,
        UNISEX
    }

    public NameGenerator() {

    }

    private void generateName() {
        ArrayList<WeightedOrigin> demographics = new ArrayList<>();
        demographics.add(new WeightedOrigin(SCOTTISH,1.0));
        Region bastille = new Region("Bastille", demographics);
        Name name = new Name(bastille);
        System.out.println(name);
        name = new Name();
        System.out.println(name);
    }

    public static void main(String[] args) {
        NameGenerator nameGenerator = new NameGenerator();
        nameGenerator.generateName();
    }
}
