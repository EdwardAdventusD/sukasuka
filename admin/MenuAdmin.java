package admin;

import java.util.ArrayList;
import java.util.Scanner;
import model.User;

public class MenuAdmin {
    private ArrayList<User> users;
    private KelolaPelanggan kelolaPelanggan;
    private KelolaLayanan kelolaLayanan;
    private KelolaDiskon kelolaDiskon;
    private Scanner scanner;
    private KelolaAkun kelolaAkun;

    public MenuAdmin(ArrayList<User> users, KelolaPelanggan kelolaPelanggan, KelolaLayanan kelolaLayanan, KelolaDiskon kelolaDiskon) {
        this.users = users;
        this.kelolaPelanggan = kelolaPelanggan;
        this.kelolaLayanan = kelolaLayanan;
        this.kelolaDiskon = kelolaDiskon;
        this.scanner = new Scanner(System.in);
        this.kelolaAkun = new KelolaAkun(users);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n===== MENU ADMIN =====");
            System.out.println("1. Kelola Pelanggan");
            System.out.println("2. Kelola Layanan");
            System.out.println("3. Kelola Diskon");
            System.out.println("4. Kelola Akun");
            System.out.println("0. Logout");
            System.out.print("Pilihan: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        kelolaPelanggan.menu();
                        break;
                    case 2:
                        kelolaLayanan.menu();
                        break;
                    case 3:
                        kelolaDiskon.menu();
                        break;
                    case 4:
                        kelolaAkun.menuAdmin();
                        break;
                    case 0:
                        System.out.println("Logout berhasil.");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (Exception e) {
                System.out.println("Input harus berupa angka!");
                scanner.nextLine();
            }
        }
    }
}