// public class MainApp {
//     public static void main(String[] args) {
//         System.out.println("OK");
//     }
// }

import java.util.Scanner;
import model.User;
import model.Admin;
import model.Cashier;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserDB userDB = new UserDB();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = userDB.login(username, password);

        if (user != null) {

            System.out.println("Login sukses");
            System.out.println("Role: " + user.getRole());

            if (user instanceof Admin) {
                menuAdmin();
            } 
            else if (user instanceof Cashier) {
                menuCashier();
            }

        } else {
            System.out.println("Login gagal");
        }
    }

    static void menuAdmin() {
        System.out.println("=== MENU ADMIN ===");
        System.out.println("1. Kelola Produk");
        System.out.println("2. Kelola User");
        System.out.println("3. Lihat Laporan");
    }

    static void menuCashier() {
        System.out.println("=== MENU CASHIER ===");
        System.out.println("1. Transaksi");
        System.out.println("2. Lihat Produk");
    }
}