package com.flightbooking.dao;

import com.flightbooking.model.Booking;
import com.flightbooking.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Booking> bookingMapper = (rs, rowNum) -> {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));
        b.setPnr(rs.getString("pnr"));
        b.setFlightId(rs.getInt("flight_id"));
        b.setPassengerName(rs.getString("passenger_name"));
        b.setEmail(rs.getString("email"));
        b.setPhone(rs.getString("phone"));
        b.setSeatsBooked(rs.getInt("seats_booked"));
        b.setStatus(rs.getString("status"));
        b.setBookedAt(rs.getTimestamp("booked_at"));

        Flight f = new Flight();
        f.setId(rs.getInt("flight_id"));
        f.setFlightNumber(rs.getString("flight_number"));
        f.setAirline(rs.getString("airline"));
        f.setSource(rs.getString("source"));
        f.setDestination(rs.getString("destination"));
        f.setDepartureDate(rs.getDate("departure_date"));
        f.setDepartureTime(rs.getTime("departure_time"));
        f.setArrivalTime(rs.getTime("arrival_time"));
        f.setPrice(rs.getBigDecimal("price"));
        b.setFlight(f);
        return b;
    };

    public int insert(Booking booking) {
        return jdbcTemplate.update(
                "INSERT INTO bookings (pnr, flight_id, passenger_name, email, phone, seats_booked, status) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                booking.getPnr(),
                booking.getFlightId(),
                booking.getPassengerName(),
                booking.getEmail(),
                booking.getPhone(),
                booking.getSeatsBooked(),
                booking.getStatus());
    }

    public Booking findByPnr(String pnr) {
        List<Booking> list = jdbcTemplate.query(
                "SELECT b.*, f.flight_number, f.airline, f.source, f.destination, "
                        + "f.departure_date, f.departure_time, f.arrival_time, f.price "
                        + "FROM bookings b JOIN flights f ON b.flight_id = f.id WHERE b.pnr = ?",
                bookingMapper,
                pnr);
        return list.isEmpty() ? null : list.get(0);
    }

    public int cancel(String pnr) {
        return jdbcTemplate.update(
                "UPDATE bookings SET status = 'CANCELLED' WHERE pnr = ? AND status = 'CONFIRMED'",
                pnr);
    }

    public int deleteByFlightId(int flightId) {
        return jdbcTemplate.update("DELETE FROM bookings WHERE flight_id = ?", flightId);
    }
}
