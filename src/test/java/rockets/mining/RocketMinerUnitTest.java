package rockets.mining;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RocketMinerUnitTest {
    Logger logger = LoggerFactory.getLogger(RocketMinerUnitTest.class);

    private DAO dao;
    private RocketMiner miner;
    private List<Rocket> rockets;
    private List<LaunchServiceProvider> lsps;
    private List<Launch> launches;

    @BeforeEach
    public void setUp() {
        dao = mock(Neo4jDAO.class);
        miner = new RocketMiner(dao);
        rockets = Lists.newArrayList();

        lsps = Arrays.asList(
                new LaunchServiceProvider("ULA", 1990, "USA"),
                new LaunchServiceProvider("SpaceX", 2002, "USA"),
                new LaunchServiceProvider("ESA", 1975, "Europe ")
        );


        // index of lsp of each rocket
        int[] lspIndex = new int[]{0, 0, 0, 1, 1};
        // 5 rockets
        for (int i = 0; i < 5; i++) {
            rockets.add(new Rocket("rocket_" + i, "Block 5","USA",lsps.get(lspIndex[i])));
        }
        // month of each launch
        int[] months = new int[]{1, 6, 4, 3, 4, 11, 6, 5, 12, 5};

        // index of rocket of each launch
        int[] rocketIndex = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 3};

        // 10 launches
        launches = IntStream.range(0, 10).mapToObj(i -> {
            logger.info("create " + i + " launch in month: " + months[i]);
            Launch l = new Launch();
            l.setLaunchDate(LocalDate.of(2017, months[i], 1));

            //cici edit
            l.setPrice(new BigDecimal("2.005"));

            l.setLaunchVehicle(rockets.get(rocketIndex[i]));
            l.setLaunchSite("VAFB");
            l.setOrbit("LEO");
            spy(l);
            return l;
        }).collect(Collectors.toList());

        //cc edit
        Set set1 = new HashSet();
        set1.add(launches.get(1));

        Set set2 = new HashSet();
        set2.add(launches.get(2));

        Set set3 = new HashSet();
        set3.add(launches.get(3));

        Set set4 = new HashSet();
        set4.add(launches.get(4));

        Set set5 = new HashSet();
        set5.add(launches.get(5));

        rockets.get(0).setLaunches(set1);
        rockets.get(1).setLaunches(set2);
        rockets.get(2).setLaunches(set3);
        rockets.get(3).setLaunches(set4);
        rockets.get(4).setLaunches(set5);


        Set set6 = new HashSet();
        set6.add(rockets.get(0));
        set6.add(rockets.get(1));
        set6.add(rockets.get(2));

        Set set7 = new HashSet();
        set7.add(rockets.get(3));
        set7.add(rockets.get(4));



        lsps.get(0).setRockets(set6);
        lsps.get(1).setRockets(set7);
        //lsps.get(2).setRockets(set8);

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostLaunches(int k) {
        when(dao.loadAll(Rocket.class)).thenReturn(rockets);
        List<Rocket> sortedLaunches = new ArrayList<>(rockets);
        sortedLaunches.sort((a, b) -> -Integer.compare(a.getLaunches().size(), b.getLaunches().size()));
        List<Rocket> loadedLaunches = miner.mostLaunchedRockets(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostRecentLaunches(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate()));
        List<Launch> loadedLaunches = miner.mostRecentLaunches(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }


//    @ParameterizedTest
//    @ValueSource(ints = {1, 2, 3})
//    public void shouldReturnTopMostExpensiveLaunches(int k) {
//        when(dao.loadAll(Launch.class)).thenReturn(launches);
//        List<Launch> sortedLaunches = new ArrayList<>(launches);
//        sortedLaunches.sort((a, b) -> -a.getPrice().compareTo(b.getPrice()));
//        List<Launch> loadedLaunches = miner.mostExpensiveLaunches(k);
//        assertEquals(k, loadedLaunches.size());
//        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
//    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void shouldReturnTopMostReliableLaunchProvider(int k) {
        when(dao.loadAll(LaunchServiceProvider.class)).thenReturn(lsps);
        List<LaunchServiceProvider> sortedLaunches = new ArrayList<>(lsps);
        sortedLaunches.sort((a, b) -> -Integer.compare(a.getPercentage(),b.getPercentage()));
        List<LaunchServiceProvider> loadedLaunches = miner.mostReliableLaunchServiceProviders(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }

    @Test
    public void shouldReturnDominantCountryOnAOrbit() {
        String orbit="LEO";
        when(dao.loadAll(LaunchServiceProvider.class)).thenReturn(lsps);
        List<LaunchServiceProvider> sortedLaunches = new ArrayList<>(lsps);
        sortedLaunches.sort((a, b) -> -Integer.compare(a.getDominant(orbit), b.getDominant(orbit)));
        String dominate = miner.dominantCountry(orbit);
        //System.out.println(dominate);
        assertEquals(sortedLaunches.get(0).getName(), dominate);
    }


//    @ParameterizedTest
//    @ValueSource(ints = {1,2,3})
//
//    public void shouldReturnHighestRevenueLaunchServiceProviders(int k) {
//
//        int year= 2017;
//        when(dao.loadAll(LaunchServiceProvider.class)).thenReturn(lsps);
//        List<LaunchServiceProvider> sortedLaunches = new ArrayList<>(lsps);
//        sortedLaunches.sort((a, b) -> -a.getTotalRevenueOfYear(2017).compareTo(b.getTotalRevenueOfYear(2017)));
//        List<LaunchServiceProvider> loadedLaunches = miner.highestRevenueLaunchServiceProviders(k,year);
//        assertEquals(k, loadedLaunches.size());
//        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
//    }
}