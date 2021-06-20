package com.techelevator.dao;

import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcSiteDaoTests extends BaseDaoTests {

    private SiteDao dao;

    @Before
    public void setup() {
        dao = new JdbcSiteDao(dataSource);
    }

    @Test
    public void getSitesThatAllowRVs_Should_ReturnSites() {
        List<Site> sites = dao.getSitesThatAllowRVs(99);

        assertEquals(2,sites.size());
    }
    @Test
    public void getAvailableSites_Should_ReturnSites() {

        //Arrange
        List<Site> expected = new ArrayList<>();
        Site s1 = new Site();
        Site s2 = new Site();
        createTestSites(s1, s2);
        expected.add(s1);
        expected.add(s2);

        //Act
        List<Site> actual = dao.getCurrentlyAvailableSites(99);

        //Assert
        Assert.assertEquals(expected.size(), actual.size());
        assertSitesMatch(expected.get(0), actual.get(0));
        assertSitesMatch(expected.get(1), actual.get(1));

    }

    @Test
    public void getAvailableSitesDateRange_Should_ReturnSites() {

        //Arrange
        List<Site> expected = new ArrayList<>();
        Site s1 = new Site();
        Site s2 = new Site();
        createTestSites(s1, s2);
        expected.add(s1);
        expected.add(s2);

        //Act
        List<Site> actual = dao.getFutureAvailableSites(99, LocalDate.now().plusDays(3), LocalDate.now().plusDays(5));

        //Assert
        Assert.assertEquals(expected.size(), actual.size());
        assertSitesMatch(expected.get(0), actual.get(0));
        assertSitesMatch(expected.get(1), actual.get(1));
    }

    private void assertSitesMatch(Site expected, Site actual){
        Assert.assertEquals(expected.getSiteId(), actual.getSiteId());
        Assert.assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
        Assert.assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
        Assert.assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
        Assert.assertEquals(expected.isAccessible(), actual.isAccessible());
        Assert.assertEquals(expected.getMaxRvLength(), actual.getMaxRvLength());
        Assert.assertEquals(expected.isUtilities(), actual.isUtilities());
    }

    private void createTestSites(Site s1, Site s2) {
        s1.setSiteId(9997);
        s1.setCampgroundId(999);
        s1.setSiteNumber(1);
        s1.setMaxOccupancy(10);
        s1.setAccessible(true);
        s1.setMaxRvLength(33);
        s1.setUtilities(true);

        s2.setSiteId(9998);
        s2.setCampgroundId(999);
        s2.setSiteNumber(2);
        s2.setMaxOccupancy(10);
        s2.setAccessible(true);
        s2.setMaxRvLength(30);
        s2.setUtilities(true);
    }
}
