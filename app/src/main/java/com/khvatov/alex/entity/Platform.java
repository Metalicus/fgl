package com.khvatov.alex.entity;

/**
 * Entity represented Platform
 */
public class Platform {

    private int id;
    private String name;

    public Platform(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
