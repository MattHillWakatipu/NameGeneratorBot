package NameGenerator;

import NameGenerator.NameGenerator.Gender;

import java.util.ArrayList;

import static NameGenerator.NameGenerator.Gender.*;
import static NameGenerator.NameGenerator.originMap;
import static NameGenerator.WeightedOrigin.Origin;

public class Name {

    static final double MASCULINE_PERCENTAGE = 0.4;
    static final double FEMININE_PERCENTAGE = 0.4;

    private final Gender gender;
    private final Origin origin;
    private final String firstName;
    private final String surname;

    /**
     * Constructor for truly random name with no parameters selected.
     */
    public Name() {
        this.gender = randomGender();
        this.origin = randomOrigin();
        this.firstName = selectFirstName();
        this.surname = selectSurname();
    }

    /**
     * Constructor for random name with region parameter selected.
     *
     * @param region Region to use demographics of.
     */
    public Name(Region region) {
        this.gender = randomGender();
        this.origin = calculateOrigin(region);
        this.firstName = selectFirstName();
        this.surname = selectSurname();
    }

    /**
     * Constructor for random name with gender parameter selected.
     *
     * @param gender Selected gender to get name of.
     */
    public Name(Gender gender) {
        this.gender = gender;
        this.origin = randomOrigin();
        this.firstName = selectFirstName();
        this.surname = selectSurname();
    }

    /**
     * Constructor for random name with gender parameter and region parameter selected.
     *
     * @param gender Selected gender to get name of.
     * @param region Region to use demographics of.
     */
    public Name(Gender gender, Region region) {
        this.gender = gender;
        this.origin = calculateOrigin(region);
        this.firstName = selectFirstName();
        this.surname = selectSurname();
    }

    //////////////////////////////
    //  Name Selection Methods  //
    //////////////////////////////

    /**
     * Randomly select a first name based off of current origin.
     *
     * @return First name.
     */
    private String selectFirstName() {
        ArrayList<String> nameList = originMap.get(origin).get(gender.toString());
        System.out.println(originMap.size());
        System.out.println(originMap.values().size());
        int lastIndex = nameList.size() - 1;

        int index = (int) (Math.random() * lastIndex);

        return nameList.get(index);
    }

    /**
     * Randomly select a surname based off of current origin.
     *
     * @return Surname.
     */
    private String selectSurname() {
        ArrayList<String> nameList = originMap.get(origin).get("SURNAME");
        int lastIndex = nameList.size() - 1;

        int index = (int) (Math.random() * lastIndex);

        return nameList.get(index);
    }

/*    private String getRandomNameFromFile(String filePath) {
        try {
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);

            if (scanner.hasNext()) {
                return scanner.next();
            }

        } catch (FileNotFoundException e) {
            System.out.println(filePath + "not found");
            e.printStackTrace();
        }
        return null;
    }*/


    /////////////////////////////////
    //  Random Generation Methods  //
    /////////////////////////////////

    /**
     * Randomly select a gender based off of preselected percentages.
     *
     * @return Gender of name.
     */
    private Gender randomGender() {
        double randomValue = Math.random();
        if (randomValue <= MASCULINE_PERCENTAGE) {
            return MASCULINE;
        }
        randomValue -= MASCULINE_PERCENTAGE;
        if (randomValue <= FEMININE_PERCENTAGE) {
            return FEMININE;
        }
        return UNISEX;
    }

    /**
     * Randomly select an origin.
     *
     * @return Origin of name.
     */
    private Origin randomOrigin() {
        int lastIndex = Origin.values().length - 1;
        int randomNumber = (int) (Math.random() * lastIndex);
        return Origin.values()[randomNumber];
    }

    /**
     * Calculates the name origin based off of the region demographics.
     *
     * @param region Region to calculate the origin for.
     * @return NameOrigin to use.
     */
    private Origin calculateOrigin(Region region) {
        double randomValue = Math.random();

        ArrayList<WeightedOrigin> demographics = region.demographics;
        for (WeightedOrigin weightedOrigin : demographics) {
            if (randomValue <= weightedOrigin.getWeighting()) {
                return weightedOrigin.getOrigin();
                //Found origin to use
            } else {
                randomValue -= weightedOrigin.getWeighting();       //remove weighting from random value and continue iterating
            }
        }
        return null;    //TODO throw exception?
    }

    ///////////////
    //  Getters  //
    ///////////////

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public Gender getGender() {
        return gender;
    }

    public Origin getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return "Name{" +
                "gender=" + gender +
                ", origin=" + origin +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    //////////////////////
    //  Helper Methods  //
    //////////////////////

    /**
     * Capitalise the first character of a string. Used for directory names.
     *
     * @param input String to capitalise.
     * @return Capitalised String.
     */
    private String capitaliseFirstChar(String input) {

        if (input.length() == 0) {
            return "";
        }

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
