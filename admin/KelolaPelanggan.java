package admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KelolaPelanggan {
    private List<Pelanggan> daftarPelanggan = new ArrayList<>();
    private ArrayList<Pesanan> daftarPesanan = new ArrayList<>();
    private int lastOrderId = 0;
    private Scanner scanner = new Scanner(System.in);

    public static class Pelanggan {
        private String nik;
        private String nama;
        private String nomorTelepon;

        public Pelanggan(String nik, String nama, String nomorTelepon) {
            this.nik = nik;
            this.nama = nama;
            this.nomorTelepon = nomorTelepon;
        }

        public String getNik() { return nik; }
        public String getNama() { return nama; }
        public String getNomorTelepon() { return nomorTelepon; }
        public void setNama(String nama) { this.nama = nama; }
        public void setNomorTelepon(String nomorTelepon) { this.nomorTelepon = nomorTelepon; }

        @Override
        public String toString() {
            return String.format("NIK: %s | Nama: %-20s | Telp: %s", 
                               nik, nama, nomorTelepon);
        }
    }

    public static class Pesanan {
        private String idPesanan;
        private String nikPelanggan;
        private String namaLayanan;
        private double berat;
        private double hargaPerKg;
        private String namaDiskon;
        private double persentaseDiskon;
        private double total;
        private String statusPembayaran;
        private String statusPesanan;
        private String tanggalMasuk;

        public Pesanan(String idPesanan, String nikPelanggan, String namaLayanan, 
                      double berat, double hargaPerKg, String namaDiskon, 
                      double persentaseDiskon, double total, 
                      String statusPembayaran, String statusPesanan) {
            this.idPesanan = idPesanan;
            this.nikPelanggan = nikPelanggan;
            this.namaLayanan = namaLayanan;
            this.berat = berat;
            this.hargaPerKg = hargaPerKg;
            this.namaDiskon = namaDiskon;
            this.persentaseDiskon = persentaseDiskon;
            this.total = total;
            this.statusPembayaran = statusPembayaran;
            this.statusPesanan = statusPesanan;
            this.tanggalMasuk = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        }

        public String getIdPesanan() { return idPesanan; }
        public String getNikPelanggan() { return nikPelanggan; }
        public String getNamaLayanan() { return namaLayanan; }
        public double getBerat() { return berat; }
        public double getHargaPerKg() { return hargaPerKg; }
        public String getNamaDiskon() { return namaDiskon; }
        public double getPersentaseDiskon() { return persentaseDiskon; }
        public double getTotal() { return total; }
        public String getStatusPembayaran() { return statusPembayaran; }
        public String getStatusPesanan() { return statusPesanan; }
        public String getTanggalMasuk() { return tanggalMasuk; }

        public void setStatusPembayaran(String status) { this.statusPembayaran = status; }
        public void setStatusPesanan(String status) { this.statusPesanan = status; }

        @Override
        public String toString() {
            return String.format("ID: %s | Layanan: %-15s | Berat: %.2fkg | Total: Rp%.2f | Pembayaran: %-12s | Status: %-8s | Tanggal: %s",
                               idPesanan, namaLayanan, berat, total, statusPembayaran, statusPesanan, tanggalMasuk);
        }
    }

    public KelolaPelanggan() {
        daftarPelanggan.add(new Pelanggan("123", "Cinta Lestari", "0812345"));
        daftarPelanggan.add(new Pelanggan("345", "Budiono", "081321"));
        daftarPelanggan.add(new Pelanggan("567", "Citra Dewi", "08123123"));
    }

    public void menu() {
        while (true) {
            System.out.println("\n===== KELOLA PELANGGAN =====");
            System.out.println("1. Tambah Pelanggan");
            System.out.println("2. Lihat Daftar Pelanggan");
            System.out.println("3. Update Pelanggan");
            System.out.println("4. Hapus Pelanggan");
            System.out.println("5. Cari Pelanggan");
            System.out.println("6. Lihat Daftar Pesanan");
            System.out.println("0. Kembali");
            System.out.print("Pilihan: ");

            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine();

                switch (pilihan) {
                    case 1: tambahPelanggan(); break;
                    case 2: tampilkanPelanggan(); break;
                    case 3: updatePelanggan(); break;
                    case 4: hapusPelanggan(); break;
                    case 5: cariPelanggan(); break;
                    case 6: tampilkanSemuaPesanan(); break;
                    case 0: return;
                    default: System.out.println("Pilihan tidak valid!");
                }
            } catch (Exception e) {
                System.out.println("Input harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    private void tambahPelanggan() {
        System.out.println("\n--- Tambah Pelanggan Baru ---");
        
        System.out.print("Masukkan NIK: ");
        String nik = scanner.nextLine().trim();
        
        if (cariPelangganByNik(nik) != null) {
            System.out.println("Pelanggan dengan NIK tersebut sudah terdaftar!");
            return;
        }
        
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine().trim();
        
        System.out.print("Masukkan Nomor Telepon: ");
        String telp = scanner.nextLine().trim();
        
        if (nik.isEmpty() || nama.isEmpty() || telp.isEmpty()) {
            System.out.println("Semua data harus diisi!");
            return;
        }
        
        if (!telp.matches("\\d+")) {
            System.out.println("Nomor telepon harus angka!");
            return;
        }
        
        daftarPelanggan.add(new Pelanggan(nik, nama, telp));
        System.out.println("Pelanggan berhasil ditambahkan!");
    }

    public void tampilkanPelanggan() {
        System.out.println("\n--- Daftar Pelanggan ---");
        if (daftarPelanggan.isEmpty()) {
            System.out.println("Belum ada pelanggan yang terdaftar.");
            return;
        }
        
        System.out.println("=============================================");
        for (Pelanggan p : daftarPelanggan) {
            System.out.println(p);
        }
        System.out.println("=============================================");
    }

    private void updatePelanggan() {
        System.out.println("\n--- Update Data Pelanggan ---");
        System.out.print("Masukkan NIK pelanggan yang akan diupdate: ");
        String nik = scanner.nextLine().trim();
        
        Pelanggan pelanggan = cariPelangganByNik(nik);
        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan!");
            return;
        }
        
        System.out.println("\nData saat ini:");
        System.out.println(pelanggan);
        
        System.out.print("\nMasukkan Nama Baru (kosongkan jika tidak ingin mengubah): ");
        String namaBaru = scanner.nextLine().trim();
        
        System.out.print("Masukkan Nomor Telepon Baru (kosongkan jika tidak ingin mengubah): ");
        String telpBaru = scanner.nextLine().trim();
        
        if (!namaBaru.isEmpty()) pelanggan.setNama(namaBaru);
        if (!telpBaru.isEmpty()) {
            if (!telpBaru.matches("\\d+")) {
                System.out.println("Nomor telepon harus angka!");
                return;
            }
            pelanggan.setNomorTelepon(telpBaru);
        }
        
        System.out.println("Data pelanggan berhasil diupdate!");
    }

    private void hapusPelanggan() {
        System.out.println("\n--- Hapus Pelanggan ---");
        System.out.print("Masukkan NIK pelanggan yang akan dihapus: ");
        String nik = scanner.nextLine().trim();
        
        Pelanggan pelanggan = cariPelangganByNik(nik);
        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan!");
            return;
        }
        
        daftarPelanggan.remove(pelanggan);
        System.out.println("Pelanggan berhasil dihapus!");
    }

    private void cariPelanggan() {
        System.out.println("\n--- Cari Pelanggan ---");
        System.out.println("1. Cari berdasarkan NIK");
        System.out.println("2. Cari berdasarkan Nama");
        System.out.print("Pilihan: ");
        
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        switch (pilihan) {
            case 1:
                System.out.print("Masukkan NIK: ");
                String nik = scanner.nextLine().trim();
                Pelanggan pByNik = cariPelangganByNik(nik);
                if (pByNik != null) {
                    System.out.println("\nHasil pencarian:");
                    System.out.println(pByNik);
                } else {
                    System.out.println("Pelanggan tidak ditemukan!");
                }
                break;
            case 2:
                System.out.print("Masukkan Nama: ");
                String nama = scanner.nextLine().trim();
                ArrayList<Pelanggan> hasil = cariPelangganByNama(nama);
                if (!hasil.isEmpty()) {
                    System.out.println("\nHasil pencarian:");
                    for (Pelanggan p : hasil) {
                        System.out.println(p);
                    }
                } else {
                    System.out.println("Pelanggan tidak ditemukan!");
                }
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    public Pelanggan cariPelangganByNik(String nik) {
        for (Pelanggan p : daftarPelanggan) {
            if (p.getNik().equalsIgnoreCase(nik)) {
                return p;
            }
        }
        return null;
    }

    private ArrayList<Pelanggan> cariPelangganByNama(String nama) {
        ArrayList<Pelanggan> hasil = new ArrayList<>();
        for (Pelanggan p : daftarPelanggan) {
            if (p.getNama().toLowerCase().contains(nama.toLowerCase())) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public void tambahPesanan(String nikPelanggan, String namaLayanan, double berat, 
                            double hargaPerKg, String namaDiskon, double persentaseDiskon,
                            double total, String statusPembayaran, String statusPesanan) {
        String idPesanan = "ORD" + (++lastOrderId);
        Pesanan pesanan = new Pesanan(idPesanan, nikPelanggan, namaLayanan, berat, 
                                    hargaPerKg, namaDiskon, persentaseDiskon, 
                                    total, statusPembayaran, statusPesanan);
        daftarPesanan.add(pesanan);
    }

    public ArrayList<Pesanan> getPesananByPelanggan(String nik) {
        ArrayList<Pesanan> result = new ArrayList<>();
        for (Pesanan p : daftarPesanan) {
            if (p.getNikPelanggan().equals(nik)) {
                result.add(p);
            }
        }
        return result;
    }

    public void tampilkanSemuaPesanan() {
        System.out.println("\n--- Daftar Semua Pesanan ---");
        if (daftarPesanan.isEmpty()) {
            System.out.println("Belum ada pesanan yang tercatat.");
            return;
        }
        
        System.out.println("==================================================================================================");
        for (Pesanan p : daftarPesanan) {
            System.out.println(p);
        }
        System.out.println("==================================================================================================");
    }

    public void tampilkanRiwayatTransaksi(String nik) {
        ArrayList<Pesanan> pesananPelanggan = getPesananByPelanggan(nik);
        if (pesananPelanggan.isEmpty()) {
            System.out.println("Tidak ada riwayat transaksi untuk pelanggan ini.");
            return;
        }
        
        System.out.println("\n--- Riwayat Transaksi ---");
        for (Pesanan p : pesananPelanggan) {
            System.out.println(p);
        }
    }

    public boolean updateStatusPesanan(String nik, int index, String statusBaru) {
        ArrayList<Pesanan> pesananPelanggan = getPesananByPelanggan(nik);
        if (index < 0 || index >= pesananPelanggan.size()) {
            return false;
        }
        
        pesananPelanggan.get(index).setStatusPesanan(statusBaru);
        return true;
    }

    public List<Pelanggan> getDaftarPelanggan() {
        return daftarPelanggan;
    }
}