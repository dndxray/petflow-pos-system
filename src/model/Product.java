package model;

public abstract class Product {
    private String id;
    private String name;
    private double price;
    private int stock;
    private String category;

    public Product(String id, String name, double price, int stock, String category) {
        assert price > 0 : "Harga harus lebih dari 0!";
        assert stock >= 0 : "Stok tidak boleh negatif!";
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // POLYMORPHISM: tiap subclass isi sendiri
    public abstract double calculateDiscount();

    public double getFinalPrice() {
        return price * (1 - calculateDiscount());
    }

    // Getters
    public String getId() { 
        return id; 
    }

    public String getName() { 
        return name; 
    }

    public double getPrice() { 
        return price; 
    }

    public int getStock() { 
        return stock; 
    }

    public String getCategory() { 
        return category; 
    }

    // Setters
    public void setPrice(double price) {
        assert price > 0 : "Harga harus lebih dari 0!";
        this.price = price;
    }
    public void setName(String name) { 
        this.name = name; 
    }
    
    public void setStock(int stock) {
        assert stock >= 0 : "Stok tidak boleh negatif!";
        this.stock = stock;
    }

    public void reduceStock(int quantity) throws Exception {
        assert quantity > 0 : "Jumlah harus lebih dari 0!";
        if (stock < quantity) {
            throw new exception.OutOfStockException("Stok " + name + " tidak cukup! Tersisa: " + stock);
        }
        this.stock -= quantity;
    }

    public void addStock(int quantity) {
        assert quantity > 0;
        this.stock += quantity;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Rp%.0f (Stok: %d)", id, name, price, stock);
    }
}