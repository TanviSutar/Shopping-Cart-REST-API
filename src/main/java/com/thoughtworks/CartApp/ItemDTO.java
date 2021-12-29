package com.thoughtworks.CartApp;

public class ItemDTO implements DTO{
    private final String name;
    private final double cost;

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
