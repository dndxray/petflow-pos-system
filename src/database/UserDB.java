import java.sql.*;
import model.User;
import model.Admin;
import model.Cashier;

public class UserDB {

    public User login(String username, String password) {

        try {
            Connection conn = DatabaseConnection.getConnection();

            // AD
            String sql = "SELECT * FROM users WHERE name=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

            String role = rs.getString("role");

            String id = rs.getString("id");
            String name = rs.getString("name");
            String pass = rs.getString("password");

            if (role.equalsIgnoreCase("ADMIN")) {
                return new Admin(id, name, pass);
            } 
            else {
                return new Cashier(id, name, pass);
            }
        }

        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
