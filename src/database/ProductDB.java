import model.FoodProduct;
import model.ToyProduct;
import model.AccessoryProduct;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDB {

    // Ambil semua produk dari ketiga tabel
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();

            // Food
            ResultSet rs1 = conn.createStatement().executeQuery("SELECT * FROM food_products");
            while (rs1.next()) {
                list.add(new FoodProduct(
                    rs1.getString("id"),
                    rs1.getString("name"),
                    rs1.getDouble("price"),
                    rs1.getInt("stock")
                ));
            }

            // Toy
            ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM toy_products");
            while (rs2.next()) {
                list.add(new ToyProduct(
                    rs2.getString("id"),
                    rs2.getString("name"),
                    rs2.getDouble("price"),
                    rs2.getInt("stock")
                ));
            }

            // Accessory
            ResultSet rs3 = conn.createStatement().executeQuery("SELECT * FROM accessory_products");
            while (rs3.next()) {
                list.add(new AccessoryProduct(
                    rs3.getString("id"),
                    rs3.getString("name"),
                    rs3.getDouble("price"),
                    rs3.getInt("stock")
                ));
            }

            // Menutup resource database dengan baik
            rs1.close();
            rs2.close();
            rs3.close();

        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Update stok setelah transaksi
    public void updateStock(Product product, int newStock) {
        String table = "";
        if (product instanceof FoodProduct) table = "food_products";
        // Menggunakan else if agar pengecekan instansiasi objek anak lebih presisi
        else if (product instanceof ToyProduct) table = "toy_products";
        else if (product instanceof AccessoryProduct) table = "accessory_products";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE " + table + " SET stock = ? WHERE id = ?"
            );
            ps.setInt(1, newStock);
            ps.setString(2, product.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== 1. FUNGSI TAMBAH PRODUK BARU (ADMIN) =====
    public void addProduct(Product product) throws Exception {
        String table = "";
        if (product instanceof FoodProduct) table = "food_products";
        else if (product instanceof ToyProduct) table = "toy_products";
        else if (product instanceof AccessoryProduct) table = "accessory_products";

        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO " + table + " (id, name, price, stock) VALUES (?, ?, ?, ?)";
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, product.getId());
        ps.setString(2, product.getName());
        ps.setDouble(3, product.getPrice()); // mengambil harga dasar produk
        ps.setInt(4, product.getStock());
        
        ps.executeUpdate();
        ps.close();
    }

    // ===== 2. FUNGSI HAPUS PRODUK (ADMIN) =====
    public void deleteProduct(String id) throws Exception {
        Connection conn = DatabaseConnection.getConnection();
        boolean deleted = false;
        
        // Karena kita tidak tahu ID tersebut berada di tabel mana, 
        // kita coba hapus di ketiga tabel satu per satu berdasarkan ID-nya.
        String[] tables = {"food_products", "toy_products", "accessory_products"};
        
        for (String table : tables) {
            String query = "DELETE FROM " + table + " WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            
            int rowsAffected = ps.executeUpdate();
            ps.close();
            
            if (rowsAffected > 0) {
                deleted = true;
                break; // Jika sudah ketemu dan terhapus di salah satu tabel, keluar dari loop
            }
        }
        
        // Jika ID tidak ditemukan di ketiga tabel tersebut, lemparkan error
        if (!deleted) {
            throw new Exception("Produk dengan ID " + id + " tidak ditemukan di kategori manapun!");
        }
    }
}