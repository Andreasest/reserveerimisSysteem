package org.andreas.reserveerimissusteem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@jakarta.persistence.Table(name="RESERVATIONS")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="RESERVATION_NAME")
    String name;

    @Column(name="RESERVATION_NUMBER_OF_PEOPLE")
    int numberOfPeople;

    @ManyToOne
    @JoinColumn(name = "TABLE_ID")
    Table table;

    @Column(name="RESERVATION_START")
    LocalDateTime start;

    @Column(name="RESERVATION_END")
    LocalDateTime end;
}
