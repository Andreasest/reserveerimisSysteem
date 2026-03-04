package org.andreas.reserveerimissusteem.entity;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name="TABLES")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="TABLE_NAME")
    private String name;

    @Column (name="TABLE_CAPACITY")
    private int capacity;

    @Column(name="IS_WINDOW")
    private boolean window;

    @Column(name="IS_QUIET")
    private boolean quiet;

    @Column(name="TABLE_ZONE")
    private String zone;

    @Column(name="TABLE_X")
    private int x;

    @Column(name="TABLE_Y")
    private int y;

    public Table() {
    }

    public Table(String name, int capacity, boolean window, boolean quiet, String zone, int x, int y) {
        this.name = name;
        this.capacity = capacity;
        this.window = window;
        this.quiet = quiet;
        this.zone = zone;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isWindow() {
        return window;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public String getZone() {
        return zone;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
