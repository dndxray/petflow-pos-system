package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();
    private String cashierId;

    public Cart(String cashierId) {
        this.cashierId = cashierId;
    }

    public void addItem(Product product, int quantity) throws Exception {
        assert quantity > 0;
        if (product.getStock() < quantity) {
            throw new exception.OutOfStockException("Stok " + product.getName() + " tidak cukup!");
        }
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(String productId) throws Exception {
        CartItem toRemove = null;
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                toRemove = item;
                break;
            }
        }
        if (toRemove == null) {
            throw new exception.ProductNotFoundException("Produk tidak ada di keranjang!");
        }
        items.remove(toRemove);
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) total += item.getSubtotal();
        return total;
    }

    public void clear() { 
        items.clear(); 
    }
    public List<CartItem> getItems() { 
        return new ArrayList<>(items); 
    }
    public boolean isEmpty() { 
        return items.isEmpty(); 
    }
    public String getCashierId() { 
        return cashierId; 
    }
}