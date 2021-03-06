package rockets.mining;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RocketMiner {
    private static Logger logger = LoggerFactory.getLogger(RocketMiner.class);

    private DAO dao;

    public RocketMiner(DAO dao) {
        this.dao = dao;
    }

    /**
     * Returns the top-k most active rockets, as measured by number of completed launches.
     *
     * @param k the number of rockets to be returned.
     * @return the list of k most active rockets.
     */
    public List<Rocket> mostLaunchedRockets(int k) {
        logger.info("find most launched " + k + " rockets");
        Collection<Rocket> rockets = dao.loadAll(Rocket.class);
        Comparator<Rocket> mostLaunchedComparator = (a, b) -> -Integer.compare(a.getLaunches().size(), b.getLaunches().size());
        return rockets.stream().sorted(mostLaunchedComparator).limit(k).collect(Collectors.toList());

    }

    /**
     * <p>
     * Returns the top-k most reliable launch service providers as measured
     * by percentage of successful launches.
     *
     * @param k the number of launch service providers to be returned.
     * @return the list of k most reliable ones.
     */
    public List<LaunchServiceProvider> mostReliableLaunchServiceProviders(int k) {

        Collection<LaunchServiceProvider> launches = dao.loadAll(LaunchServiceProvider.class);
        Comparator<LaunchServiceProvider> ReliableComparator = (a, b) -> -Integer.compare(a.getPercentage(),b.getPercentage());
        return launches.stream().sorted(ReliableComparator).limit(k).collect(Collectors.toList());

    }

    /**
     * <p>
     * Returns the top-k most recent launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most recent launches.
     */
    public List<Launch> mostRecentLaunches(int k) {
        logger.info("find most recent " + k + " launches");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Comparator<Launch> mostRecentComparator = (a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate());
        return launches.stream().sorted(mostRecentComparator).limit(k).collect(Collectors.toList());

    }

    /**
     * <p>
     * Returns the dominant country who has the most launched rockets in an orbit.
     *
     * @param orbit the orbit
     * @return the country who sends the most payload to the orbit
     */
    public String dominantCountry(String orbit) {
        Collection<LaunchServiceProvider> launches = dao.loadAll(LaunchServiceProvider.class);
        Comparator<LaunchServiceProvider> ReliableComparator = (a, b) -> -Integer.compare(a.getDominant(orbit), b.getDominant(orbit));
        return launches.stream().sorted(ReliableComparator).limit(1).collect(Collectors.toList()).get(0).getName();

    }

    /**
     * <p>
     * Returns the top-k most expensive launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most expensive launches.
     */
    public List<Launch> mostExpensiveLaunches(int k) {
        logger.info("find most Expensive " + k + " launches");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Comparator<Launch> launchExpensiveComparator = (a, b) -> -a.getPrice().compareTo(b.getPrice());
        return launches.stream().sorted(launchExpensiveComparator).limit(k).collect(Collectors.toList());
    }

    /**
     * <p>
     * Returns a list of launch service provider that has the top-k highest
     * sales revenue in a year.
     *
     * @param k the number of launch service provider.
     * @param year the year in request
     * @return the list of k launch service providers who has the highest sales revenue.
     */
    public List<LaunchServiceProvider> highestRevenueLaunchServiceProviders(int k, int year) {
        logger.info("find most Expensive " + k + " launches");
        Collection<LaunchServiceProvider> launches = dao.loadAll(LaunchServiceProvider.class);
        Comparator<LaunchServiceProvider> launchExpensiveComparator = (a, b) -> -a.getTotalRevenue(year).compareTo(b.getTotalRevenue(year));
        return launches.stream().sorted(launchExpensiveComparator).limit(k).collect(Collectors.toList());

    }
}
