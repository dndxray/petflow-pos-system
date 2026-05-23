import java.util.Scanner;
import java.util.List;
import model.User;
import model.Admin;
import model.Cashier;
import model.Product;
import model.FoodProduct;
import model.ToyProduct;
import model.AccessoryProduct;
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
                menuAdmin(user); // Mengoper objek user ke menu admin
            } else if (user instanceof Cashier) {
                menuCashier(user);
            }
        } 
        else {
            System.out.println(" Login gagal! Username atau password salah.");
        }
    }

    // ===== MENU ADMIN =====
    static void menuAdmin(User user) {
        // Proteksi parameter: Hanya role ADMIN yang boleh masuk ke dalam perulangan menu
        if (user == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
            System.out.println(" Akses Ditolak! Anda bukan Admin.");
            return;
        }

        while (true) {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Hapus Produk");
            System.out.println("3. Lihat Semua Produk");
            System.out.println("4. Kelola User Kasir (Tambah/Hapus Pegawai)");
            System.out.println("5. Lihat Total Omset");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            String pilihan = sc.nextLine();

            switch (pilihan) {
                case "1" -> tambahProdukAdmin(user);
                case "2" -> hapusProdukAdmin(user);
                case "3" -> lihatProduk();
                case "4" -> kelolaUserKasir(user);
                case "5" -> lihatOmset(user);
                case "0" -> {
                    System.out.println("Sampai jumpa, Admin!");
                    return;
                }
                default -> System.out.println(" Pilihan tidak valid!");
            }
        }
    }

    // ===== 1. TAMBAH PRODUK (ADMIN ONLY) =====
    static void tambahProdukAdmin(User user) {
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            System.out.println(" Akses ilegal!");
            return;
        }
        System.out.println("\n--- Tambah Produk Baru ---");
        System.out.print("Masukkan ID Produk (cth: P001): ");
        String id = sc.nextLine();
        System.out.print("Masukkan Nama Produk: ");
        String name = sc.nextLine();
        System.out.print("Masukkan Harga Dasar: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Masukkan Stok: ");
        int stock = Integer.parseInt(sc.nextLine());
        
        System.out.println("Kategori: 1. Food | 2. Toy | 3. Accessory");
        System.out.print("Pilih Kategori (1-3): ");
        String katPilihan = sc.nextLine();

        Product newProduct = null;
        switch (katPilihan) {
            case "1" -> newProduct = new FoodProduct(id, name, price, stock);
            case "2" -> newProduct = new ToyProduct(id, name, price, stock);
            case "3" -> newProduct = new AccessoryProduct(id, name, price, stock);
            default -> {
                System.out.println(" Kategori tidak valid! Pembatalan input.");
                return;
            }
        }

        // Karena ProductDB punyamu belum menyediakan fungsi .addProduct(), 
        // kamu bisa mengintegrasikannya nanti di kelas database kamu jika diperlukan.
        System.out.println(" Fitur addProduct siap dihubungkan ke ProductDB!");
    }

    // ===== 2. HAPUS PRODUK (ADMIN ONLY) =====
    static void hapusProdukAdmin(User user) {
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            System.out.println(" Akses ilegal!");
            return;
        }
        lihatProduk();
        System.out.print("\nMasukkan ID Produk yang ingin dihapus: ");
        String id = sc.nextLine();
        System.out.println(" ID " + id + " siap diproses hapus!");
    }

    // ===== 4. KELOLA USER KASIR (ADMIN ONLY) =====
    static void kelolaUserKasir(User user) {
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            System.out.println(" Akses ilegal!");
            return;
        }
        System.out.println("\n--- Kelola Pegawai Kasir ---");
        System.out.println("1. Tambah Kasir Baru");
        System.out.println("2. Hapus Kasir");
        System.out.print("Pilih (1-2): ");
        String pil = sc.nextLine();

        if (pil.equals("1")) {
            System.out.print("Masukkan ID User Baru (cth: USR002): ");
            String id = sc.nextLine();
            System.out.print("Masukkan Nama Lengkap: ");
            String name = sc.nextLine();
            System.out.print("Masukkan Password: ");
            String pass = sc.nextLine();

            User newCashier = new Cashier(id, name, pass);
            // Menjalankan fungsi addUser() murni milik UserDB yang sudah kamu buat
            try {
                // Catatan: Karena di UserDB milikmu method addUser belum dibuat,
                // Pastikan kamu mengimplementasikannya di UserDB agar baris ini tidak error saat compile.
                System.out.println(" Pegawai " + name + " siap dimasukkan via objek newCashier!");
            } catch (Exception e) {
                System.out.println(" Gagal menambah user: " + e.getMessage());
            }
        } 
        else if (pil.equals("2")) {
            System.out.print("Masukkan ID User Kasir yang akan dihapus: ");
            String id = sc.nextLine();
            System.out.println(" Proses hapus user ID " + id + " diarahkan ke UserDB.");
        }
    }

    // ===== 5. LIHAT TOTAL OMSET (ADMIN ONLY) =====
    static void lihatOmset(User user) {
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            System.out.println(" Akses ilegal!");
            return;
        }
        // Menampilkan omset simulasi atau dari database TransactionDB milikmu
        System.out.println("\n=================================");
        System.out.println(" TOTAL OMSET PENJUALAN: (Hubungkan ke TransactionDB)");
        System.out.println("=================================");
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

        List<Product> products = productDB.getAllProducts();
        Product selected = null;
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                selected = p;
                break;
            }
        }

        if (selected == null) {
            System.out.println(" Produk tidak ditemukan!");
            return;
        }

        System.out.print("Jumlah: ");
        int qty = Integer.parseInt(sc.nextLine());

        try {
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

        if (bayar < cart.getTotal()) {
            System.out.println(" Uang tidak cukup!");
            return;
        }

        String txId = "TRX" + String.format("%04d", txCounter++);
        Transaction tx = new Transaction(txId, cart.getItems(), bayar, user.getId(), user.getName());

        for (CartItem item : cart.getItems()) {
            int newStock = item.getProduct().getStock() - item.getQuantity();
            productDB.updateStock(item.getProduct(), newStock);
        }

        transactionDB.saveTransaction(tx);
        System.out.println(tx.generateReceipt());
        cart.clear();
    }
}