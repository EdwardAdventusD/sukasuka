class LayananLaundry {
    private String idLayanan, nama;
    private double hargaPerKg;

    public LayananLaundry(String idLayanan, String nama, double hargaPerKg) {
        this.idLayanan = idLayanan;
        this.nama = nama;
        this.hargaPerKg = hargaPerKg;
    }

    public String getIdLayanan() {
        return idLayanan;
    }

    public String getNama() {
        return nama;
    }

    public double getHargaPerKg() {
        return hargaPerKg;
    }
}