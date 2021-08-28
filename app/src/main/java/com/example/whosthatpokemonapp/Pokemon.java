package com.example.whosthatpokemonapp;

import android.widget.ImageView;

public class Pokemon {
    public final String name;
    public final String imgUrl;

    public Pokemon(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }
}