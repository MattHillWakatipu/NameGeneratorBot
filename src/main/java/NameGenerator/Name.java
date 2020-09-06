package NameGenerator;

import NameGenerator.NameGender.Gender;

import java.util.ArrayList;

import static NameGenerator.NameGender.FEMININE_PERCENTAGE;
import static NameGenerator.NameGender.MASCULINE_PERCENTAGE;

public class Name {

    private final String firstName;
    private final String surname;
    private final Gender gender;

    public Name(String firstName, String surname, Gender gender) {
        this.firstName = firstName;
        this.surname = surname;
        this.gender = gender;
    }

    public Name(Region region) {
        NameOrigin origin = calculateOrigin(region);
        this.gender = randomGender();
        this.firstName = selectFirstName(origin);
        this.surname = selectSurname(origin);
    }

    private String selectSurname(NameOrigin origin) {
        return null;
    }

    private String selectFirstName(NameOrigin origin) {
        return null;
    }

    /**
     * Randomly select a gender based off of preselected percentages.
     *
     * @return Gender of name.
     */
    private Gender randomGender() {
        double randomValue = Math.random();
        if (randomValue <= MASCULINE_PERCENTAGE) {
            return Gender.MASCULINE;
        }
        randomValue -= MASCULINE_PERCENTAGE;
        if (randomValue <= FEMININE_PERCENTAGE) {
            return Gender.FEMININE;
        }
        return Gender.UNISEX;
    }

    /**
     * Calculates the name origin based off of the region demographics.
     *
     * @param region Region to calculate the origin for.
     * @return NameOrigin to use.
     */
    private NameOrigin calculateOrigin(Region region) {
        double randomValue = Math.random();

        ArrayList<NameOrigin> demographics = region.demographics;
        for (NameOrigin origin : demographics) {
            if (randomValue <= origin.getWeighting()) {
                return origin;
                //Found origin to use
            } else {
                randomValue -= origin.getWeighting();       //remove weighting from random value and continue iterating
            }
        }
        return null;    //TODO throw exception
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public Gender getGender() {
        return gender;
    }
}
