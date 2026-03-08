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
import java.util.Random;

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
        if (tableRepository.count()==0) {
            tableRepository.saveAll(List.of(
                    new Table("M1", 6, true, true, "MAIN", 20, 26),
                    new Table("M2", 4, false, true, "MAIN", 40, 26),
                    new Table("M3", 4, true, true, "MAIN", 20, 56),
                    new Table("M4", 2, false, false, "MAIN", 40, 56),
                    //terrace
                    new Table("T1", 2, false, false, "TERRACE", 35, 83),
                    new Table("T2", 4, false, false, "TERRACE", 70, 83),
                    //private
                    new Table("P1", 6, true, true, "PRIVATE", 68, 26),
                    new Table("P2", 8, true, true, "PRIVATE", 83, 26)
            ));
        }
        reservationRepository.deleteAll();
        generateRandomReservations();
    }

    private void generateRandomReservations() {
        List<Table> tables=tableRepository.findAll();
        String[] names= {"Andreas","Andres","Richard","Stefani","Jaagup","Margit", "Robert", "Bob"};
        Random random=new Random();
        for (Table table:tables){
            int r=3+random.nextInt(4);
            for (int i = 0; i < r; i++) {
                int days= random.nextInt(7);
                int hour=12+ random.nextInt(8);
                LocalDateTime start=LocalDateTime.now()
                        .plusDays(days)
                        .withHour(hour)
                        .withMinute(0);
                LocalDateTime end=start.plusHours(2);

                if (reservationRepository.findByTableAndEndAfterAndStartBefore(table,start,end).isEmpty()){
                    String name=names[random.nextInt(names.length)];
                    int partysize= table.getCapacity();
                    reservationRepository.save(new Reservation(name,partysize,table,start,end));
                }
            }
        }
    }


}
