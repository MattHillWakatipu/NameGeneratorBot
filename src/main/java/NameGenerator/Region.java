package NameGenerator;

import java.util.ArrayList;

public class Region {

    private final String name;
    ArrayList<NameOrigin> demographics;

    public Region(String name, ArrayList<NameOrigin> demographics) {
        this.name = name;
        this.demographics = demographics;
    }

    public String getName() {
        return name;
    }
}
