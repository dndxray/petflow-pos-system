import java.util.Scanner;
import java.util.List;
import model.User;
import model.Admin;
import model.Cashier;
import model.Product;
import model.Cart;
import model.CartItem;
import model.Transaction;

public class MainApp {

    static Scanner sc = new Scanner(System.in);
    static UserDB userDB = new UserDB();
    static ProductDB productDB = new ProductDB();
    static TransactionDB transactionDB = new TransactionDB();
    static int txCounter = 1;

    public static void main(String[] args) {

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = userDB.login(username, password);

        if (user != null) {
            System.out.println(" Login sukses! Selamat datang, " + user.getName());
            System.out.println("Role: " + user.getRole());

            if (user instanceof Admin) {
                menuAdmin();
            } else if (user instanceof Cashier) {
                menuCashier(user);
            }

        } 
        else {
            System.out.println(" Login gagal! Username atau password salah.");
        }
    }

    // ===== MENU ADMIN =====
    static void menuAdmin() {
        System.out.println("\n=== MENU ADMIN ===");
        System.out.println("1. Kelola Produk");
        System.out.println("2. Kelola User");
        System.out.println("3. Lihat Laporan");
        System.out.println("0. Keluar");
    }

    // ===== MENU KASIR =====
    static void menuCashier(User user) {
        Cart cart = new Cart(user.getId());

        while (true) {
            System.out.println("\n=== MENU KASIR ===");
            System.out.println("1. Lihat Produk");
            System.out.println("2. Tambah ke Keranjang");
            System.out.println("3. Lihat Keranjang");
            System.out.println("4. Checkout");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            String pilihan = sc.nextLine();

            switch (pilihan) {
                case "1" -> lihatProduk();
                case "2" -> tambahKeKeranjang(cart);
                case "3" -> lihatKeranjang(cart);
                case "4" -> checkout(cart, user);
                case "0" -> {
                    System.out.println("Sampai jumpa!");
                    return;
                }
                default -> System.out.println(" Pilihan tidak valid!");
            }
        }
    }

    // ===== LIHAT PRODUK =====
    static void lihatProduk() {
        List<Product> products = productDB.getAllProducts();

        if (products.isEmpty()) {
            System.out.println(" Belum ada produk!");
            return;
        }

        System.out.println("\n=== DAFTAR PRODUK ===");
        System.out.printf("%-8s %-20s %-12s %-10s %-10s%n", "ID", "Nama", "Kategori", "Harga", "Stok");
        System.out.println("=".repeat(65));

        for (Product p : products) {
            System.out.printf("%-8s %-20s %-12s Rp%-8.0f %-10d%n",
                p.getId(),
                p.getName(),
                p.getCategory(),
                p.getFinalPrice(),
                p.getStock()
            );
            if (p.calculateDiscount() > 0) {
                System.out.printf("         (diskon %.0f%% dari Rp%.0f)%n",
                    p.calculateDiscount() * 100, p.getPrice());
            }
        }
    }

    // ===== TAMBAH KE KERANJANG =====
    static void tambahKeKeranjang(Cart cart) {
        lihatProduk();

        System.out.print("\nMasukkan ID produk: ");
        String id = sc.nextLine();

        // Cari produk
        List<Product> products = productDB.getAllProducts();
        Product selected = null;
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                selected = p;
                break;
            }
        }

        // EXCEPTION: ProductNotFoundException
        if (selected == null) {
            System.out.println(" Produk tidak ditemukan!");
            return;
        }

        System.out.print("Jumlah: ");
        int qty = Integer.parseInt(sc.nextLine());

        try {
            // ASSERTION + EXCEPTION: OutOfStockException
            cart.addItem(selected, qty);
            System.out.println(" " + selected.getName() + " x" + qty + " ditambahkan!");
        } catch (Exception e) {
            System.out.println(" " + e.getMessage());
        }
    }

    // ===== LIHAT KERANJANG =====
    static void lihatKeranjang(Cart cart) {
        if (cart.isEmpty()) {
            System.out.println(" Keranjang kosong!");
            return;
        }

        System.out.println("\n=== KERANJANG ===");
        System.out.printf("%-20s %-8s %-12s %-12s%n", "Produk", "Qty", "Harga Satuan", "Subtotal");
        System.out.println("=".repeat(55));

        for (CartItem item : cart.getItems()) {
            System.out.printf("%-20s %-8d Rp%-10.0f Rp%.0f%n",
                item.getProduct().getName(),
                item.getQuantity(),
                item.getProduct().getFinalPrice(),
                item.getSubtotal()
            );
        }

        System.out.println("=".repeat(55));
        System.out.printf("TOTAL: Rp%.0f%n", cart.getTotal());
    }

    // ===== CHECKOUT =====
    static void checkout(Cart cart, User user) {
        if (cart.isEmpty()) {
            System.out.println(" Keranjang kosong!");
            return;
        }

        lihatKeranjang(cart);

        System.out.printf("%nTotal belanja: Rp%.0f%n", cart.getTotal());
        System.out.print("Jumlah bayar: Rp");
        double bayar = Double.parseDouble(sc.nextLine());

        // EXCEPTION: bayar kurang
        if (bayar < cart.getTotal()) {
            System.out.println(" Uang tidak cukup!");
            return;
        }

        // Buat transaksi
        String txId = "TRX" + String.format("%04d", txCounter++);
        Transaction tx = new Transaction(txId, cart.getItems(), bayar, user.getId(), user.getName());

        // Update stok di database
        for (CartItem item : cart.getItems()) {
            int newStock = item.getProduct().getStock() - item.getQuantity();
            productDB.updateStock(item.getProduct(), newStock);
        }

        // Simpan transaksi ke database
        transactionDB.saveTransaction(tx);

        // Cetak struk
        System.out.println(tx.generateReceipt());

        // Kosongkan keranjang
        cart.clear();
    }
}