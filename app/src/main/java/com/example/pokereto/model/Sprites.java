package com.example.pokereto.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sprites implements Serializable {
    @SerializedName("front_default")
    private String sprite;

    public Sprites() {}

    public Sprites(String sprite) {
        this.sprite = sprite;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
