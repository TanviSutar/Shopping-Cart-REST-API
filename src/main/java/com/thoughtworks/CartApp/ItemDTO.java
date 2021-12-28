package com.thoughtworks.CartApp;

public class ItemDTO {
    private String name;
    private double cost;

    public ItemDTO(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}
