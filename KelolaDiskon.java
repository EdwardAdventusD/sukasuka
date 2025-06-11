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

        // Getter methods
        public String getNama() {
            return nama;
        }

        public double getPersentase() {
            return persentase;
        }

        public int getSisaPenggunaan() {
            return sisaPenggunaan;
        }

        // Method untuk menggunakan diskon
        public boolean gunakanDiskon() {
            if (sisaPenggunaan > 0) {
                sisaPenggunaan--;
                return true;
            }
            return false;
        }

        // Setter methods
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

    public static void main(String[] args) {
        KelolaDiskon app = new KelolaDiskon();
        app.menu();
    }

    public void menu() {
        while (true) {
            System.out.println("\n===== KELOLA DISKON =====");
            System.out.println("1. Tambah Diskon");
            System.out.println("2. Lihat Daftar Diskon");
            System.out.println("3. Update Diskon");
            System.out.println("4. Hapus Diskon");
            System.out.println("5. Gunakan Diskon");
            System.out.println("6. Tambah Kuota Diskon");
            System.out.println("0. Keluar");
            System.out.print("Pilihan: ");

            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine(); // Consume newline

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
                    case 5:
                        gunakanDiskon();
                        break;
                    case 6:
                        tambahKuotaDiskon();
                        break;
                    case 0:
                        System.out.println("Terima kasih!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (Exception e) {
                System.out.println("Input harus berupa angka!");
                scanner.nextLine(); // Clear invalid input
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
        scanner.nextLine(); // Consume newline
        
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
        
        System.out.print("\nMasukkan Persentase Baru (0 jika tidak ingin mengubah): ");
        double persentaseBaru = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        if (persentaseBaru > 0) {
            if (persentaseBaru > 100) {
                System.out.println("Persentase tidak boleh lebih dari 100%!");
                return;
            }
            diskon.setPersentase(persentaseBaru);
        }
        
        System.out.println("Diskon berhasil diupdate!");
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

    private void gunakanDiskon() {
        System.out.println("\n--- Gunakan Diskon ---");
        System.out.print("Masukkan Nama Diskon yang akan digunakan: ");
        String nama = scanner.nextLine().trim();
        
        Diskon diskon = cariDiskonByNama(nama);
        if (diskon == null) {
            System.out.println("Diskon tidak ditemukan!");
            return;
        }
        
        if (diskon.gunakanDiskon()) {
            System.out.printf("Diskon %s (%.1f%%) berhasil digunakan! Sisa kuota: %d\n",
                            diskon.getNama(), diskon.getPersentase(), diskon.getSisaPenggunaan());
        } else {
            System.out.println("Kuota diskon sudah habis!");
        }
    }

    private void tambahKuotaDiskon() {
        System.out.println("\n--- Tambah Kuota Diskon ---");
        System.out.print("Masukkan Nama Diskon: ");
        String nama = scanner.nextLine().trim();
        
        Diskon diskon = cariDiskonByNama(nama);
        if (diskon == null) {
            System.out.println("Diskon tidak ditemukan!");
            return;
        }
        
        System.out.print("Masukkan Jumlah Kuota Tambahan: ");
        int tambahan = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (tambahan <= 0) {
            System.out.println("Kuota tambahan harus lebih dari 0!");
            return;
        }
        
        diskon.tambahKuota(tambahan);
        System.out.printf("Kuota diskon %s berhasil ditambah %d. Total kuota sekarang: %d\n",
                         nama, tambahan, diskon.getSisaPenggunaan());
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