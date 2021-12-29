package com.thoughtworks.CartApp;

import javax.persistence.Entity;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private double cost;

    public Item(){}

    public Item(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost);
    }
}
