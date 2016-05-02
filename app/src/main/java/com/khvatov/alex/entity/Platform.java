package com.khvatov.alex.entity;

import java.io.Serializable;

/**
 * Entity represented Platform
 */
public class Platform implements Serializable {

    private long id;
    private String name;

    public Platform(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
