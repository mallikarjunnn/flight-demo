package com.flightbooking.controller;

import com.flightbooking.model.Booking;
import com.flightbooking.model.Flight;
import com.flightbooking.service.BookingService;
import com.flightbooking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookingController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/book")
    public String bookForm(@RequestParam int flightId, Model model) {
        Flight flight = flightService.getFlight(flightId);
        if (flight == null) {
            model.addAttribute("error", "Flight not found.");
            return "message";
        }
        model.addAttribute("flight", flight);
        return "book";
    }

    @PostMapping("/book")
    public String book(@RequestParam int flightId,
                       @RequestParam String passengerName,
                       @RequestParam String email,
                       @RequestParam String phone,
                       @RequestParam int seats,
                       Model model) {
        try {
            Booking booking = bookingService.book(flightId, passengerName, email, phone, seats);
            model.addAttribute("booking", booking);
            return "booking-success";
        } catch (Exception e) {
            Flight flight = flightService.getFlight(flightId);
            model.addAttribute("flight", flight);
            model.addAttribute("error", e.getMessage());
            return "book";
        }
    }

    @GetMapping("/booking")
    public String lookupForm() {
        return "booking-details";
    }

    @PostMapping("/booking")
    public String lookup(@RequestParam String pnr, Model model) {
        Booking booking = bookingService.getByPnr(pnr);
        if (booking == null) {
            model.addAttribute("error", "No booking found for PNR " + pnr.toUpperCase());
        } else {
            model.addAttribute("booking", booking);
        }
        model.addAttribute("pnr", pnr);
        return "booking-details";
    }

    @PostMapping("/cancel")
    public String cancel(@RequestParam String pnr, RedirectAttributes redirect) {
        try {
            bookingService.cancel(pnr);
            redirect.addFlashAttribute("success",
                    "Ticket " + pnr.toUpperCase() + " cancelled. Seats were added back to the flight.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/booking";
    }
}
