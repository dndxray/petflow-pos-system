package com.petflow.controller;
import com.petflow.database.ProductDB;
import com.petflow.model.Product;
import java.util.List;

public class ProductController {
    private ProductDB productDB = new ProductDB();
    public List<Product> getAllProducts() {
        return productDB.getAllProducts();
    }
    
    public boolean addProduct(Product product) {
        return productDB.addProduct(product);
    }
    
    public boolean deleteProduct(String id) {
        return productDB.deleteProduct(id);
    }
}