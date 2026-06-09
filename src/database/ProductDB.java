import model.FoodProduct;
import model.ToyProduct;
import model.AccessoryProduct;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDB {

    // =========================
    // AMBIL SEMUA PRODUK
    // =========================
    public List<Product> getAllProducts() {

        List<Product> list = new ArrayList<>();

        try {

            Connection conn = DatabaseConnection.getConnection();

            // FOOD
            ResultSet rs1 = conn.createStatement()
                    .executeQuery("SELECT * FROM food_products");

            while (rs1.next()) {
                list.add(new FoodProduct(
                        rs1.getString("id"),
                        rs1.getString("name"),
                        rs1.getDouble("price"),
                        rs1.getInt("stock")
                ));
            }

            // TOY
            ResultSet rs2 = conn.createStatement()
                    .executeQuery("SELECT * FROM toy_products");

            while (rs2.next()) {
                list.add(new ToyProduct(
                        rs2.getString("id"),
                        rs2.getString("name"),
                        rs2.getDouble("price"),
                        rs2.getInt("stock")
                ));
            }

            // ACCESSORY
            ResultSet rs3 = conn.createStatement()
                    .executeQuery("SELECT * FROM accessory_products");

            while (rs3.next()) {
                list.add(new AccessoryProduct(
                        rs3.getString("id"),
                        rs3.getString("name"),
                        rs3.getDouble("price"),
                        rs3.getInt("stock")
                ));
            }

            rs1.close();
            rs2.close();
            rs3.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =========================
    // UPDATE STOK
    // =========================
    public void updateStock(Product product, int newStock) {

        String table = "";

        if (product instanceof FoodProduct) {
            table = "food_products";
        } else if (product instanceof ToyProduct) {
            table = "toy_products";
        } else if (product instanceof AccessoryProduct) {
            table = "accessory_products";
        }

        try {

            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE " + table + " SET stock=? WHERE id=?"
            );

            ps.setInt(1, newStock);
            ps.setString(2, product.getId());

            ps.executeUpdate();

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // TAMBAH PRODUK
    // =========================
    public boolean addProduct(Product product) {

        String table = "";

        if (product instanceof FoodProduct) {
            table = "food_products";
        } else if (product instanceof ToyProduct) {
            table = "toy_products";
        } else if (product instanceof AccessoryProduct) {
            table = "accessory_products";
        }

        try {

            Connection conn = DatabaseConnection.getConnection();

            String sql =
                    "INSERT INTO " + table +
                    " (id,name,price,stock) VALUES (?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, product.getId());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());

            ps.executeUpdate();

            ps.close();

            return true;

        } catch (Exception e) {

            System.out.println("Gagal menambah produk : "
                    + e.getMessage());

            return false;
        }
    }

    // =========================
    // HAPUS PRODUK
    // =========================
    public boolean deleteProduct(String id) {

        try {

            Connection conn = DatabaseConnection.getConnection();

            String[] tables = {
                    "food_products",
                    "toy_products",
                    "accessory_products"
            };

            for (String table : tables) {

                String sql =
                        "DELETE FROM " + table + " WHERE id=?";

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ps.setString(1, id);

                int affected = ps.executeUpdate();

                ps.close();

                if (affected > 0) {
                    return true;
                }
            }

        } catch (Exception e) {

            System.out.println("Gagal menghapus produk : "
                    + e.getMessage());
        }

        return false;
    }
}