package com.techelevator.dao;

import com.techelevator.model.Reservation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcReservationDaoTests extends BaseDaoTests {

    private ReservationDao dao;

    @Before
    public void setup() {
        dao = new JdbcReservationDao(dataSource);
    }

    @Test
    public void createReservation_Should_ReturnNewReservationId() {
        int reservationCreated = dao.createReservation(9999,
                "TEST NAME",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3));

        assertEquals(reservationCreated, 1);
    }

    @Test
    public void getAllUpcomingReservations_returns_reservations_in_next_30_days() {
        List<Reservation> expected = new ArrayList<>();
        List<Reservation> actual = dao.getAllUpcomingReservations();

        Reservation r1 = new Reservation();
        r1.setReservationId(1237);
        r1.setSiteId(9999);
        r1.setName("Test Testerson");
        r1.setFromDate(LocalDate.now().plusDays(1));
        r1.setToDate(LocalDate.now().plusDays(5));
        r1.setCreateDate(LocalDate.now().minusDays(23));
        expected.add(r1);

        Reservation r2 = new Reservation();
        r2.setReservationId(1236);
        r2.setSiteId(9999);
        r2.setName("Bob Robertson");
        r2.setFromDate(LocalDate.now().plusDays(11));
        r2.setToDate(LocalDate.now().plusDays(18));
        r2.setCreateDate(LocalDate.now().minusDays(23));
        expected.add(r2);

        Assert.assertEquals(expected.size(), actual.size());
        assertReservationsMatch(expected.get(0), actual.get(0));
        assertReservationsMatch(expected.get(1), actual.get(1));
    }

    private void assertReservationsMatch(Reservation expected, Reservation actual) {
        Assert.assertEquals(expected.getReservationId(), actual.getReservationId());
        Assert.assertEquals(expected.getSiteId(), actual.getSiteId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getFromDate(), actual.getFromDate());
        Assert.assertEquals(expected.getToDate(), actual.getToDate());
        Assert.assertEquals(expected.getCreateDate(), actual.getCreateDate());
    }

}
