package com.flightbooking.service;

import com.flightbooking.dao.BookingDao;
import com.flightbooking.dao.FlightDao;
import com.flightbooking.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private BookingDao bookingDao;

    public List<Flight> getAllFlights() {
        return flightDao.findAll();
    }

    public Flight getFlight(int id) {
        return flightDao.findById(id);
    }

    public List<Flight> searchFlights(String source, String destination, Date date) {
        return flightDao.search(source.trim(), destination.trim(), date);
    }

    public void addFlight(Flight flight) {
        flight.setAvailableSeats(flight.getTotalSeats());
        flightDao.insert(flight);
    }

    @Transactional
    public boolean deleteFlight(int id) {
        bookingDao.deleteByFlightId(id);
        return flightDao.deleteById(id) > 0;
    }
}
