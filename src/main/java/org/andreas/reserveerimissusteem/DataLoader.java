package org.andreas.reserveerimissusteem;

import org.andreas.reserveerimissusteem.entity.Table;
import org.andreas.reserveerimissusteem.repository.TableRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final TableRepository tableRepository;

    public DataLoader(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("DataLoader running, count: " + tableRepository.count());

        if (tableRepository.count()>0) return;
        tableRepository.saveAll(List.of(
                new Table("M1",4,false,false,"MAIN",1,1),
                new Table("M2",4,false,true,"MAIN",1,1),
                new Table("M3",4,true,false,"MAIN",1,1),
                new Table("M4",2,true,true,"MAIN",3,2),
                //terrace
                new Table("T1",2,false,false,"TERRACE",1,1),
                new Table("T2",4,false,false,"TERRACE",2,1),
                //private
                new Table("P1",6,true,true,"PRIVATE",1,1),
                new Table("P2",8,true,true,"PRIVATE",2,1)
        ));
    }


}
