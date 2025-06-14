package kasir;

import admin.KelolaPelanggan;
import admin.KelolaLayanan;
import admin.KelolaDiskon;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class MenuKasir {
    public static final String STATUS_MASUK = "Masuk";
    public static final String STATUS_DIPROSES = "Diproses";
    public static final String STATUS_SELESAI = "Selesai";
    public static final String STATUS_BELUM_LUNAS = "Belum Lunas";
    public static final String STATUS_LUNAS = "Lunas";

    private Scanner scanner;
    private KelolaPelanggan kelolaPelanggan;
    private KelolaLayanan kelolaLayanan;
    private KelolaDiskon kelolaDiskon;
    private TransaksiPelanggan transaksiPelanggan;

    public MenuKasir(KelolaPelanggan kelolaPelanggan, KelolaLayanan kelolaLayanan, KelolaDiskon kelolaDiskon) {
        this.scanner = new Scanner(System.in);
        this.kelolaPelanggan = kelolaPelanggan;
        this.kelolaLayanan = kelolaLayanan;
        this.kelolaDiskon = kelolaDiskon;
        this.transaksiPelanggan = new TransaksiPelanggan(kelolaPelanggan, kelolaLayanan, kelolaDiskon);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n===== MENU KASIR =====");
            System.out.println("1. Transaksi Baru");
            System.out.println("2. Lihat Riwayat Transaksi");
            System.out.println("3. Update Status Pesanan");
            System.out.println("0. Logout");
            System.out.print("Pilihan: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        transaksiPelanggan.prosesTransaksi();
                        break;
                    case 2:
                        tampilkanRiwayatTransaksi();
                        break;
                    case 3:
                        updateStatusPesanan();
                        break;
                    case 0:
                        System.out.println("Logout berhasil.");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.out.println("Input harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    private void tampilkanDaftarPelanggan() {
        System.out.println("\n--- Daftar Pelanggan ---");
        System.out.println("=============================================");
        List<KelolaPelanggan.Pelanggan> daftarPelanggan = kelolaPelanggan.getDaftarPelanggan();
        for (KelolaPelanggan.Pelanggan p : daftarPelanggan) {
            System.out.println(p);
        }
        System.out.println("=============================================");
    }

    private void tampilkanRiwayatTransaksi() {
        System.out.println("\n===== RIWAYAT TRANSAKSI =====");
        tampilkanDaftarPelanggan();
        
        System.out.print("Masukkan NIK Pelanggan: ");
        String nik = scanner.nextLine();
        
        kelolaPelanggan.tampilkanRiwayatTransaksi(nik);
    }

    private void updateStatusPesanan() {
        System.out.println("\n===== UPDATE STATUS PESANAN =====");
        tampilkanDaftarPelanggan();
        
        System.out.print("Masukkan NIK Pelanggan: ");
        String nik = scanner.nextLine();
        
        ArrayList<KelolaPelanggan.Pesanan> pesananList = kelolaPelanggan.getPesananByPelanggan(nik);
        if (pesananList.isEmpty()) {
            System.out.println("Tidak ada pesanan untuk pelanggan ini.");
            return;
        }
        
        System.out.println("\nDaftar Pesanan:");
        for (int i = 0; i < pesananList.size(); i++) {
            System.out.println((i+1) + ". " + pesananList.get(i));
        }
        
        System.out.print("\nPilih nomor pesanan yang akan diupdate: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        
        if (index < 1 || index > pesananList.size()) {
            System.out.println("Nomor pesanan tidak valid!");
            return;
        }
        
        KelolaPelanggan.Pesanan pesanan = pesananList.get(index-1);
        
        System.out.println("\nPilih yang akan diupdate:");
        System.out.println("1. Status Pesanan");
        System.out.println("2. Status Pembayaran");
        System.out.println("3. Keduanya");
        System.out.print("Pilihan: ");
        int pilihanUpdate = scanner.nextInt();
        scanner.nextLine();
        
        String statusPesananBaru = pesanan.getStatusPesanan();
        String statusPembayaranBaru = pesanan.getStatusPembayaran();
        
        if (pilihanUpdate == 1 || pilihanUpdate == 3) {
            System.out.println("\nPilih Status Pesanan Baru:");
            System.out.println("1. Masuk");
            System.out.println("2. Diproses");
            System.out.println("3. Selesai");
            System.out.print("Pilihan: ");
            int pilihanStatus = scanner.nextInt();
            scanner.nextLine();
            
            switch (pilihanStatus) {
                case 1:
                    statusPesananBaru = STATUS_MASUK;
                    break;
                case 2:
                    statusPesananBaru = STATUS_DIPROSES;
                    break;
                case 3:
                    statusPesananBaru = STATUS_SELESAI;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
                    return;
            }
        }
        
        if (pilihanUpdate == 2 || pilihanUpdate == 3) {
            System.out.println("\nPilih Status Pembayaran Baru:");
            System.out.println("1. Belum Lunas");
            System.out.println("2. Lunas");
            System.out.print("Pilihan: ");
            int pilihanBayar = scanner.nextInt();
            scanner.nextLine();
            
            switch (pilihanBayar) {
                case 1:
                    statusPembayaranBaru = STATUS_BELUM_LUNAS;
                    break;
                case 2:
                    statusPembayaranBaru = STATUS_LUNAS;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
                    return;
            }
        }
        
        pesanan.setStatusPesanan(statusPesananBaru);
        pesanan.setStatusPembayaran(statusPembayaranBaru);
        
        System.out.println("\nStatus berhasil diupdate:");
        System.out.println("Status Pesanan: " + statusPesananBaru);
        System.out.println("Status Pembayaran: " + statusPembayaranBaru);
    }
}