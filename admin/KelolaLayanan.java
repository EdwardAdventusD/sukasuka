package admin;

import java.util.ArrayList;
import java.util.Scanner;

public class KelolaLayanan {
    private ArrayList<Layanan> daftarLayanan = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public KelolaLayanan() {
        // Add default services
        daftarLayanan.add(new Layanan("Cuci Kering", 5500));
        daftarLayanan.add(new Layanan("Cuci Setrika", 7500));
        daftarLayanan.add(new Layanan("Setrika", 3500));
    }

    public static class Layanan {
        private String nama;
        private double hargaPerKg;

        public Layanan(String nama, double hargaPerKg) {
            this.nama = nama;
            this.hargaPerKg = hargaPerKg;
        }

        public String getNama() { return nama; }
        public double getHargaPerKg() { return hargaPerKg; }
        public void setNama(String nama) { this.nama = nama; }
        public void setHargaPerKg(double hargaPerKg) { this.hargaPerKg = hargaPerKg; }

        @Override
        public String toString() {
            return String.format("Nama: %-20s | Harga/kg: Rp%,.2f", nama, hargaPerKg);
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\n===== KELOLA LAYANAN =====");
            System.out.println("1. Tambah Layanan");
            System.out.println("2. Lihat Daftar Layanan");
            System.out.println("3. Update Layanan");
            System.out.println("4. Hapus Layanan");
            System.out.println("0. Kembali");
            System.out.print("Pilihan: ");

            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine();

                switch (pilihan) {
                    case 1:
                        tambahLayanan();
                        break;
                    case 2:
                        tampilkanLayanan();
                        break;
                    case 3:
                        updateLayanan();
                        break;
                    case 4:
                        hapusLayanan();
                        break;
                    case 0:
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

    private void tambahLayanan() {
        System.out.println("\n--- Tambah Layanan Baru ---");
        
        System.out.print("Masukkan Nama Layanan: ");
        String nama = scanner.nextLine().trim();
        
        if (nama.isEmpty()) {
            System.out.println("Nama layanan tidak boleh kosong!");
            return;
        }
        
        if (cariLayananByNama(nama) != null) {
            System.out.println("Nama layanan sudah terdaftar!");
            return;
        }
        
        System.out.print("Masukkan Harga per Kg: ");
        try {
            double harga = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            
            if (harga <= 0) {
                System.out.println("Harga harus lebih dari 0!");
                return;
            }
            
            daftarLayanan.add(new Layanan(nama, harga));
            System.out.println("Layanan berhasil ditambahkan!");
        } catch (Exception e) {
            System.out.println("Harga harus berupa angka!");
            scanner.nextLine(); // Clear invalid input
        }
    }

    private void tampilkanLayanan() {
        System.out.println("\n--- Daftar Layanan ---");
        if (daftarLayanan.isEmpty()) {
            System.out.println("Belum ada layanan yang terdaftar.");
            return;
        }
        
        System.out.println("=================================");
        for (Layanan layanan : daftarLayanan) {
            System.out.println(layanan);
        }
        System.out.println("=================================");
    }

    private void updateLayanan() {
        System.out.println("\n--- Update Layanan ---");
        System.out.print("Masukkan Nama Layanan yang akan diupdate: ");
        String namaLama = scanner.nextLine().trim();
        
        Layanan layanan = cariLayananByNama(namaLama);
        if (layanan == null) {
            System.out.println("Layanan tidak ditemukan!");
            return;
        }
        
        System.out.println("\nData saat ini:");
        System.out.println(layanan);
        
        System.out.print("\nMasukkan Nama Baru (kosongkan jika tidak ingin mengubah): ");
        String namaBaru = scanner.nextLine().trim();
        
        if (!namaBaru.isEmpty()) {
            // Cek jika nama baru sudah digunakan oleh layanan lain
            if (!namaBaru.equalsIgnoreCase(namaLama)) {
                if (cariLayananByNama(namaBaru) != null) {
                    System.out.println("Nama layanan sudah digunakan!");
                    return;
                }
                layanan.setNama(namaBaru);
            }
        }
        
        System.out.print("Masukkan Harga Baru (0 jika tidak ingin mengubah): ");
        try {
            double hargaBaru = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            
            if (hargaBaru > 0) {
                layanan.setHargaPerKg(hargaBaru);
            } else if (hargaBaru < 0) {
                System.out.println("Harga tidak boleh negatif!");
                return;
            }
            
            System.out.println("Layanan berhasil diupdate!");
        } catch (Exception e) {
            System.out.println("Harga harus berupa angka!");
            scanner.nextLine(); // Clear invalid input
        }
    }

    private void hapusLayanan() {
        System.out.println("\n--- Hapus Layanan ---");
        System.out.print("Masukkan Nama Layanan yang akan dihapus: ");
        String nama = scanner.nextLine().trim();
        
        Layanan layanan = cariLayananByNama(nama);
        if (layanan == null) {
            System.out.println("Layanan tidak ditemukan!");
            return;
        }
        
        daftarLayanan.remove(layanan);
        System.out.println("Layanan berhasil dihapus!");
    }

    private Layanan cariLayananByNama(String nama) {
        for (Layanan layanan : daftarLayanan) {
            if (layanan.getNama().equalsIgnoreCase(nama)) {
                return layanan;
            }
        }
        return null;
    }

    public ArrayList<Layanan> getDaftarLayanan() {
        return daftarLayanan;
    }
}