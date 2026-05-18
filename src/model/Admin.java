package model;

public class Admin extends User {

    public Admin(String id, String name, String password) {
        super(id, name, password, "Admin");
    }

    @Override
    public String getPermissions() {
        return "Admin dapat: semua akses kasir + kelola produk, kelola user, lihat laporan";
    }
}