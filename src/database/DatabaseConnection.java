//package database;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
private static final String URL =
"jdbc:mysql://localhost:3306/petshop_pos?useSSL=false&allowPublicKeyRetrieval=true";

private static final String USER = "root";
private static final String PASSWORD = "";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Test koneksi
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println(" Koneksi MySQL berhasil!");
            conn.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}