package kasir;

import admin.KelolaPelanggan;
import admin.KelolaLayanan;
import admin.KelolaDiskon;
import java.util.Scanner;

public class TransaksiPelanggan {
    public static final String STATUS_MASUK = "Masuk";
    public static final String STATUS_DIPROSES = "Diproses";
    public static final String STATUS_SELESAI = "Selesai";
    
    private final KelolaPelanggan kelolaPelanggan;
    private final KelolaLayanan kelolaLayanan;
    private final KelolaDiskon kelolaDiskon;
    private final Scanner scanner;

    public TransaksiPelanggan(KelolaPelanggan kelolaPelanggan, KelolaLayanan kelolaLayanan, KelolaDiskon kelolaDiskon) {
        this.kelolaPelanggan = kelolaPelanggan;
        this.kelolaLayanan = kelolaLayanan;
        this.kelolaDiskon = kelolaDiskon;
        this.scanner = new Scanner(System.in);
    }

    public void prosesTransaksi() {
        System.out.println("\n===== PROSES TRANSAKSI =====");
        
        kelolaPelanggan.tampilkanPelanggan();
        KelolaPelanggan.Pelanggan pelanggan = pilihPelanggan();
        if (pelanggan == null) return;
        
        KelolaLayanan.Layanan layanan = pilihLayanan();
        if (layanan == null) return;
        
        double berat = inputBerat();
        if (berat <= 0) return;
        
        KelolaDiskon.Diskon diskon = pilihDiskon();
        String statusPembayaran = tentukanStatusPembayaran();
        
        double[] hasilPerhitungan = hitungTotalHarga(layanan, berat, diskon);
        double totalHarga = hasilPerhitungan[0];
        double potonganDiskon = hasilPerhitungan[1];
        
        tampilkanDetailTransaksi(pelanggan, layanan, berat, diskon, potonganDiskon, 
                               totalHarga, statusPembayaran, STATUS_MASUK);
        
        simpanTransaksi(pelanggan, layanan, berat, diskon, totalHarga, statusPembayaran);
        
        System.out.println("\nTransaksi berhasil diproses!");
    }

    private KelolaPelanggan.Pelanggan pilihPelanggan() {
        System.out.print("\nMasukkan NIK Pelanggan: ");
        String nik = scanner.nextLine().trim();
        
        if (nik.isEmpty()) {
            System.out.println("NIK tidak boleh kosong!");
            return null;
        }
        
        KelolaPelanggan.Pelanggan pelanggan = kelolaPelanggan.cariPelangganByNik(nik);
        
        if (pelanggan != null) {
            return pelanggan;
        }
        
        System.out.println("Pelanggan dengan NIK " + nik + " tidak ditemukan!");
        System.out.print("Apakah Anda ingin mendaftarkan pelanggan baru? (y/n): ");
        String pilihan = scanner.nextLine().trim().toLowerCase();
        
        if (!pilihan.equals("y")) {
            return null;
        }
        
        return buatPelangganBaru(nik);
    }

    private KelolaPelanggan.Pelanggan buatPelangganBaru(String nik) {
        System.out.println("\n--- PENDAFTARAN PELANGGAN BARU ---");
        System.out.print("Masukkan Nama Pelanggan: ");
        String nama = scanner.nextLine().trim();
        
        System.out.print("Masukkan Nomor Telepon: ");
        String telepon = scanner.nextLine().trim();
        
        if (nama.isEmpty() || telepon.isEmpty()) {
            System.out.println("Nama dan telepon tidak boleh kosong!");
            return null;
        }
        
        if (!telepon.matches("\\d+")) {
            System.out.println("Nomor telepon harus berupa angka!");
            return null;
        }
        
        KelolaPelanggan.Pelanggan pelangganBaru = new KelolaPelanggan.Pelanggan(nik, nama, telepon);
        kelolaPelanggan.getDaftarPelanggan().add(pelangganBaru);
        
        System.out.println("\nData Pelanggan:");
        System.out.println("NIK: " + pelangganBaru.getNik());
        System.out.println("Nama: " + pelangganBaru.getNama());
        System.out.println("Telepon: " + pelangganBaru.getNomorTelepon());
        System.out.println("Pelanggan baru berhasil terdaftar!");
        
        return pelangganBaru;
    }

    private KelolaLayanan.Layanan pilihLayanan() {
        System.out.println("\n--- DAFTAR LAYANAN ---");
        int nomorUrut = 1;
        for (KelolaLayanan.Layanan layanan : kelolaLayanan.getDaftarLayanan()) {
            System.out.println(nomorUrut++ + ". " + layanan.getNama() + " - Rp" + layanan.getHargaPerKg() + "/kg");
        }
        
        System.out.print("\nPilih Layanan (nomor): ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        if (pilihan < 1 || pilihan > kelolaLayanan.getDaftarLayanan().size()) {
            System.out.println("Pilihan layanan tidak valid!");
            return null;
        }
        
        return kelolaLayanan.getDaftarLayanan().get(pilihan - 1);
    }

    private double inputBerat() {
        System.out.print("Masukkan Berat Cucian (kg): ");
        double berat = scanner.nextDouble();
        scanner.nextLine();
        
        if (berat <= 0) {
            System.out.println("Berat harus lebih dari 0 kg!");
            return -1;
        }
        
        return berat;
    }

    private KelolaDiskon.Diskon pilihDiskon() {
        if (kelolaDiskon.getDaftarDiskon().isEmpty()) {
            System.out.println("\nTidak ada diskon yang tersedia saat ini.");
            return null;
        }
        
        System.out.println("\n--- DAFTAR DISKON ---");
        int nomorUrut = 1;
        for (KelolaDiskon.Diskon diskon : kelolaDiskon.getDaftarDiskon()) {
            System.out.println(nomorUrut++ + ". " + diskon.getNama() + " - " + diskon.getPersentase() + 
                             "% (Kuota tersisa: " + diskon.getSisaPenggunaan() + ")");
        }
        
        System.out.print("\nPilih Diskon (nomor, 0 untuk tidak menggunakan diskon): ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        if (pilihan == 0) {
            return null;
        }
        
        if (pilihan < 1 || pilihan > kelolaDiskon.getDaftarDiskon().size()) {
            System.out.println("Pilihan diskon tidak valid!");
            return null;
        }
        
        KelolaDiskon.Diskon diskonTerpilih = kelolaDiskon.getDaftarDiskon().get(pilihan - 1);
        
        if (diskonTerpilih.getSisaPenggunaan() <= 0) {
            System.out.println("Kuota diskon ini telah habis!");
            return null;
        }
        
        return diskonTerpilih;
    }

    private double[] hitungTotalHarga(KelolaLayanan.Layanan layanan, double berat, KelolaDiskon.Diskon diskon) {
        double subtotal = berat * layanan.getHargaPerKg();
        double potongan = 0;
        
        if (diskon != null) {
            potongan = subtotal * (diskon.getPersentase() / 100);
            subtotal -= potongan;
            diskon.tambahKuota(-1);
        }
        
        return new double[]{subtotal, potongan};
    }

    private String tentukanStatusPembayaran() {
        System.out.print("\nStatus Pembayaran (1. Lunas / 2. Belum Lunas): ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        while (pilihan != 1 && pilihan != 2) {
            System.out.println("Pilihan tidak valid!");
            System.out.print("Status Pembayaran (1. Lunas / 2. Belum Lunas): ");
            pilihan = scanner.nextInt();
            scanner.nextLine();
        }
        
        return pilihan == 1 ? "Lunas" : "Belum Lunas";
    }

    private void tampilkanDetailTransaksi(KelolaPelanggan.Pelanggan pelanggan, 
                                        KelolaLayanan.Layanan layanan, 
                                        double berat, 
                                        KelolaDiskon.Diskon diskon, 
                                        double potongan, 
                                        double total,
                                        String statusPembayaran,
                                        String statusPesanan) {
        System.out.println("\n===== DETAIL TRANSAKSI =====");
        System.out.println("Pelanggan: " + pelanggan.getNama() + " (" + pelanggan.getNik() + ")");
        System.out.println("Telepon: " + pelanggan.getNomorTelepon());
        System.out.println("Layanan: " + layanan.getNama() + " @Rp" + layanan.getHargaPerKg() + "/kg");
        System.out.println("Berat: " + berat + " kg");
        
        if (diskon != null) {
            System.out.println("Diskon: " + diskon.getNama() + " (" + diskon.getPersentase() + "%)");
            System.out.println("Potongan: Rp" + potongan);
        } else {
            System.out.println("Diskon: -");
        }
        
        System.out.println("TOTAL HARGA: Rp" + total);
        System.out.println("Status Pembayaran: " + statusPembayaran);
        System.out.println("Status Pesanan: " + statusPesanan);
        System.out.println("============================");
    }

    private void simpanTransaksi(KelolaPelanggan.Pelanggan pelanggan,
                               KelolaLayanan.Layanan layanan,
                               double berat,
                               KelolaDiskon.Diskon diskon,
                               double total,
                               String statusPembayaran) {
        String namaDiskon = diskon != null ? diskon.getNama() : null;
        double persentaseDiskon = diskon != null ? diskon.getPersentase() : 0;
        
        kelolaPelanggan.tambahPesanan(
            pelanggan.getNik(),
            layanan.getNama(),
            berat,
            layanan.getHargaPerKg(),
            namaDiskon,
            persentaseDiskon,
            total,
            statusPembayaran,
            STATUS_MASUK
        );
    }
}