// MenuKasir.java
import java.util.Scanner;

public class MenuKasir {
    private Scanner scanner;

    public MenuKasir() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        // Tambahkan menu khusus kasir di sini
        System.out.println("\n===== MENU KASIR =====");
        System.out.println("1. Transaksi");
        System.out.println("2. Lihat Layanan");
        System.out.println("0. Logout");
        System.out.print("Pilihan: ");
        
        int pilihan = scanner.nextInt();
        scanner.nextLine();

        // Implementasi menu kasir
    }
}