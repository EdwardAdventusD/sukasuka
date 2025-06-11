class Transaksi {
    private String idTransaksi, idPelanggan, idLayanan, status = "proses";
    private double berat, total;

    public Transaksi(String idTransaksi, String idPelanggan, String idLayanan, double berat, double total) {
        this.idTransaksi = idTransaksi;
        this.idPelanggan = idPelanggan;
        this.idLayanan = idLayanan;
        this.berat = berat;
        this.total = total;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return String.format("ID: %s | Pelanggan: %s | Layanan: %s | Berat: %.2fkg | Total: Rp%.2f | Status: %s",
                idTransaksi, idPelanggan, idLayanan, berat, total, status);
    }
}