package rockets.model;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

public class RocketUnitTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName("should create rocket successfully when given right parameters to constructor")
    @Test
    public void shouldConstructRocketObject() {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider  manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name, variation,country,manufacturer);
        assertNotNull(bfr);
    }

    @DisplayName("should throw exception when given null manufacturer to constructor")
    @Test
    public void shouldThrowExceptionWhenNoManufacturerGiven() {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        assertThrows(NullPointerException.class, () -> new Rocket(name, variation, country, null));
    }

    @DisplayName("should set rocket massToLEO value")
    @ValueSource(strings = {"10000", "15000"})
    public void shouldSetMassToLEOWhenGivenCorrectValue(String massToLEO) {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");

        Rocket bfr = new Rocket(name,variation, country, manufacturer);

        bfr.setMassToLEO(massToLEO);
        assertEquals(massToLEO, bfr.getMassToLEO());
    }

    @DisplayName("should throw exception when set massToLEO to null")
    @Test
    public void shouldThrowExceptionWhenSetMassToLEOToNull() {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name, variation, country,manufacturer);
        assertThrows(NullPointerException.class, () -> bfr.setMassToLEO(null));
    }

    //A1
    // cici edited
    @DisplayName("should throw exception when pass a invalid MassToLEO to setMassToLEO function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetMassToLEOToInvalid(String massToLEO) {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name,variation,country,manufacturer);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bfr.setMassToLEO("..//~"));
        assertEquals("MassToLEO should contain numbers", exception.getMessage());

        bfr.setMassToLEO("600 to");
        assertEquals("600 to",bfr.getMassToLEO());
    }

    // cici edited
    @DisplayName("should throw exceptions when pass a null MassToGTO to setMassToGTO function")
    @Test
    public void shouldThrowExceptionWhenSetMassToGTOToNull() {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name,variation, country,manufacturer);

//        NullPointerException exception = assertThrows(NullPointerException.class,
//                () -> bfr.setMassToGTO(null));
//        assertEquals("MassToGTO cannot be null or empty", exception.getMessage());



        assertThrows(NullPointerException.class, () -> bfr.setMassToGTO(null));
    }

    // cici edited
    @DisplayName("should throw exception when pass a empty MassToGTO to setMassToLEO function")

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetMassToGTOToEmpty(String massToGTO) {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name,variation, country,manufacturer);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bfr.setMassToGTO(massToGTO));
        assertEquals("MassToGTO cannot be null or empty", exception.getMessage());
    }

    // cici edited
    @DisplayName("should throw exception when pass a invalid MassToGTO to setMassToGTO function")

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetMassToGTOToInvalid(String massToGTO) {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name,variation, country,manufacturer);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bfr.setMassToGTO("..//~"));
        assertEquals("MassToGTO should contain numbers", exception.getMessage());

        bfr.setMassToGTO("600 to");
        assertEquals("600 to",bfr.getMassToGTO());
    }

    // cici edited
    @DisplayName("should throw exceptions when pass a null MassToOther to setMassToOther function")
    @Test

    public void shouldThrowExceptionWhenSetMassToOtherToNull() {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name,variation, country,manufacturer);

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> bfr.setMassToOther(null));
        assertEquals("MassToOther cannot be null or empty", exception.getMessage());
    }

    // cici edited
    @DisplayName("should throw exception when pass a empty MassToOther to setMassToOther function")

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetMassToOtherToEmpty(String massToOther) {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name,variation,country,manufacturer);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bfr.setMassToOther(massToOther));
        assertEquals("MassToOther cannot be null or empty", exception.getMessage());
    }

    // cici edited
    @DisplayName("should throw exception when pass a invalidMassToOther to setMassToOther function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetMassToOtherToInvalid(String massToOther) {
        String name = "BFR";
        String country = "USA";
        String variation ="ccc";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name,variation,country,manufacturer);


        bfr.setMassToOther("600 to");
        assertEquals("600 to",bfr.getMassToOther());

        bfr.setMassToOther("600.12");
        assertEquals("600.12",bfr.getMassToOther());
    }

}