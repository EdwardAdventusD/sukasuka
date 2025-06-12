package admin;

import java.util.ArrayList;
import java.util.Scanner;

public class KelolaDiskon {
    private ArrayList<Diskon> daftarDiskon = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static class Diskon {
        private String nama;
        private double persentase;
        private int sisaPenggunaan;

        public Diskon(String nama, double persentase, int sisaPenggunaan) {
            this.nama = nama;
            this.persentase = persentase;
            this.sisaPenggunaan = sisaPenggunaan;
        }

        public String getNama() { return nama; }
        public double getPersentase() { return persentase; }
        public int getSisaPenggunaan() { return sisaPenggunaan; }

        public void setPersentase(double persentase) {
            this.persentase = persentase;
        }

        public void tambahKuota(int tambahan) {
            this.sisaPenggunaan += tambahan;
        }

        @Override
        public String toString() {
            return String.format("Nama: %-15s | Diskon: %5.1f%% | Sisa Kuota: %3d", 
                               nama, persentase, sisaPenggunaan);
        }
    }

    public KelolaDiskon() {
        // Tambahkan 2 diskon default
        daftarDiskon.add(new Diskon("Hari Raya", 15.0, 50));
        daftarDiskon.add(new Diskon("New Member", 10.0, 100));
    }

    public void menu() {
        while (true) {
            System.out.println("\n===== KELOLA DISKON =====");
            System.out.println("1. Tambah Diskon");
            System.out.println("2. Lihat Daftar Diskon");
            System.out.println("3. Update Diskon");
            System.out.println("4. Hapus Diskon");
            System.out.println("0. Kembali");
            System.out.print("Pilihan: ");

            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine();

                switch (pilihan) {
                    case 1:
                        tambahDiskon();
                        break;
                    case 2:
                        tampilkanDiskon();
                        break;
                    case 3:
                        updateDiskon();
                        break;
                    case 4:
                        hapusDiskon();
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

    private void tambahDiskon() {
        System.out.println("\n--- Tambah Diskon Baru ---");
        
        System.out.print("Masukkan Nama Diskon: ");
        String nama = scanner.nextLine().trim();
        
        if (nama.isEmpty()) {
            System.out.println("Nama diskon tidak boleh kosong!");
            return;
        }
        
        if (cariDiskonByNama(nama) != null) {
            System.out.println("Diskon dengan nama tersebut sudah ada!");
            return;
        }
        
        System.out.print("Masukkan Persentase Diskon (%): ");
        double persentase = scanner.nextDouble();
        
        System.out.print("Masukkan Kuota Penggunaan: ");
        int kuota = scanner.nextInt();
        scanner.nextLine();
        
        if (persentase <= 0 || persentase > 100) {
            System.out.println("Persentase diskon harus antara 0-100%!");
            return;
        }
        
        if (kuota <= 0) {
            System.out.println("Kuota harus lebih dari 0!");
            return;
        }
        
        daftarDiskon.add(new Diskon(nama, persentase, kuota));
        System.out.println("Diskon berhasil ditambahkan!");
    }

    private void tampilkanDiskon() {
        System.out.println("\n--- Daftar Diskon ---");
        if (daftarDiskon.isEmpty()) {
            System.out.println("Belum ada diskon yang terdaftar.");
            return;
        }
        
        System.out.println("===========================================");
        for (Diskon diskon : daftarDiskon) {
            System.out.println(diskon);
        }
        System.out.println("===========================================");
    }

    private void updateDiskon() {
        System.out.println("\n--- Update Diskon ---");
        System.out.print("Masukkan Nama Diskon yang akan diupdate: ");
        String nama = scanner.nextLine().trim();
        
        Diskon diskon = cariDiskonByNama(nama);
        if (diskon == null) {
            System.out.println("Diskon tidak ditemukan!");
            return;
        }
        
        System.out.println("\nData saat ini:");
        System.out.println(diskon);
        
        System.out.println("\nPilihan Update:");
        System.out.println("1. Ubah Persentase");
        System.out.println("2. Tambah Kuota");
        System.out.print("Pilihan: ");
        
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        switch (pilihan) {
            case 1:
                System.out.print("Masukkan Persentase Baru: ");
                double persentaseBaru = scanner.nextDouble();
                scanner.nextLine();
                
                if (persentaseBaru <= 0 || persentaseBaru > 100) {
                    System.out.println("Persentase harus antara 0-100%!");
                    return;
                }
                diskon.setPersentase(persentaseBaru);
                System.out.println("Persentase diskon berhasil diupdate!");
                break;
                
            case 2:
                System.out.print("Masukkan Jumlah Kuota Tambahan: ");
                int tambahan = scanner.nextInt();
                scanner.nextLine();
                
                if (tambahan <= 0) {
                    System.out.println("Kuota tambahan harus lebih dari 0!");
                    return;
                }
                diskon.tambahKuota(tambahan);
                System.out.println("Kuota diskon berhasil ditambah!");
                break;
                
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    private void hapusDiskon() {
        System.out.println("\n--- Hapus Diskon ---");
        System.out.print("Masukkan Nama Diskon yang akan dihapus: ");
        String nama = scanner.nextLine().trim();
        
        Diskon diskon = cariDiskonByNama(nama);
        if (diskon == null) {
            System.out.println("Diskon tidak ditemukan!");
            return;
        }
        
        daftarDiskon.remove(diskon);
        System.out.println("Diskon berhasil dihapus!");
    }

    private Diskon cariDiskonByNama(String nama) {
        for (Diskon diskon : daftarDiskon) {
            if (diskon.getNama().equalsIgnoreCase(nama)) {
                return diskon;
            }
        }
        return null;
    }

    public ArrayList<Diskon> getDaftarDiskon() {
        return daftarDiskon;
    }
}