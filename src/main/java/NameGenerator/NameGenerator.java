package NameGenerator;

import NameGenerator.WeightedOrigin.Origin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static NameGenerator.NameGenerator.Gender.*;
import static NameGenerator.WeightedOrigin.Origin.SCOTTISH;

/**
 * Main class of the random name generator.
 */
public class NameGenerator {

    enum Gender {
        MASCULINE,
        FEMININE,
        UNISEX
    }

    //Map of Origins to suffixMaps, suffixMaps map their suffix to lists of names
    public static HashMap<Origin, HashMap<String, ArrayList<String>>> originMap = new HashMap<>();
    public static HashMap<String, Region> regionMap = new HashMap<>();

    /**
     * Constructor for NameGenerator.
     */
    public NameGenerator() {
        createNameLists();

        ArrayList<WeightedOrigin> demographics = new ArrayList<>();
        demographics.add(new WeightedOrigin(SCOTTISH, 1.0));
        Region bastille = new Region("Bastille", demographics);
        regionMap.put(bastille.getName(), bastille);
    }

    /**
     * Iterates through all resource files, creates nested map structure and populates the name lists.
     */
    private void createNameLists() {
        String prefix = "src/main/resources/NameLists/";

        ArrayList<String> originList = createOriginList();
        ArrayList<String> suffixList = createSuffixList();

        //For each origin, create a suffixMap, read from file, create arraylist and store in originMap
        for (String origin : originList) {

            HashMap<String, ArrayList<String>> suffixMap = new HashMap<>();             //Map of suffixes to NameLists
            String directory = capitaliseFirstChar(origin);

            for (String suffix : suffixList) {

                String filePath = prefix + directory + "/" + origin + "_" + suffix + ".txt";
                ArrayList<String> nameList = createNameList(filePath);

                suffixMap.put(suffix.toUpperCase(), nameList);
            }

            Origin originEnum = Origin.valueOf(origin.toUpperCase());                   //Convert back to enum
            originMap.put(originEnum, suffixMap);                                       //Put suffixMap into originMap
        }
    }

    /**
     * Converts all origins to Strings for file paths.
     *
     * @return ArrayList of all origins.
     */
    private ArrayList<String> createOriginList() {
        ArrayList<String> origins = new ArrayList<>();
        for (Origin origin : Origin.values()) {
            origins.add(origin.toString().toLowerCase());
        }
        return origins;
    }

    /**
     * Converts genders to Strings for file paths. Add surname to suffix list.
     *
     * @return Arraylist of all suffixes.
     */
    private ArrayList<String> createSuffixList() {
        ArrayList<String> suffixList = new ArrayList<>();
        for (Gender gender : Gender.values()) {
            suffixList.add(gender.toString().toLowerCase());
        }
        suffixList.add("surname");
        return suffixList;
    }

    /**
     * Reads all names from file and returns them in an ArrayList.
     *
     * @param filePath Filepath of the file to read.
     * @return ArrayList of all names in the file.
     */
    private ArrayList<String> createNameList(String filePath) {
        ArrayList<String> nameList = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                nameList.add(scanner.next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return nameList;
    }

    /**
     * Generate a name based on user input.
     *
     * @return Generated name.
     */
    public String generateName() {

        Name name = new Name();
        return name.toString();
    }

    public Name generateName(String regionString) {
        if (!regionMap.containsKey(regionString)) {
            throw new IllegalArgumentException();
        }
        Region region = regionMap.get(regionString);
        return new Name(region);
    }

    public Name generateName(char gender) {
        return switch (gender) {
            case 'M' | 'm' -> new Name(MASCULINE);
            case 'F' | 'f' -> new Name(FEMININE);
            case 'U' | 'u' -> new Name(UNISEX);
            default -> throw new IllegalArgumentException();
        };
    }

    public Name generateName(String regionString, char gender) {
        if (!regionMap.containsKey(regionString)) {
            throw new IllegalArgumentException();
        }
        Region region = regionMap.get(regionString);

        return switch (gender) {
            case 'M' | 'm' -> new Name(MASCULINE, region);
            case 'F' | 'f' -> new Name(FEMININE, region);
            case 'U' | 'u' -> new Name(UNISEX, region);
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Main, used for testing purposes.
     *
     * @param args ignored.
     */
    public static void main(String[] args) {
        NameGenerator nameGenerator = new NameGenerator();
        System.out.println(nameGenerator.generateName());
        System.out.println(nameGenerator.generateName("Bastille"));
        System.out.println(nameGenerator.generateName('u'));
        System.out.println(nameGenerator.generateName("Bastille", 'u'));
    }

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
