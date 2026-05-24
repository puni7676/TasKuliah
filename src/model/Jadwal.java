package model;

public class Jadwal {

    private int id;
    private String mataKuliah;
    private String hari;
    private String jam;
    private String ruangan;

    public Jadwal() {
    }

    public Jadwal(
            int id,
            String mataKuliah,
            String hari,
            String jam,
            String ruangan
    ) {

        this.id = id;
        this.mataKuliah = mataKuliah;
        this.hari = hari;
        this.jam = jam;
        this.ruangan = ruangan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }
}