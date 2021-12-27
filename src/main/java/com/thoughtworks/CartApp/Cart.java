package com.thoughtworks.CartApp;

import java.util.ArrayList;

public class Cart{
    private final Iterable<Item> items;
    private double totalCost;

    public Cart(Iterable<Item> items) {
        this.items = items;
        totalCost = 0.0;
        for(Item item: items){
            totalCost += item.getCost();
        }
    }

    public Iterable<Item> getItems() {
        return items;
    }

    public double getTotalCost() {
        return totalCost;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Cart)) return false;
//        Cart cart = (Cart) o;
//        if(this.getItems() instanceof Collection<?> && cart.getItems() instanceof  Collection<?>){
//            if(((Collection<?>) this.getItems()).size() != ((Collection<?>) cart.getItems()).size()){
//                return false;
//            }
//        }
//        if(this.getItems().size() != cart.getItems().size()) return false;
//        for(int i=0; i<this.getItems().size(); i++){
//            if(!this.getItems().get(i).equals(cart.getItems().get(i))) return false;
//        }
//        return Double.compare(cart.totalCost, totalCost) == 0;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(items, totalCost);
//    }
}
