package com.petflow.repository;

import java.util.Optional;

import com.petflow.model.Product;

public class ProductRepository extends Repository<Product> {

    @Override
    public Optional<Product> findById(String id) {
        for (Product p : data) {
            if (p.getId().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public boolean isStockAvailable(String id, int qty) {
        Optional<Product> p = findById(id);
        return p.isPresent() && p.get().getStock() >= qty;
    }

    public void showAvailableProducts() {

        data.stream()
            .filter(product -> product.getStock() > 0)
            .forEach(System.out::println);
    }
}