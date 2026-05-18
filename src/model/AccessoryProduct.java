package model;

public class AccessoryProduct extends Product {

    public AccessoryProduct(String id, String name, double price, int stock) {
        super(id, name, price, stock, "Aksesori");
    }

    @Override
    public double calculateDiscount() {
        return 0.00; 
    }
}