package com.petflow.controller;
import com.petflow.model.Cart;
import com.petflow.model.CartItem;
import com.petflow.model.Product;
import java.util.List;

public class CartController {
    private Cart cart;
    public CartController(String cashierId) {
        this.cart = new Cart(cashierId);
    }

    public void addItemToCart(Product product, int quantity) throws Exception {
        if (product == null) {
            throw new IllegalArgumentException("produk tidak boleh null");
        }
        cart.addItem(product, quantity);
    }

    public List<CartItem> getCartItems() {
        return cart.getItems();
    }

    public void removeItemFromCart(String productId) throws Exception {
        cart.removeItem(productId);
    }

    public void clearCart() {
        cart.clear();
    }

    public double getCartTotal() {
        return cart.getTotal();
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }

    public Cart getCart() {
        return cart;
    }
}