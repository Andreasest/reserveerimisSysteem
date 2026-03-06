package org.andreas.reserveerimissusteem.controller;

import org.andreas.reserveerimissusteem.entity.Meal;
import org.andreas.reserveerimissusteem.service.DailyMealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/daily-meal")
public class DailyMealController {
    private final DailyMealService dailyMealService;

    public DailyMealController(DailyMealService dailyMealService) {
        this.dailyMealService = dailyMealService;
    }

    @GetMapping("")
    public Meal getDailyMeal(){
        return dailyMealService.getDaily();
    }
}
