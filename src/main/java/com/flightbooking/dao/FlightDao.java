package com.flightbooking.dao;

import com.flightbooking.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class FlightDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Flight> flightMapper = (rs, rowNum) -> {
        Flight f = new Flight();
        f.setId(rs.getInt("id"));
        f.setFlightNumber(rs.getString("flight_number"));
        f.setAirline(rs.getString("airline"));
        f.setSource(rs.getString("source"));
        f.setDestination(rs.getString("destination"));
        f.setDepartureDate(rs.getDate("departure_date"));
        f.setDepartureTime(rs.getTime("departure_time"));
        f.setArrivalTime(rs.getTime("arrival_time"));
        f.setPrice(rs.getBigDecimal("price"));
        f.setTotalSeats(rs.getInt("total_seats"));
        f.setAvailableSeats(rs.getInt("available_seats"));
        return f;
    };

    public List<Flight> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM flights ORDER BY departure_date, departure_time",
                flightMapper);
    }

    public Flight findById(int id) {
        List<Flight> list = jdbcTemplate.query(
                "SELECT * FROM flights WHERE id = ?",
                flightMapper,
                id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Flight> search(String source, String destination, Date date) {
        if (date != null) {
            return jdbcTemplate.query(
                    "SELECT * FROM flights WHERE LOWER(source) = LOWER(?) "
                            + "AND LOWER(destination) = LOWER(?) AND departure_date = ? "
                            + "ORDER BY departure_time",
                    flightMapper,
                    source, destination, date);
        }
        return jdbcTemplate.query(
                "SELECT * FROM flights WHERE LOWER(source) = LOWER(?) "
                        + "AND LOWER(destination) = LOWER(?) ORDER BY departure_date, departure_time",
                flightMapper,
                source, destination);
    }

    public int insert(Flight flight) {
        return jdbcTemplate.update(
                "INSERT INTO flights (flight_number, airline, source, destination, departure_date, "
                        + "departure_time, arrival_time, price, total_seats, available_seats) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                flight.getFlightNumber(),
                flight.getAirline(),
                flight.getSource(),
                flight.getDestination(),
                flight.getDepartureDate(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getPrice(),
                flight.getTotalSeats(),
                flight.getAvailableSeats());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM flights WHERE id = ?", id);
    }

    public int decrementSeats(int flightId, int seats) {
        return jdbcTemplate.update(
                "UPDATE flights SET available_seats = available_seats - ? "
                        + "WHERE id = ? AND available_seats >= ?",
                seats, flightId, seats);
    }

    public int incrementSeats(int flightId, int seats) {
        return jdbcTemplate.update(
                "UPDATE flights SET available_seats = available_seats + ? WHERE id = ?",
                seats, flightId);
    }
}
