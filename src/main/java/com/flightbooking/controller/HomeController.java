package com.flightbooking.controller;

import com.flightbooking.model.Flight;
import com.flightbooking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String source,
                         @RequestParam String destination,
                         @RequestParam(required = false) String date,
                         Model model) {
        Date sqlDate = null;
        if (date != null && !date.trim().isEmpty()) {
            sqlDate = Date.valueOf(date);
        }
        List<Flight> flights = flightService.searchFlights(source, destination, sqlDate);
        model.addAttribute("flights", flights);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("date", date);
        return "search-results";
    }
}
