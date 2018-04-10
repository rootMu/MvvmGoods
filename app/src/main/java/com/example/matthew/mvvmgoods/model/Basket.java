package com.example.matthew.mvvmgoods.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Basket implements Serializable {

    private ArrayList<Item> basket = new ArrayList<>();

    public ArrayList<Item> getBasket(){
        return basket;
    }

    public void addToBasket(Item item){
        basket.add(item);
    }

    public float calculateTotal(){
        float total = 0;
        for (Item item:
             basket) {
            total += item.getPrice();
        }
        return total;
    }

}
