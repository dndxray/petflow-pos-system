package model;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        assert quantity > 0 : "Jumlah item harus lebih dari 0!";
        this.product = product;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return product.getFinalPrice() * quantity;
    }

    public Product getProduct() { 
        return product; 
    }

    public int getQuantity() { 
        return quantity; 
    }
    
    public void setQuantity(int quantity) {
        assert quantity > 0;
        this.quantity = quantity;
    }
}