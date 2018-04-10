package com.example.matthew.mvvmgoods.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable{

    @SerializedName("name") public String name;
    @SerializedName("price") private float price;
    @SerializedName("unit") public String unit;
    private int quanity = 0;

    public Item(String name, float price, String unit) {
        this.unit = unit;
        this.name = name;
        this.price = price;
    }

    public Item(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public int getQuanity() {
        return quanity;
    }

    public void incremementQuantity() {
        quanity++;
    }
}
