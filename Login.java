// Login.java
import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    private boolean isAppEnd = false;
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser = null;
    private ArrayList<KelolaLayanan.Layanan> daftarLayanan = new ArrayList<>();
    private ArrayList<KelolaDiskon.Diskon> daftarDiskon = new ArrayList<>();

    public void run() {
        Scanner input = new Scanner(System.in);

        // Tambahkan user admin default
        if (users.isEmpty()) {
            users.add(new User("admin", "admin123", "admin"));
        }

        while (!isAppEnd) {
            System.out.println("\n===== APLIKASI KELOMPOK SUKA SUKA =====");
            if (currentUser != null) {
                System.out.println("Anda login sebagai: " + currentUser.getUsername() + 
                                 " (" + currentUser.getRole() + ")");
            }
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.println("0. Keluar");
            System.out.print("Masukkan Pilihan Anda: ");
            
            try {
                int pilihan = input.nextInt();
                input.nextLine();

                switch (pilihan) {
                    case 0:
                        isAppEnd = true;
                        break;
                    case 1:
                        loginUser(input);
                        break;
                    case 2:
                        registerUser(input);
                        break;
                    default:
                        System.out.println("Pilihan Tidak Valid. Silakan Coba Lagi.");
                }
            } catch (Exception e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                input.nextLine();
            }
        }
        input.close();
        System.out.println("Terima kasih!");
    }

    private void loginUser(Scanner input) {
        System.out.print("Masukkan Username Anda: ");
        String username = input.nextLine().trim();
        System.out.print("Masukkan Password Anda: ");
        String password = input.nextLine().trim();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.checkPassword(password)) {
                    currentUser = user;
                    System.out.println("Login Berhasil! Selamat datang, " + 
                                     username + " (" + user.getRole() + ").");
                    
                    // Redirect ke menu sesuai role
                    if (currentUser.getRole().equals("admin")) {
                        MenuAdmin menuAdmin = new MenuAdmin(users, daftarLayanan, daftarDiskon);
                        menuAdmin.showMenu();
                    } else {
                        MenuKasir menuKasir = new MenuKasir();
                        menuKasir.showMenu();
                    }
                    return;
                } else {
                    System.out.println("Password Salah. Silakan Coba Lagi.");
                    return;
                }
            }
        }
        System.out.println("Username Tidak Ditemukan. Silakan Coba Lagi.");
    }

    private void registerUser(Scanner input) {
        System.out.print("Masukkan Username Baru Anda: ");
        String username = input.nextLine().trim();

        if (getUserByUsername(username) != null) {
            System.out.println("Username Sudah Terdaftar. Silakan Gunakan Username Lain.");
            return;
        }

        System.out.print("Masukkan Password Baru Anda: ");
        String password = input.nextLine().trim();

        System.out.print("Masukkan Role (admin/kasir): ");
        String role = input.nextLine().toLowerCase();

        if (!role.equals("admin") && !role.equals("kasir")) {
            System.out.println("Role Tidak Valid. Gunakan 'admin' Atau 'kasir'.");
            return;
        }

        users.add(new User(username, password, role));
        System.out.println("Akun Baru Berhasil Dibuat Dengan Role '" + role + "'. Silakan Login Kembali.");
    }

    private User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Login app = new Login();
        app.run();
    }
}