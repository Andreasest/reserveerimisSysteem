package org.andreas.reserveerimissusteem.service;

import org.andreas.reserveerimissusteem.entity.Table;

import java.util.List;

public class TableService {
    List<Table> tables;

    public List<Table> getAllTables() {
        return tables;
    }

    public Table getTable(Integer id) {
        for (Table table:tables){
            if (table.id == id) return table;
        }
        return null;
    }
}
