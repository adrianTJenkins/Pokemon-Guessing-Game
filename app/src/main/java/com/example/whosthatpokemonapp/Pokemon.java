package com.example.whosthatpokemonapp;

import android.widget.ImageView;

public class Pokemon {
    public String name;
    public String imgUrl;

    public Pokemon() {

    }

    public Pokemon(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setImgUrl(String url) {
        this.imgUrl = url;
    }
}