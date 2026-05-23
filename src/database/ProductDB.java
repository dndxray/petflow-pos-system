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
        else if (product instanceof ToyProduct) table = "toy_products";
        else table = "accessory_products";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE " + table + " SET stock = ? WHERE id = ?"
            );
            ps.setInt(1, newStock);
            ps.setString(2, product.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}