package org.andreas.reserveerimissusteem.repository;

import org.andreas.reserveerimissusteem.entity.Reservation;
import org.andreas.reserveerimissusteem.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    List<Reservation> findByTable(Table table);
}
