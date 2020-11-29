package com.example.pokereto.model;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable {

    private String name;
    private int id;
    private String dbid;
    private Sprites sprites;
    private List<Stat> stats;
    private List<Type> types;
    private long time;

    public Pokemon() {
    }

    public Pokemon(String name, int id, Sprites sprites, List<Stat> stats, List<Type> types) {
        this.name = name;
        this.id = id;
        this.sprites = sprites;
        this.stats = stats;
        this.types = types;
        time = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDbid() {
        return dbid;
    }

    public void setDbid(String dbid) {
        this.dbid = dbid;
    }

}
