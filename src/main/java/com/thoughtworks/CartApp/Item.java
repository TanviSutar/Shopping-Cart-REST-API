package com.thoughtworks.CartApp;

import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private double cost;
    private static int idCount;

    public Item(String name, double cost) {
        this.name = name;
        this.cost = cost;
        this.id = idCount++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Double.compare(item.cost, cost) == 0 && name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost);
    }
}
