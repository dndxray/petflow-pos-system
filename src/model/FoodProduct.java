package model;

public class FoodProduct extends Product {

    public FoodProduct(String id, String name, double price, int stock) {
        super(id, name, price, stock, "Makanan");
    }

    @Override
    public double calculateDiscount() {
        return 0.05;
    }
}