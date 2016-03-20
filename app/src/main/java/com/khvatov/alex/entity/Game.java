package com.khvatov.alex.entity;

import java.util.Date;

/**
 * Entity represented a game
 */
public class Game {

    private int id;
    private Platform platform;
    private String name;
    private Date finishedDate;

    public Game() {
    }

    public Game(Platform platform, String name, Date finishedDate) {
        this.platform = platform;
        this.name = name;
        this.finishedDate = finishedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Platform getPlatform() {
        return platform;
    }

    public String getName() {
        return name;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }
}
