package com.example.pokereto.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Stat implements Serializable {
    @SerializedName("base_stat")
    private int baseStat;

    public Stat() {
    }

    public Stat(int baseStat) {
        this.baseStat = baseStat;
    }

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }
}
