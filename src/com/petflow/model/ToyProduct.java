package com.petflow.model;
import com.petflow.interfaces.Discountable;
public class ToyProduct extends Product implements Discountable {

    public ToyProduct(String id, String name, double price, int stock) {
        super(id, name, price, stock, "Mainan");
    }

    @Override
    public double calculateDiscount() {
        return 0.10; 
    }
}