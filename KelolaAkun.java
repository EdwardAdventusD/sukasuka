// KelolaAkun.java
import java.util.ArrayList;
import java.util.Scanner;

public class KelolaAkun {
    private ArrayList<User> daftarUser;
    
    public KelolaAkun(ArrayList<User> daftarUser) {
        this.daftarUser = daftarUser;

        this.scanner = new Scanner(System.in);
        
        // Tambahkan user admin default jika belum ada
        if (this.daftarUser.isEmpty()) {
            this.daftarUser.add(new User("admin", "admin123", "admin"));
        }
    }

    // Main method untuk menjalankan secara mandiri
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        KelolaAkun app = new KelolaAkun(users);
        app.menuAdmin();
    }

    public void menuAdmin() {
        while (true) {
            System.out.println("\n===== MENU KELOLA AKUN (ADMIN) =====");
            System.out.println("1. Tambah Akun");
            System.out.println("2. Lihat Daftar Akun");
            System.out.println("3. Edit Akun");
            System.out.println("4. Hapus Akun");
            System.out.println("0. Keluar");
            System.out.print("Pilihan: ");
            
            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (pilihan) {
                    case 1:
                        tambahAkun();
                        break;
                    case 2:
                        tampilkanAkun();
                        break;
                    case 3:
                        editAkun();
                        break;
                    case 4:
                        hapusAkun();
                        break;
                    case 0:
                        System.out.println("Keluar dari program...");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (Exception e) {
                System.out.println("Input harus berupa angka!");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private void tambahAkun() {
        System.out.println("\n--- Tambah Akun Baru ---");
        
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        if (username.isEmpty()) {
            System.out.println("Username tidak boleh kosong!");
            return;
        }
        
        if (cariUserByUsername(username) != null) {
            System.out.println("Username sudah terdaftar!");
            return;
        }
        
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        if (password.isEmpty()) {
            System.out.println("Password tidak boleh kosong!");
            return;
        }
        
        System.out.print("Role (admin/kasir): ");
        String role = scanner.nextLine().toLowerCase();
        
        if (!role.equals("admin") && !role.equals("kasir")) {
            System.out.println("Role hanya bisa admin atau kasir!");
            return;
        }
        
        daftarUser.add(new User(username, password, role));
        System.out.println("Akun berhasil ditambahkan!");
    }

    private void tampilkanAkun() {
        System.out.println("\n--- Daftar Akun ---");
        if (daftarUser.isEmpty()) {
            System.out.println("Belum ada akun yang terdaftar.");
            return;
        }
        
        System.out.printf("%-15s %-10s\n", "Username", "Role");
        System.out.println("---------------------");
        for (User user : daftarUser) {
            System.out.printf("%-15s %-10s\n", 
                            user.getUsername(), 
                            user.getRole());
        }
    }

    private void editAkun() {
        System.out.println("\n--- Edit Akun ---");
        System.out.print("Masukkan username yang akan diedit: ");
        String username = scanner.nextLine().trim();
        
        User user = cariUserByUsername(username);
        if (user == null) {
            System.out.println("Username tidak ditemukan!");
            return;
        }
        
        System.out.println("\nData saat ini:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Role: " + user.getRole());
        
        System.out.print("\nPassword baru (kosongkan jika tidak ingin mengubah): ");
        String passwordBaru = scanner.nextLine().trim();
        
        if (!passwordBaru.isEmpty()) {
            user.setPassword(passwordBaru);
        }
        
        System.out.print("Role baru (admin/kasir, kosongkan jika tidak ingin mengubah): ");
        String roleBaru = scanner.nextLine().toLowerCase().trim();
        
        if (!roleBaru.isEmpty()) {
            if (roleBaru.equals("admin") || roleBaru.equals("kasir")) {
                user.setRole(roleBaru);
            } else {
                System.out.println("Role tidak valid, data role tidak diubah");
            }
        }
        
        System.out.println("Akun berhasil diupdate!");
    }

    private void hapusAkun() {
        System.out.println("\n--- Hapus Akun ---");
        System.out.print("Masukkan username yang akan dihapus: ");
        String username = scanner.nextLine().trim();
        
        User user = cariUserByUsername(username);
        if (user == null) {
            System.out.println("Username tidak ditemukan!");
            return;
        }
        
        if (user.getUsername().equals("admin")) {
            System.out.println("Tidak bisa menghapus akun admin utama!");
            return;
        }
        
        daftarUser.remove(user);
        System.out.println("Akun berhasil dihapus!");
    }

    private User cariUserByUsername(String username) {
        for (User user : daftarUser) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    // Inner class User
    public static class User {
        private String username;
        private String password;
        private String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        // Getter dan Setter
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getRole() { return role; }
        public void setPassword(String password) { this.password = password; }
        public void setRole(String role) { this.role = role; }
    }
}