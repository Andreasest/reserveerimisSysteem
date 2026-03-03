package org.andreas.reserveerimissusteem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    @GetMapping("/api/reservation")
    public String index(){
        return "Hello reservation world!";
    }
}
