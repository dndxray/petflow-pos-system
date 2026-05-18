package model;

public class Cashier extends User {

    public Cashier(String id, String name, String password) {
        super(id, name, password, "CASHIER");
    }

    @Override
    public String getPermissions() {
        return "Kasir dapat: lihat produk, buat transaksi, kelola keranjang";
    }
}