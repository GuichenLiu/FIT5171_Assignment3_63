package rockets.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class PayLoad {

    //satellite, space probe, spacecraft
    private String type;
    //payload name
    private String name;
    //weight of payload
    private String mass;

    private LaunchServiceProvider manufacturer;

    public boolean onlyCharacter(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }

    public boolean onlyCharacterAndNumber(String input) {return input.matches("[a-zA-Z0-9\\s]+"); }

    public boolean isInRange(String input, int i, int j) {return input.length()>i && input.length()<j;}

    public PayLoad(String type, String name, LaunchServiceProvider manufacturer) {
        if(onlyCharacter(type)
                &&onlyCharacterAndNumber(name)
                //&&onlyCharacter(manufacturer)
                &&isInRange(type,0,30)
                &&isInRange(name,0,30)
                //&&isInRange(manufacturer,0,100)
        ){
            this.type = type;
            this.name = name;
            this.manufacturer = manufacturer;
        }
        else throw new IllegalArgumentException("Type and Manufacturer contain only alphabetic, Name contain only alphanumeric, all are under 30 characters");
    }

    public String getType(){return type;}

    public String getName(){return name;}

    public String getMass(){return mass; }

    public LaunchServiceProvider getManufacturer(){return manufacturer;}

    public boolean isDoubleOrInteger(String input) {

        if (input.matches(("(([0-9]*)\\.([0-9]*))")) || input.matches(("[0-9]+"))) return true;
        else return false;
    }


    public void setMass(String weight, String unit){
        notBlank(weight, "weight and unit cannot be null or empty");
        notBlank(unit, "weight and unit cannot be null or empty");

        if(isDoubleOrInteger(weight) && onlyCharacter(unit))
        {
            this.mass = weight + " " + unit;
        }
        else throw new IllegalArgumentException("Weight must be Integer or Double, unit contain only characters");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayLoad payload = (PayLoad) o;
        return Objects.equals(type, payload.type) &&
                Objects.equals(name, payload.name) &&
                Objects.equals(manufacturer, payload.manufacturer);

    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, manufacturer);
    }

    @Override
    public String toString() {
        return "PayLoad{" +
                "type='" + type + '\'' +
                "name='" + name + '\'' +
                "manufacturer='" + manufacturer + '\'' +
                '}';
    }

}
