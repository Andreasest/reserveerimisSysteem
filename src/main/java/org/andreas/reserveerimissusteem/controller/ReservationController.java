package org.andreas.reserveerimissusteem.controller;

import org.andreas.reserveerimissusteem.entity.Reservation;
import org.andreas.reserveerimissusteem.entity.Table;
import org.andreas.reserveerimissusteem.repository.ReservationRepository;
import org.andreas.reserveerimissusteem.repository.TableRepository;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    public ReservationController(ReservationRepository reservationRepository, TableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
    }

    @GetMapping("")
    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    @PostMapping("")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation){
        Table table = tableRepository.findById(reservation.getTable().getId()).orElseThrow();

        LocalDateTime start=reservation.getStart();
        LocalDateTime end=reservation.getEnd();
        boolean isOccupied=!reservationRepository.findByTableAndEndAfterAndStartBefore(table,start,end).isEmpty();
        if (isOccupied){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Table already reserved");
        }

        reservation.setTable(table);
        return ResponseEntity.ok(reservationRepository.save(reservation));
    }
    @GetMapping("/available")
    public List<Integer> getAvailableTableIds(@RequestParam String dateTime, @RequestParam(defaultValue = "2") int durationHours) {
        LocalDateTime start = LocalDateTime.parse(dateTime);
        LocalDateTime end = start.plusHours(durationHours);
        List<Table> allTables = tableRepository.findAll();

        List<Integer> availableIds = new ArrayList<>();
        for (Table table : allTables) {
            if (reservationRepository.findByTableAndEndAfterAndStartBefore(table, start, end).isEmpty()) {
                availableIds.add(table.getId());
            }
        }
        return availableIds;
    }
}
