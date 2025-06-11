import java.util.*;
import java.time.LocalDate;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();
    static HashMap<String, Pelanggan> dataPelanggan = new HashMap<>();
    static ArrayList<LayananLaundry> daftarLayanan = new ArrayList<>();
    static ArrayList<Kasir> daftarUser = new ArrayList<>();
    static int counterTransaksi = 1;

    public static void main(String[] args) {
        daftarUser.add(new Kasir("kasir", "kasir"));
        daftarLayanan.add(new LayananLaundry("L001", "Cuci Kering", 5000));
        daftarLayanan.add(new LayananLaundry("L002", "Cuci Setrika", 7000));
        daftarLayanan.add(new LayananLaundry("L003", "Setrika Saja", 4000));

        while (true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            boolean loginBerhasil = false;
            for (Kasir kasir : daftarUser) {
                if (kasir.login(username, password)) {
                    menuKasir(kasir);
                    loginBerhasil = true;
                    break;
                }
            }

            if (!loginBerhasil) {
                System.out.println("Login gagal! Coba lagi.\n");
            }
        }
    }

    static String generateIdTransaksi() {
        return String.format("TR%03d", counterTransaksi++);
    }

    static void menuKasir(Kasir kasir) {
        while (true) {
            System.out.println("\nMenu Kasir:");
            System.out.println("1. Tambah Transaksi");
            System.out.println("2. Tampilkan Semua Transaksi");
            System.out.println("3. Ubah Status Pesanan");
            System.out.println("4. Lihat Status Transaksi");
            System.out.println("5. Logout");
            System.out.print("Pilih: ");
            int pilih = Integer.parseInt(scanner.nextLine());

            switch (pilih) {
                case 1 -> tambahTransaksi();
                case 2 -> tampilkanTransaksi();
                case 3 -> ubahStatusPesanan();
                case 4 -> lihatStatusTransaksi();
                case 5 -> {
                    System.out.println("Logout berhasil.\n");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static void tambahTransaksi() {
        String idTransaksi = generateIdTransaksi();
        System.out.print("NIK Pelanggan: ");
        String nik = scanner.nextLine();

        Pelanggan pelanggan = dataPelanggan.getOrDefault(nik, null);
        if (pelanggan == null) {
            System.out.print("Nama: ");
            String nama = scanner.nextLine();
            System.out.print("No HP: ");
            String hp = scanner.nextLine();
            pelanggan = new Pelanggan(nik, nama, hp);
            dataPelanggan.put(nik, pelanggan);
        } else {
            System.out.println("Data ditemukan: " + pelanggan.getNama());
        }

        System.out.println("Pilih Layanan:");
        for (int i = 0; i < daftarLayanan.size(); i++) {
            LayananLaundry l = daftarLayanan.get(i);
            System.out.printf("%d. %s - Rp%.2f/kg\n", i + 1, l.getNama(), l.getHargaPerKg());
        }
        System.out.print("Masukkan nomor layanan: ");
        int pilihan = Integer.parseInt(scanner.nextLine());
        LayananLaundry layanan = daftarLayanan.get(pilihan - 1);

        System.out.print("Berat (kg): ");
        double berat = Double.parseDouble(scanner.nextLine());
        double total = layanan.getHargaPerKg() * berat;

        LocalDate today = LocalDate.now();
        boolean dapatDiskon = today.getDayOfMonth() == 25;
        double diskon = dapatDiskon ? 0.10 * total : 0.0;
        double totalBayar = total - diskon;

        Transaksi trx = new Transaksi(idTransaksi, nik, layanan.getIdLayanan(), berat, totalBayar);
        daftarTransaksi.add(trx);

        System.out.println("\n===== NOTA TRANSAKSI =====");
        System.out.println("ID Transaksi : " + idTransaksi);
        System.out.println("Nama Pelanggan : " + pelanggan.getNama());
        System.out.println("Layanan : " + layanan.getNama());
        System.out.println("Berat : " + berat + " kg");
        System.out.printf("Total : Rp%.2f\n", total);
        if (dapatDiskon) {
            System.out.printf("Diskon 10%% : -Rp%.2f\n", diskon);
        }
        System.out.printf("Total Bayar : Rp%.2f\n", totalBayar);
        System.out.println("===========================\n");
    }

    static void tampilkanTransaksi() {
        for (Transaksi t : daftarTransaksi) {
            System.out.println(t);
        }
    }

    static void ubahStatusPesanan() {
        System.out.print("Masukkan ID Transaksi: ");
        String id = scanner.nextLine();
        for (Transaksi t : daftarTransaksi) {
            if (t.getIdTransaksi().equals(id)) {
                System.out.print("Status baru (proses / selesai / diambil): ");
                String status = scanner.nextLine();
                t.setStatus(status);
                System.out.println("Status pesanan diperbarui menjadi: " + status);
                return;
            }
        }
        System.out.println("Transaksi tidak ditemukan.");
    }

    static void lihatStatusTransaksi() {
        System.out.print("Masukkan ID Transaksi: ");
        String id = scanner.nextLine();
        for (Transaksi t : daftarTransaksi) {
            if (t.getIdTransaksi().equals(id)) {
                System.out.println("Status Pesanan: " + t.getStatus());
                return;
            }
        }
        System.out.println("Transaksi tidak ditemukan.");
    }
}
