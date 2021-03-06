package com.techelevator.dao;

import com.techelevator.model.Park;
import com.techelevator.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcSiteDao implements SiteDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcSiteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Site> getSitesThatAllowRVs(int parkId) {
        List<Site> sites = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM site " +
                "WHERE max_rv_length > 0;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()) {
            sites.add(mapRowToSite(results));
        }

        return sites;
    }
    @Override
    public List<Site> getCurrentlyAvailableSites(int parkId) {
        List<Site> sites = new ArrayList<>();
        String sql = "SELECT site_id, site.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities " +
                "FROM site " +
                "LEFT JOIN reservation USING (site_id) " +
                "JOIN campground ON site.campground_id = campground.campground_id " +
                "WHERE reservation_id IS NULL AND campground.park_id = ? " +
                "ORDER BY site_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
        while(results.next()) {
            Site site = mapRowToSite(results);
            sites.add(site);
        }
        return sites;
    }

    @Override
    public List<Site> getFutureAvailableSites(int parkId, LocalDate startDate, LocalDate endDate) {
        List<Site> sites = new ArrayList<>();

        String sql = "SELECT site_id, s.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities "
        + "FROM site s  LEFT JOIN reservation r USING (site_id) JOIN campground c USING (campground_id) "
        + "WHERE r.reservation_id IS NULL OR (r.from_date > ? AND r.to_date < ? AND c.park_id = ?) "
        + "ORDER BY site_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, endDate, startDate, parkId);
        while(results.next()) {
            Site site = mapRowToSite(results);
            sites.add(site);
        }

        return sites;
    }

    private Site mapRowToSite(SqlRowSet results) {
        Site site = new Site();
        site.setSiteId(results.getInt("site_id"));
        site.setCampgroundId(results.getInt("campground_id"));
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        site.setMaxRvLength(results.getInt("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }
}
