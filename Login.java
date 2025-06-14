import model.User;
import admin.KelolaAkun;
import admin.KelolaPelanggan;
import admin.KelolaPelanggan.Pelanggan;
import admin.KelolaPelanggan.Pesanan;
import admin.KelolaLayanan;
import admin.KelolaLayanan.Layanan;
import admin.KelolaDiskon;
import admin.KelolaDiskon.Diskon;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    private boolean isAppEnd = false;
    private KelolaAkun kelolaAkun;
    private User currentUser = null;
    private KelolaPelanggan kelolaPelanggan;
    private KelolaLayanan kelolaLayanan;
    private KelolaDiskon kelolaDiskon;
    private ArrayList<Pelanggan> daftarPelanggan;
    private ArrayList<Layanan> daftarLayanan;
    private ArrayList<Diskon> daftarDiskon;
    private ArrayList<Pesanan> daftarPesanan;

    public Login(ArrayList<User> users, ArrayList<Pelanggan> daftarPelanggan, ArrayList<Layanan> daftarLayanan, ArrayList<Diskon> daftarDiskon) {
        this.kelolaAkun = new KelolaAkun(users);
        this.daftarPelanggan = daftarPelanggan;
        this.daftarLayanan = daftarLayanan;
        this.daftarDiskon = daftarDiskon;
        this.daftarPesanan = new ArrayList<>();
        
        this.kelolaPelanggan = new KelolaPelanggan(this.daftarPelanggan, this.daftarPesanan);
        this.kelolaLayanan = new KelolaLayanan(this.daftarLayanan);
        this.kelolaDiskon = new KelolaDiskon(this.daftarDiskon);
    }

    public void run() {
        Scanner input = new Scanner(System.in);

        while (!isAppEnd) {
            System.out.println("\n===== APLIKASI LAUNDRY =====");
            if (currentUser != null) {
                System.out.println("Anda login sebagai: " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
                redirectToRoleMenu();
                currentUser = null;
                continue;
            }
            
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.println("0. Keluar");
            System.out.print("Pilihan: ");
            
            try {
                int choice = input.nextInt();
                input.nextLine();

                switch (choice) {
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
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                input.nextLine();
            }
        }
        input.close();
    }

    private void redirectToRoleMenu() {
        if (currentUser.getRole().equals("admin")) {
            admin.MenuAdmin menuAdmin = new admin.MenuAdmin(kelolaAkun.getDaftarUser(), kelolaPelanggan, kelolaLayanan, kelolaDiskon);
            menuAdmin.showMenu();
        } else {
            kasir.MenuKasir menuKasir = new kasir.MenuKasir(kelolaPelanggan, kelolaLayanan, kelolaDiskon);
            menuKasir.showMenu();
        }
    }

    private void loginUser(Scanner input) {
        System.out.print("Username: ");
        String username = input.nextLine().trim();
        System.out.print("Password: ");
        String password = input.nextLine().trim();

        User user = kelolaAkun.cariUserByUsername(username);
        if (user != null) {
            if (user.checkPassword(password)) {
                currentUser = user;
                System.out.println("Login berhasil!");
            } else {
                System.out.println("Password salah!");
            }
        } else {
            System.out.println("Username tidak ditemukan!");
        }
    }

    private void registerUser(Scanner input) {
        System.out.println("\n--- REGISTRASI USER BARU ---");
        
        System.out.print("Username: ");
        String username = input.nextLine().trim();
        
        if (username.isEmpty()) {
            System.out.println("Username tidak boleh kosong!");
            return;
        }
        
        if (kelolaAkun.cariUserByUsername(username) != null) {
            System.out.println("Username sudah digunakan!");
            return;
        }
        
        System.out.print("Password: ");
        String password = input.nextLine().trim();
        
        if (password.isEmpty()) {
            System.out.println("Password tidak boleh kosong!");
            return;
        }

        System.out.print("Role (admin/kasir): ");
        String role = input.nextLine().toLowerCase().trim();
        
        if (!role.equals("admin") && !role.equals("kasir")) {
            System.out.println("Role harus 'admin' atau 'kasir'!");
            return;
        }
        
        kelolaAkun.tambahAkun(new User(username, password, role));
        System.out.println("Registrasi berhasil! Role: " + role);
    }

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
        ArrayList<Layanan> daftarLayanan = new ArrayList<>();
        ArrayList<Diskon> daftarDiskon = new ArrayList<>();
        
        new Login(users, daftarPelanggan, daftarLayanan, daftarDiskon).run();
    }
}