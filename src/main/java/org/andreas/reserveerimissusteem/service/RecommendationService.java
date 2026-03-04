package org.andreas.reserveerimissusteem.service;

import org.andreas.reserveerimissusteem.entity.Table;
import org.andreas.reserveerimissusteem.repository.ReservationRepository;
import org.andreas.reserveerimissusteem.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {
    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public RecommendationService(TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Table> recommendTables(int numberOfPeople, String zone, boolean window, boolean quiet, LocalDateTime start, int durationHours){
        LocalDateTime end=start.plusHours(durationHours);
        List<Table> allTables=tableRepository.findAll();

        List<Table> availableTables=new ArrayList<>();
        for (Table table:allTables){
            if (isAvailable(table,start,end)&&table.getCapacity()>=numberOfPeople){
                availableTables.add(table);
            }
        }

        for (int i = 0; i < availableTables.size(); i++) {
            for (int j = i+1; j < availableTables.size(); j++) {
                int Iscore=score(availableTables.get(i),numberOfPeople,zone,window,quiet);
                int Jscore=score(availableTables.get(j),numberOfPeople,zone,window,quiet);
                if (Iscore<Jscore){
                    Table temp= availableTables.get(i);
                    availableTables.set(i,availableTables.get(j));
                    availableTables.set(j,temp);
                }
            }
        }
        return availableTables;
    }

    private boolean isAvailable(Table table, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.findByTableAndEndAfterAndStartBefore(table,start,end).isEmpty();
    }

    private int score(Table table, int numberOfPeople, String zone, boolean window, boolean quiet){
        int score=0;

        if (table.isWindow()==window) score+=20;
        if (table.isQuiet()==quiet) score+=20;
        if (zone!=null&&zone.equals(table.getZone())) score+=20;

        int wastedSeats=table.getCapacity()-numberOfPeople;
        if (wastedSeats>4)wastedSeats=4; // dont penalice too much,
        score=score-wastedSeats*5;
        if (score<0) score=0;
        return score;
    }
}
