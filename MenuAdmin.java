// MenuAdmin.java
import java.util.ArrayList;
import java.util.Scanner;

public class MenuAdmin {
    private Scanner scanner;
    private ArrayList<User> daftarUser;
    private ArrayList<Layanan> daftarLayanan;
    private ArrayList<Diskon> daftarDiskon;

    public MenuAdmin(ArrayList<User> users, 
                   ArrayList<Layanan> layanan,
                   ArrayList<Diskon> diskon) {
        this.scanner = new Scanner(System.in);
        this.daftarUser = users;
        this.daftarLayanan = layanan;
        this.daftarDiskon = diskon;
    }

    public void showMenu() {
        KelolaAkun kelolaAkun = new KelolaAkun(daftarUser);
        KelolaLayanan kelolaLayanan = new KelolaLayanan();
        KelolaDiskon kelolaDiskon = new KelolaDiskon();

        while (true) {
            System.out.println("\n===== MENU ADMIN =====");
            System.out.println("1. Kelola Akun");
            System.out.println("2. Kelola Layanan");
            System.out.println("3. Kelola Diskon");
            System.out.println("0. Logout");
            System.out.print("Pilihan: ");
            
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    kelolaAkun.menuAdmin();
                    break;
                case 2:
                    kelolaLayanan.menu();
                    break;
                case 3:
                    kelolaDiskon.menu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
}