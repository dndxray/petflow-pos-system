package model;

public class ToyProduct extends Product {

    public ToyProduct(String id, String name, double price, int stock) {
        super(id, name, price, stock, "Mainan");
    }

    @Override
    public double calculateDiscount() {
        return 0.10; 
    }
}