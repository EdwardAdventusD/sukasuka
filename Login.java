import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    private boolean isAppEnd = false;
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<String> passwords = new ArrayList<>();
    // private int idAuthUser = -1;

    public void run() {
        Scanner input = new Scanner(System.in);

        while (!isAppEnd) {
            System.out.println("\n===== APLIKASI KELOMPOK SUKA SUKA =====");
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.println("0. Keluar");
            System.out.print("Masukkan Pilihan Anda: ");
            int pilihan = input.nextInt();

            switch (pilihan) {
                case 0:
                    isAppEnd = true;
                    break;
                case 1:
                    System.out.print("Masukkan Username Anda: ");
                    String usernameLogin = input.next();
                    System.out.print("Masukkan Password Anda: ");
                    String passwordLogin = input.next();
                    if (usernames.contains(usernameLogin)) {
                        int index = usernames.indexOf(usernameLogin);
                        if (passwords.get(index).equals(passwordLogin)) {
                            // idAuthUser = index;
                            System.out.println("Login Berhasil! Selamat datang, " + usernameLogin + ".");
                        } else {
                            System.out.println("Password Salah. Silakan Coba Lagi.");
                        }
                    } else {
                        System.out.println("Username Tidak Ditemukan. Silakan Coba Lagi.");
                    }
                    break;
                case 2:
                    System.out.print("Masukkan Username Baru Anda: ");
                    String usernameBaru = input.next();
                    if (usernames.contains(usernameBaru)) {
                        System.out.println("Username Sudah Terdaftar. Silakan Gunakan Username Lain.");
                        break;
                    }
                    System.out.print("Masukkan Password Baru Anda: ");
                    String passwordBaru = input.next();
                    usernames.add(usernameBaru);
                    passwords.add(passwordBaru);
                    System.out.println("Akun Baru Berhasil Dibuat! Silakan Login Kembali.");
                    break;
                default:
                    System.out.println("Pilihan Tidak Valid. Silakan Coba Lagi.");
                    break;
            }
        }
        System.out.println("Terima Kasih!");
    }

    public static void main(String[] args) {
        Login app = new Login();
        app.run();
    }
}
