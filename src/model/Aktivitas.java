package model;

public class Aktivitas {

    private String namaAktivitas;
    private String tanggal;
    private String keterangan;

    public Aktivitas() {
    }

    public Aktivitas(
            String namaAktivitas,
            String tanggal,
            String keterangan
    ) {

        this.namaAktivitas = namaAktivitas;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
    }

    public String getNamaAktivitas() {
        return namaAktivitas;
    }

    public void setNamaAktivitas(String namaAktivitas) {
        this.namaAktivitas = namaAktivitas;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void tampilAktivitas() {

        System.out.println(
                "Aktivitas : " + namaAktivitas
        );

    }
}