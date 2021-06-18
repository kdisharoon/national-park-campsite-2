package com.techelevator.dao;

import com.techelevator.model.Site;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface SiteDao {

    List<Site> getSitesThatAllowRVs(int parkId);
    List<Site> getCurrentlyAvailableSites(int parkId);
    List<Site> getFutureAvailableSites(int parkId, LocalDate startDate, LocalDate endDate);
}
