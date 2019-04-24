package rockets.model;

import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static rockets.model.Launch.LaunchOutcome.FAILED;
import static rockets.model.Launch.LaunchOutcome.SUCCESSFUL;

public class LaunchServiceProvider extends Entity {
    private String name;

    private int yearFounded;

    private String country;

    private String headquarters;

    private Set<Rocket> rockets;

    private Set<Launch> launchs;

    //cc new
    private BigDecimal totalRevenue;
   private int percentage;
   private int Dominant;

    public LaunchServiceProvider(String name, int yearFounded, String country) {
        this.name = name;
        this.yearFounded = yearFounded;
        this.country = country;

        rockets = Sets.newLinkedHashSet();// new
    }

    //new
    public BigDecimal getTotalRevenue(int year) {
        BigDecimal totalRevenue = new BigDecimal(0.00);
        Set<Launch> set = getLaunchs();
        for (Launch st : set) {
            if (st.getLaunchDate().getYear()==year)
            {
                totalRevenue.add(st.getPrice());
            }
        }
        return totalRevenue;
    }

    public int getPercentage() {
        Set<Launch> set = getLaunchs();
        int succ=0;
        for (Launch st : set) {
            if (st.getLaunchOutcome()==SUCCESSFUL)
            {
                succ+=1;
            }
        }
        percentage = succ/getLaunchs().size();
        return percentage;
    }

    public int getDominant(String orbit) {
        Set<Launch> set = getLaunchs();
        int Dominant =0;
        for (Launch st : set) {
            if (st.getOrbit()==orbit)
            {
                Dominant+=1;
            }
        }

        return Dominant;
    }

    public Set<Launch> getLaunchs() {
        return launchs;
    }

    public void setLaunchs(Set<Launch> launchs) {
        this.launchs = launchs;
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

    public Set<Rocket> getRockets() {
        return rockets;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public void setRockets(Set<Rocket> rockets) {
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
