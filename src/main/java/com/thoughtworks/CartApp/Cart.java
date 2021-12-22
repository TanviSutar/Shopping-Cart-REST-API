package com.thoughtworks.CartApp;

import java.util.ArrayList;
import java.util.Objects;

public class Cart extends ArrayList<Item> {
    private int totalCost;

    public Cart(){
        totalCost = 0;
    }

    public void addItem(Item item){
        this.add(item);
        totalCost += item.getCost();
    }

    public double totalCost(){
        return totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        if (!super.equals(o)) return false;
        Cart items = (Cart) o;
        return totalCost == items.totalCost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), totalCost);
    }
}
