package org.andreas.reserveerimissusteem;

import org.andreas.reserveerimissusteem.entity.Reservation;
import org.andreas.reserveerimissusteem.entity.Table;
import org.andreas.reserveerimissusteem.repository.ReservationRepository;
import org.andreas.reserveerimissusteem.repository.TableRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public DataLoader(TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (tableRepository.count()<0) {
            tableRepository.saveAll(List.of(
                    new Table("M1", 4, false, false, "MAIN", 1, 1),
                    new Table("M2", 4, false, true, "MAIN", 1, 1),
                    new Table("M3", 4, true, false, "MAIN", 1, 1),
                    new Table("M4", 2, true, true, "MAIN", 3, 2),
                    //terrace
                    new Table("T1", 2, false, false, "TERRACE", 1, 1),
                    new Table("T2", 4, false, false, "TERRACE", 2, 1),
                    //private
                    new Table("P1", 6, true, true, "PRIVATE", 1, 1),
                    new Table("P2", 8, true, true, "PRIVATE", 2, 1)
            ));
        }
        reservationRepository.deleteAll();
        Table m1=tableRepository.findByName("M1");
        reservationRepository.save(new Reservation(
                "John",
                2,
                m1,
                LocalDateTime.of(2026, 3, 10, 18, 0),
                LocalDateTime.of(2026, 3, 10, 20, 0)));
    }


}
