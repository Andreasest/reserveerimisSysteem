package org.andreas.reserveerimissusteem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@jakarta.persistence.Table(name="RESERVATIONS")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="RESERVATION_NAME")
    private String name;

    @Column(name="RESERVATION_NUMBER_OF_PEOPLE")
    private Integer numberOfPeople;

    @ManyToOne
    @JoinColumn(name = "TABLE_ID")
    private Table table;

    @Column(name="RESERVATION_START")
    private LocalDateTime start;

    @Column(name="RESERVATION_END")
    private LocalDateTime end;

    public Reservation() {
    }

    public Reservation(String name, int numberOfPeople, Table table, LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.numberOfPeople = numberOfPeople;
        this.table = table;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public Table getTable() {
        return table;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
