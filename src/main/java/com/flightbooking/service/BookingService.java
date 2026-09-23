package com.flightbooking.service;

import com.flightbooking.dao.BookingDao;
import com.flightbooking.dao.FlightDao;
import com.flightbooking.model.Booking;
import com.flightbooking.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private FlightDao flightDao;

    @Transactional
    public Booking book(int flightId, String name, String email, String phone, int seats) {
        Flight flight = flightDao.findById(flightId);
        if (flight == null) {
            throw new IllegalArgumentException("Flight not found.");
        }
        if (seats < 1) {
            throw new IllegalArgumentException("Book at least 1 seat.");
        }
        int updated = flightDao.decrementSeats(flightId, seats);
        if (updated == 0) {
            throw new IllegalStateException("Not enough seats. Available: " + flight.getAvailableSeats());
        }

        Booking booking = new Booking();
        booking.setPnr(generatePnr());
        booking.setFlightId(flightId);
        booking.setPassengerName(name.trim());
        booking.setEmail(email.trim());
        booking.setPhone(phone.trim());
        booking.setSeatsBooked(seats);
        booking.setStatus("CONFIRMED");
        bookingDao.insert(booking);
        return bookingDao.findByPnr(booking.getPnr());
    }

    public Booking getByPnr(String pnr) {
        return bookingDao.findByPnr(pnr.trim().toUpperCase());
    }

    @Transactional
    public void cancel(String pnr) {
        Booking booking = bookingDao.findByPnr(pnr.trim().toUpperCase());
        if (booking == null) {
            throw new IllegalArgumentException("No booking found for PNR: " + pnr);
        }
        if (!"CONFIRMED".equals(booking.getStatus())) {
            throw new IllegalStateException("This ticket is already cancelled.");
        }
        int cancelled = bookingDao.cancel(booking.getPnr());
        if (cancelled == 0) {
            throw new IllegalStateException("Could not cancel this ticket.");
        }
        flightDao.incrementSeats(booking.getFlightId(), booking.getSeatsBooked());
    }

    private String generatePnr() {
        return "FB" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}
