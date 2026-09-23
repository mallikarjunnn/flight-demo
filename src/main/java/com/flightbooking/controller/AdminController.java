package com.flightbooking.controller;

import com.flightbooking.model.Admin;
import com.flightbooking.model.Flight;
import com.flightbooking.service.AdminService;
import com.flightbooking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private FlightService flightService;

    @GetMapping("/admin/login")
    public String loginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Admin admin = adminService.login(username, password);
        if (admin == null) {
            model.addAttribute("error", "Invalid username or password.");
            return "admin-login";
        }
        session.setAttribute("admin", admin.getUsername());
        return "redirect:/admin";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/admin")
    public String dashboard(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("flights", flightService.getAllFlights());
        return "admin-dashboard";
    }

    @PostMapping("/admin/flights/add")
    public String addFlight(@RequestParam String flightNumber,
                            @RequestParam String airline,
                            @RequestParam String source,
                            @RequestParam String destination,
                            @RequestParam String departureDate,
                            @RequestParam String departureTime,
                            @RequestParam String arrivalTime,
                            @RequestParam BigDecimal price,
                            @RequestParam int totalSeats,
                            HttpSession session,
                            RedirectAttributes redirect) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        try {
            Flight flight = new Flight();
            flight.setFlightNumber(flightNumber.trim());
            flight.setAirline(airline.trim());
            flight.setSource(source.trim());
            flight.setDestination(destination.trim());
            flight.setDepartureDate(Date.valueOf(departureDate));
            flight.setDepartureTime(Time.valueOf(normalizeTime(departureTime)));
            flight.setArrivalTime(Time.valueOf(normalizeTime(arrivalTime)));
            flight.setPrice(price);
            flight.setTotalSeats(totalSeats);
            flightService.addFlight(flight);
            redirect.addFlashAttribute("success", "Flight " + flightNumber + " added.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Could not add flight: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/flights/delete")
    public String deleteFlight(@RequestParam int id, HttpSession session, RedirectAttributes redirect) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (flightService.deleteFlight(id)) {
            redirect.addFlashAttribute("success", "Flight deleted.");
        } else {
            redirect.addFlashAttribute("error", "Flight not found.");
        }
        return "redirect:/admin";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("admin") != null;
    }

    /** HTML time input is HH:mm; MySQL Time.valueOf needs HH:mm:ss */
    private String normalizeTime(String time) {
        if (time != null && time.length() == 5) {
            return time + ":00";
        }
        return time;
    }
}
