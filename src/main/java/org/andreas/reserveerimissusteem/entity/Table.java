package org.andreas.reserveerimissusteem.entity;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name="TABLES")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name="TABLE_NAME")
    String name;

    @Column (name="TABLE_CAPACITY")
    int capacity;

    @Column(name="IS_WINDOW")
    boolean aknaKoht;

    @Column(name="IS_QUIET")
    boolean vaikneKoht;

    @Column(name="TABLE_ZONE")
    String tsoon;

    @Column(name="TABLE_X")
    int x;

    @Column(name="TABLE_Y")
    int y;
}
