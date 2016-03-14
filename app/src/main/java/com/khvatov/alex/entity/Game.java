package com.khvatov.alex.entity;

import java.util.Date;

/**
 * Entity represented a game
 */
public class Game {

    private Platform platform;
    private String name;
    private Date finishedDate;

    public Game(Platform platform, String name, Date finishedDate) {
        this.platform = platform;
        this.name = name;
        this.finishedDate = finishedDate;
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
}
