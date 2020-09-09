package NameGenerator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class NameGeneratorTest {

    private static NameGenerator nameGenerator;
    private static String region;
    private Name name;

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        nameGenerator = new NameGenerator();
        File file = new File("src/main/resources/regions.csv");
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        region = scanner.next();
    }

    @Test
    void generateNameFromParams_0() {
        String[] params = {};
        assertNotNull(nameGenerator.generateNameFromParams(params));
    }

    @Test
    void generateNameFromParams_1_Gender() {
        String[] params = {"F"};
        assertNotNull(nameGenerator.generateNameFromParams(params));
    }

    @Test
    void generateNameFromParams_1_Region() {
        String[] params = {region};
        assertNotNull(nameGenerator.generateNameFromParams(params));

    }

    @Test
    void generateNameFromParams_2() {
        String[] params = {"F", region};
        assertNotNull(nameGenerator.generateNameFromParams(params));

    }

    @Test
    void generateNameFromParams_3() {
        String[] params = {"F", region, region};
        assertThrows(RuntimeException.class, () -> nameGenerator.generateNameFromParams(params));
    }

    @Test
    void randomName() {
        name = nameGenerator.randomName();
        checkNoFieldsNull(name);
    }

    @Test
    void genderName_F() {
        name = nameGenerator.genderName('F');
        checkNoFieldsNull(name);
    }

    @Test
    void genderName_f() {
        name = nameGenerator.genderName('f');
        checkNoFieldsNull(name);
    }

    @Test
    void genderName_M() {
        name = nameGenerator.genderName('m');
        checkNoFieldsNull(name);
    }

    @Test
    void genderName_m() {
        name = nameGenerator.genderName('m');
        checkNoFieldsNull(name);
    }

    @Test
    void genderName_U() {
        name = nameGenerator.genderName('U');
        checkNoFieldsNull(name);
    }

    @Test
    void genderName_u() {
        name = nameGenerator.genderName('U');
        checkNoFieldsNull(name);
    }

    @Test
    void regionName() {
        name = nameGenerator.regionName(region);
        checkNoFieldsNull(name);
    }

    @Test
    void genderRegionName_F() {
        name = nameGenerator.genderRegionName('F', region);
        checkNoFieldsNull(name);
    }

    @Test
    void genderRegionName_M() {
        name = nameGenerator.genderRegionName('M', region);
        checkNoFieldsNull(name);

    }

    @Test
    void genderRegionName_U() {
        name = nameGenerator.genderRegionName('U', region);
        checkNoFieldsNull(name);
    }
    @Test
    void genderRegionName_f() {
        name = nameGenerator.genderRegionName('f', region);
        checkNoFieldsNull(name);
    }

    @Test
    void genderRegionName_m() {
        name = nameGenerator.genderRegionName('m', region);
        checkNoFieldsNull(name);
    }

    @Test
    void genderRegionName_u() {
        name = nameGenerator.genderRegionName('u', region);
        checkNoFieldsNull(name);
    }

    private void checkNoFieldsNull(Name name) {
        assertNotNull(name.getGender());
        assertNotNull(name.getOrigin());
        assertNotNull(name.getFirstName());
        assertNotNull(name.getSurname());
    }
}