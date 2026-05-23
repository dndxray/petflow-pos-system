
import model.CartItem;
import model.Transaction;
import java.sql.*;

public class TransactionDB {

    public void saveTransaction(Transaction tx) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            // Simpan header transaksi
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO transactions VALUES (?, ?, ?, ?, ?, ?, NOW())"
            );
            ps.setString(1, tx.getTransactionId());
            ps.setString(2, tx.getCashierId());
            ps.setString(3, tx.getCashierName());
            ps.setDouble(4, tx.getTotalAmount());
            ps.setDouble(5, tx.getAmountPaid());
            ps.setDouble(6, tx.getChange());
            ps.executeUpdate();

            // Simpan detail item
            for (CartItem item : tx.getItems()) {
                PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO transaction_items (transaction_id, product_id, product_name, quantity, price, discount) VALUES (?, ?, ?, ?, ?, ?)"
                );
                ps2.setString(1, tx.getTransactionId());
                ps2.setString(2, item.getProduct().getId());
                ps2.setString(3, item.getProduct().getName());
                ps2.setInt(4, item.getQuantity());
                ps2.setDouble(5, item.getProduct().getPrice());
                ps2.setDouble(6, item.getProduct().calculateDiscount());
                ps2.executeUpdate();
            }

            System.out.println("✅ Transaksi tersimpan!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}