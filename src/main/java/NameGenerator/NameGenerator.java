package NameGenerator;

import NameGenerator.WeightedOrigin.Origin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static NameGenerator.WeightedOrigin.Origin.SCOTTISH;


/**
 *
 */
public class NameGenerator {

    enum Gender {
        MASCULINE,
        FEMININE,
        UNISEX
    }

    //Map of Origins to suffixMaps, suffixMaps map their suffix to lists of names
    public static HashMap<Origin, HashMap<String, ArrayList<String>>> originMap = new HashMap<>();

    public NameGenerator() {
        createNameLists();
    }

    private void createNameLists() {

        String prefix = "src/main/resources/NameLists/";

        ArrayList<String> originList = createOriginList();
        ArrayList<String> suffixList = createSuffixList();

        //For each origin, create a suffixMap, read from file and create arraylist
        for (String origin : originList) {

            HashMap<String, ArrayList<String>> suffixMap = new HashMap<>();
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

    private ArrayList<String> createOriginList() {
        ArrayList<String> origins = new ArrayList<>();
        for (Origin origin : Origin.values()) {
            origins.add(origin.toString().toLowerCase());
        }
        return origins;
    }

    private ArrayList<String> createSuffixList() {
        ArrayList<String> suffixList = new ArrayList<>();
        for (Gender gender : Gender.values()) {
            suffixList.add(gender.toString().toLowerCase());
        }
        suffixList.add("surname");
        return suffixList;
    }

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

    public String generateName() {
        ArrayList<WeightedOrigin> demographics = new ArrayList<>();
        demographics.add(new WeightedOrigin(SCOTTISH, 1.0));
        Region bastille = new Region("Bastille", demographics);
        Name name = new Name(bastille);
        return name.toString();
    }

    public static void main(String[] args) {
        NameGenerator nameGenerator = new NameGenerator();
        nameGenerator.generateName();
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
