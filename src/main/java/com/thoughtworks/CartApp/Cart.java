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
        Cart cart = (Cart) o;
        if(this.size() != cart.size()) return false;
        for(int i=0; i<this.size(); i++){
            if(!this.get(i).equals(cart.get(i))) return false;
        }
        return totalCost == cart.totalCost;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for(Item items: this){
            hash += Objects.hash(items);
        }
        return hash;
    }
}
