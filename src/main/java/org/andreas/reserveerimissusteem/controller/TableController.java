package org.andreas.reserveerimissusteem.controller;

import org.andreas.reserveerimissusteem.entity.Table;
import org.andreas.reserveerimissusteem.repository.TableRepository;

import org.andreas.reserveerimissusteem.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/table")
public class TableController {
    private final TableRepository tableRepository;
    private final RecommendationService recommendationService;

    public TableController(TableRepository repository, RecommendationService recommendationService) {
        this.tableRepository = repository;
        this.recommendationService = recommendationService;
    }

    @GetMapping("")
    public List<Table> getAllTables(){
        return tableRepository.findAll();
    }

    @GetMapping("/{id}")
    public Table getTable(@PathVariable Integer id) {
        return tableRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found")
        );
    }

    @GetMapping("/recommend")
    public List<Table> recommend(@RequestParam int numberOfPeople,
                                 @RequestParam(required = false) String zone,
                                 @RequestParam(required = false) boolean window,
                                 @RequestParam(required = false) boolean quiet,
                                 @RequestParam String dateTime,
                                 @RequestParam(defaultValue = "2") int durationHours){
        LocalDateTime start= LocalDateTime.parse(dateTime);
        return recommendationService.recommendTables(numberOfPeople,zone,window,quiet,start,durationHours);

    }


}

