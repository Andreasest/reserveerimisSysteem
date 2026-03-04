package org.andreas.reserveerimissusteem.controller;

import org.andreas.reserveerimissusteem.entity.Table;
import org.andreas.reserveerimissusteem.repository.TableRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/table")
public class TableController {
    private final TableRepository tableRepository;

    public TableController(TableRepository repository) {
        this.tableRepository = repository;
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


}

