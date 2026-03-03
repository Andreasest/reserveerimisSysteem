package org.andreas.reserveerimissusteem.controller;

import org.andreas.reserveerimissusteem.entity.Table;
import org.andreas.reserveerimissusteem.service.TableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/table")
public class TableController {
    TableService service;

    @GetMapping("")
    public List<Table> getAllTables(){
        return service.getAllTables();
    }

    @GetMapping("/{id}")
    Table getTable(@PathVariable Integer id){
        return service.getTable(id);
    }


}

