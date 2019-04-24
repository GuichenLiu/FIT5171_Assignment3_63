package rockets.model;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.Validate.*;

public class LaunchServiceProvider extends Entity {
    private String name;

    private int yearFounded;

    private String country;

    private String headquarters;

    private Set<Rocket> rockets;

    public boolean onlyCharacter(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }

    public boolean isInteger(String input){
        return input.matches("[0-9]+");
    }

    public boolean isInRange(String input, int i) {return input.length()<i;}

    public boolean notFutureYear(int year) {
        Date date = new Date();
        String strDateFormat = "yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        //System.out.println(sdf);
        if(year<Integer.valueOf(sdf.format(date))) return true;
        else return false;
    }

    public LaunchServiceProvider(String name, int yearFounded, String country) {

        if(onlyCharacter(name)
                && onlyCharacter(country)
                && isInteger(String.valueOf(yearFounded))
                && notFutureYear(yearFounded)
                && isInRange(name,30)
                && isInRange(country, 30)){
            this.name = name;
            this.yearFounded = yearFounded;
            this.country = country;

            rockets = Sets.newLinkedHashSet();
        }
        else {throw new IllegalArgumentException("Year must be an integer, name and country must only contain character, less than 30 in length");}

    }

    public String getName() {
        return name;
    }

    public int getYearFounded() {
        return yearFounded;
    }

    public String getCountry() {
        return country;
    }

    public String getHeadquarters() {
        return headquarters;
    }


    public Set<Rocket> getRockets() { return rockets; }


    public void setHeadquarters(String headquarters) {

        //ADDED
        notBlank(headquarters, "headquarter cannot be null or empty" );
        if (isInRange(headquarters, 30)) {
            this.headquarters = headquarters;
        }
        else throw new IllegalArgumentException("headquarter must be under 30 characters");

    }

    public void setRockets(Set<Rocket> rockets) {
        notNull(rockets, "rockets cannot be null or empty" );
        this.rockets = rockets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaunchServiceProvider that = (LaunchServiceProvider) o;
        return yearFounded == that.yearFounded &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, yearFounded, country);
    }
}
