package com.thoughtworks.CartApp;

import java.util.Locale;
import java.util.Objects;

public class Item {
    //private final int id;
    private final String name;
    private final double cost;
    private static int idCount;

    Item(String name, double cost) {
        this.name = name.toLowerCase(Locale.ROOT);
        this.cost = cost;
        //this.id = idCount++;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

//    public int getId() {
//        return id;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return name.equalsIgnoreCase(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
