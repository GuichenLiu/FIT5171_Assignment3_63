package rockets.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PayLoadUnitTest {

    private PayLoad target;

    @BeforeEach
    public void setUp() {
        String type = "Satellite";
        String name = "Sputnik I";
        String mass = "83.6 kg";
        String manufacturer = "Ministry of Radiotechnical Industry";
        target = new PayLoad(type,name,manufacturer);
    }

    @DisplayName("should return true when pass the valid parameters for constructor")
    @Test
    public void shouldReturnTrueWhenPassValidInputToConstructor() {
        String type = "Satellite";
        String name = "Vanguard 1";
        String mass = "1.47 kg";
        String manufacturer = "Naval Research Laboratory";
        target = new PayLoad(type,name,manufacturer);
        assertTrue(type.equals(target.getType()));
        assertTrue(name.equals(target.getName()));
        assertTrue(manufacturer.equals(target.getManufacturer()));
    }


    @DisplayName("should throw exception when pass the invalid parameters for constructor")
    @Test
    public void shouldThrowExceptionWhenPassInvalidInputToConstructor(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> new PayLoad("111","Vanguard 1", "Naval Research Laboratory"));
        assertEquals("Type and Manufacturer conctain only alphabetic, Name contain only alphanumeric, all are under 30 characters", exception.getMessage());

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, ()
                -> new PayLoad("Satellite","Vanguard 1!!!", "Naval Research Laboratory"));
        assertEquals("Type and Manufacturer conctain only alphabetic, Name contain only alphanumeric, all are under 30 characters", exception.getMessage());

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, ()
                -> new PayLoad("Satellite","Vanguard 1", "Naval Research Laboratory!!!"));
        assertEquals("Type and Manufacturer conctain only alphabetic, Name contain only alphanumeric, all are under 30 characters", exception.getMessage());
    }

    @DisplayName("should throw exception when pass empty weight and unit to setMass function")
    @Test
    public void shouldThrowExceptionWhenSetWeightAndUnitToNull() {

        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setMass(null,null));
        assertEquals("weight and unit cannot be null or empty", exception.getMessage());

        NullPointerException exception1 = assertThrows(NullPointerException.class, () -> target.setMass("1.2",null));
        assertEquals("weight and unit cannot be null or empty", exception.getMessage());

        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> target.setMass(null,"kg"));
        assertEquals("weight and unit cannot be null or empty", exception.getMessage());
    }



    @DisplayName("should throw exception when pass invalid weight and unit to setMass function")
    @Test
    public void shouldThrowExceptionWhenPassInvalidWeightAndUnitToSetMass(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setMass("aaa", "kg"));
        assertEquals("Weight must be Integer or Double, unit contain only characters", exception.getMessage());

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> target.setMass("1.2","!!!"));
        assertEquals("Weight must be Integer or Double, unit contain only characters", exception1.getMessage());

        target.setMass("1.2", "kg");
        assertEquals("1.2 kg",target.getMass());
    }

}
