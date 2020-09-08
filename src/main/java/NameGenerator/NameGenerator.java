package NameGenerator;

import NameGenerator.WeightedOrigin.Origin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static NameGenerator.NameGenerator.Gender.*;

/**
 * Main class of the random name generator.
 */
public class NameGenerator {

    //Map of Origins to suffixMaps, suffixMaps map their suffix to lists of names
    public static HashMap<Origin, HashMap<String, ArrayList<String>>> originMap = new HashMap<>();
    public static HashMap<String, Region> regionMap = new HashMap<>();

    /**
     * Constructor for NameGenerator.
     * Populates static collections with data read from file.
     */
    public NameGenerator() {
        createNameLists();
        createRegions();
    }

    /**
     * Main, used for testing purposes.
     *
     * @param args ignored.
     */
    public static void main(String[] args) {
        NameGenerator nameGenerator = new NameGenerator();
        String command = "U Alberton";
        String[] params = command.split(" ");
        System.out.println(nameGenerator.generateNameFromParams(params));
        command = "F BuinnLleith";
        params = command.split(" ");
        System.out.println(nameGenerator.generateNameFromParams(params));
        command = "M Gwynloc";
        params = command.split(" ");
        System.out.println(nameGenerator.generateNameFromParams(params));
    }

    //////////////////////
    //  Setup methods   //
    //////////////////////

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
                ArrayList<String> nameList = readNameList(filePath);

                suffixMap.put(suffix.toUpperCase(), nameList);
            }

            Origin originEnum = Origin.valueOf(origin.toUpperCase());                   //Convert back to enum
            originMap.put(originEnum, suffixMap);                                       //Put suffixMap into originMap
        }
    }

    /**
     * Reads through the regions.csv file, creating all regions and storing them in regionMap.
     */
    private void createRegions() {
        try {
            File file = new File("src/main/resources/regions.csv");
            Scanner lineScan = new Scanner(file);

            //For every region in the file, create the region and store in map
            while (lineScan.hasNextLine()) {
                String line = lineScan.nextLine();
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(",");

                String regionName = scanner.next();
                ArrayList<WeightedOrigin> demographics = new ArrayList<>();

                //Read all weightings and store in demographics
                while (scanner.hasNext()) {
                    Origin origin = Origin.valueOf(scanner.next().toUpperCase());       //Turn string into an origin enum
                    double originWeight = scanner.nextDouble();                         //Get weighting
                    demographics.add(new WeightedOrigin(origin, originWeight));         //Add to demographics
                }
                Region region = new Region(regionName, demographics);                   //Create region
                regionMap.put(region.getName(), region);                                //Store region in map
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////
    //  SetUp Helper Methods    //
    //////////////////////////////

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
    private ArrayList<String> readNameList(String filePath) {
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

    //////////////////////////////
    //  Name Generation Methods //
    //////////////////////////////

    /**
     * Generate a name based on passed parameters.
     *
     * @return Generated name.
     */
    public String generateNameFromParams(String[] params) {
        int amountOfParams = params.length;

        switch (amountOfParams) {
            case 0:
                return String.valueOf(randomName());

            case 1:
                //Use gender method if argument is a char, otherwise use region method.
                if (params[0].length() == 1) {
                    return String.valueOf(genderName(params[0].charAt(0)));
                }
                return String.valueOf(regionName(params[0]));

            case 2:
                return String.valueOf(genderRegionName(params[0].charAt(0), params[1]));
        }
        throw new RuntimeException();
    }

    /**
     * Create a purely random name.
     *
     * @return Generated name.
     */
    public Name randomName() {
        return new Name();
    }

    /**
     * Create a random name based off region.
     *
     * @param regionString Region to use demographics of.
     * @return Generated name.
     */
    public Name regionName(String regionString) {
        if (!regionMap.containsKey(regionString)) {
            throw new IllegalArgumentException();
        }
        Region region = regionMap.get(regionString);
        return new Name(region);
    }

    /**
     * Create a random name based off gender.
     *
     * @param gender Gender of first name.
     * @return Generated name.
     */
    public Name genderName(char gender) {
        return switch (gender) {
            case 'M', 'm' -> new Name(MASCULINE);
            case 'F', 'f' -> new Name(FEMININE);
            case 'U', 'u' -> new Name(UNISEX);
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Create a random name based off region and gender.
     *
     * @param gender Gender of first name.
     * @param regionString Region to use demographics of.
     * @return Generated name.
     */
    public Name genderRegionName(char gender, String regionString) {
        if (!regionMap.containsKey(regionString)) {
            throw new IllegalArgumentException();
        }
        Region region = regionMap.get(regionString);

        return switch (gender) {
            case 'M', 'm' -> new Name(MASCULINE, region);
            case 'F', 'f' -> new Name(FEMININE, region);
            case 'U', 'u' -> new Name(UNISEX, region);
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Gender of Name.
     */
    enum Gender {
        MASCULINE,
        FEMININE,
        UNISEX
    }
}
