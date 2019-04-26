package rockets.model;

import com.google.common.collect.Sets;

//import java.math.BigDecimal;
import java.math.*;
import java.util.Objects;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.Validate.*;
import static rockets.model.Launch.LaunchOutcome.SUCCESSFUL;

public class LaunchServiceProvider extends Entity {
    private String name;

    private int yearFounded;

    private String country;

    private String headquarters;

    private Set<Rocket> rockets;



    //cc new
    //private Set<Launch> launches;
    private BigDecimal totalRevenue;
    private int percentage;
    private int Dominant;

    public boolean onlyCharacter(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }

    public boolean isInteger(String input){
        return input.matches("[0-9]+");
    }

    public boolean isInRange(String input, int i) {return input.length()<i;}

    public boolean validYear(int year) {
        Date date = new Date();
        String strDateFormat = "yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        //System.out.println(sdf);
        if(year > 1900 && year<Integer.valueOf(sdf.format(date))) return true;
        else return false;
    }

    public LaunchServiceProvider(String name, int yearFounded, String country) {

        if(onlyCharacter(name)
                && onlyCharacter(country)
                && isInteger(String.valueOf(yearFounded))
                && validYear(yearFounded)
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



//    //new
//    public BigDecimal getTotalRevenue(int year) {
//        BigDecimal totalRevenue = new BigDecimal(0.00);
//        Set<Rocket> set = getRockets();
//        for (Rocket ro :set){
//            Set<Launch> set1 =ro.getLaunches();
//            for (Launch st : set1) {
//                if (st.getLaunchDate().getYear() == year) {
//                    totalRevenue.add(st.getPrice());
//                }
//            }
//        }
//        return totalRevenue;
//    }

    //nam fix
    // to find out the total revenue of a selected year
    public BigDecimal getTotalRevenueOfYear(int year) {
        BigDecimal totalRevenueOfYearXXXX = BigDecimal.valueOf(0.00);
        Set<Rocket> set = getRockets();
        for (Rocket ro :set){
            Set<Launch> set1 =ro.getLaunches();
            for (Launch st : set1) {
                if (st.getLaunchDate().getYear() == year) {
                    BigDecimal lCost = st.getPrice();
                    totalRevenueOfYearXXXX = totalRevenueOfYearXXXX.add(lCost);
                }
            }
        }
        return totalRevenueOfYearXXXX;
    }




    public int getPercentage() {
        int succ=0;
        int size =0;
        Set<Rocket> set = getRockets();
        for (Rocket ro :set)
        {
            Set<Launch> set1 = ro.getLaunches();
            size+=set1.size();
            for (Launch st : set1)
            {
                //size+=1;
                if (st.getLaunchOutcome() == SUCCESSFUL)
                {
                    succ += 1;
                }
            }
        }

        if (size>0)
        {
            percentage = succ / size;
        }
        //System.out.println(percentage);
        return percentage;
    }



    public int getDominant(String orbit) {
        Set<Rocket> set = getRockets();
        int Dominant =0;
        for (Rocket ro :set)
        {
            Set<Launch> set1 = ro.getLaunches();

            for (Launch st : set1) {
                //System.out.println(st.getOrbit());
                if (st.getOrbit() == orbit) {
                    Dominant += 1;
                }
            }
        }
        System.out.println(Dominant);
        return Dominant;
    }


}
